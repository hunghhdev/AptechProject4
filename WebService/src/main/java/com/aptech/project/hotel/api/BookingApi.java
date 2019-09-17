package com.aptech.project.hotel.api;

import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.BookingService;
import com.aptech.project.hotel.service.RoomService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.API + "/booking")
public class BookingApi {

    @Autowired
    private BookingService service;

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/list-empty")
    @PreAuthorize("hasAuthority('PERM_ROOKING_READ')")
    public ResponseEntity<ServiceResult> listEmpty(@RequestParam("page") int page, @RequestParam("size") int size){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_EMPTY),
                                       roomService.listAll(Constant.ROOM_STATUS_EMPTY, pageable)));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/list-booked")
    @PreAuthorize("hasAuthority('PERM_ROOKING_READ')")
    public ResponseEntity<ServiceResult> listBooked(@RequestParam("page") int page, @RequestParam("size") int size){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_BOOKED),
                                       roomService.listAll(Constant.ROOM_STATUS_BOOKED, pageable)));
        return ResponseEntity.ok(serviceResult);
    }
}
