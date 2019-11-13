package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.RoomConverter;
import com.aptech.project.hotel.entity.Room;
import com.aptech.project.hotel.entity.Supplies;
import com.aptech.project.hotel.model.*;
import com.aptech.project.hotel.service.RoomService;
import com.aptech.project.hotel.service.SuppliesService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(Constant.API+"/room")
public class RoomApi {

    @Autowired
    private RoomService service;

    @Autowired
    private SuppliesService suppliesService;

    @Autowired
    private RoomConverter converter;

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_ROOM_READ')")
    public ResponseEntity<ServiceResult> list(
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("type") String type,
            @RequestParam("fromDate") long fromDate, @RequestParam("toDate") long toDate){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        serviceResult.setData(new Data(service.count(type, Constant.minDate(fromDate), Constant.maxDate(toDate)),
                converter.toRoomsDto(service.listAll(type,
                        Constant.minDate(fromDate), Constant.maxDate(toDate), pageable))));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('PERM_ROOM_CREATE')")
    public ResponseEntity<ServiceResult> create(@RequestBody RoomDto roomDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        if (service.existByCode(roomDto.getCode())){
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            serviceResult.setMessage("Mã phòng đã tồn tại");
            return ResponseEntity.ok(serviceResult);
        }
        Room room = converter.toRoom(roomDto);
        room.setCreatedBy(authentication.getName());
        serviceResult.setData(converter.toRoomDto(service.save(room)));
        room.getSupplies().forEach(r -> suppliesService.addUsed(r.getId()));
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('PERM_ROOM_UPDATE')")
    public ResponseEntity<ServiceResult> update(@RequestBody RoomDto roomDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Room room = converter.toRoom(roomDto);
        Set<Supplies> suppliesNow = suppliesService.findByRoomId(room.getId());
        Set<Supplies> suppliesSubtract = diff(suppliesNow, room.getSupplies());
        Set<Supplies> suppliesAdd = diff(room.getSupplies(), suppliesNow);
        room.setUpdatedBy(authentication.getName());
        serviceResult.setData(converter.toRoomDto(service.save(room)));
        suppliesSubtract.forEach(r -> suppliesService.subtractUsed(r.getId()));
        suppliesAdd.forEach(r -> suppliesService.addUsed(r.getId()));
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/delete")
    @PreAuthorize("hasAuthority('PERM_ROOM_DELETE')")
    public ResponseEntity<ServiceResult> delete(@RequestParam("id") int id, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Room room = service.findById(id);
        if (room == null){
            serviceResult.setMessage("Không tìm thấy phòng");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
        }
        room.setDeleted(true);
        room.setUpdatedBy(authentication.getName());
        service.save(room);
        room.getSupplies().forEach(r -> suppliesService.subtractUsed(r.getId()));
        serviceResult.setMessage("Xoá phòng thành công");
        return ResponseEntity.ok(serviceResult);
    }


    public static Set<Supplies> diff(Set<Supplies> s1, Set<Supplies> s2) {
        Set<Supplies> returnPer = new HashSet<>(s1);
        for (Supplies supplies: s2) {
            for (Supplies s: s1) {
                if (supplies.equals(s)) returnPer.remove(s);
            }
        }
        return returnPer;
    }
}
