package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.RoomConverter;
import com.aptech.project.hotel.entity.Room;
import com.aptech.project.hotel.model.*;
import com.aptech.project.hotel.service.RoomService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API+"/room")
public class RoomApi {

    @Autowired
    private RoomService service;

    @Autowired
    private RoomConverter converter;

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_ROOM_READ')")
    public ResponseEntity<ServiceResult> list(
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("type") String type,
            @RequestParam("branch") String branch,
            @RequestParam("fromDate") long fromDate, @RequestParam("toDate") long toDate){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        serviceResult.setData(new Data(service.count(branch, type, Constant.minDate(fromDate), Constant.maxDate(toDate)),
                converter.toRoomsDto(service.listAll(branch, type,
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
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('PERM_ROOM_UPDATE')")
    public ResponseEntity<ServiceResult> update(@RequestBody RoomDto roomDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Room room = converter.toRoom(roomDto);
        room.setUpdatedBy(authentication.getName());
        serviceResult.setData(converter.toRoomDto(service.save(room)));
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/delete")
    @PreAuthorize("hasAuthority('PERM_ROOM_DELETE')")
    public ResponseEntity<ServiceResult> delete(@RequestParam("id") int id, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        service.delete(id,authentication.getName());
        serviceResult.setMessage("Xoá phòng thành công");
        return ResponseEntity.ok(serviceResult);
    }

}
