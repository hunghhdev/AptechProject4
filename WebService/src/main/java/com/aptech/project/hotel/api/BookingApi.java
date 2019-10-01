package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.BookingConverter;
import com.aptech.project.hotel.converter.RoomConverter;
import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.model.BookingDto;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API + "/booking")
public class BookingApi {

    @Autowired
    private BookingService service;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private BookingConverter bookingConverter;

    @GetMapping(value = "/list-empty")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> listEmpty(@RequestParam("page") int page, @RequestParam("size") int size){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_EMPTY),
                                       roomConverter.toRoomsDto(roomService.listAll(Constant.ROOM_STATUS_EMPTY, pageable))));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/list-booked")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> listBooked(@RequestParam("page") int page, @RequestParam("size") int size){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_BOOKED),
                                       roomService.listAll(Constant.ROOM_STATUS_BOOKED, pageable)));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/book")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> booking(@RequestBody BookingDto bookingDto, Authentication authentication){
        ServiceResult serviceResult = new ServiceResult();
        Booking booking = bookingConverter.toBooking(bookingDto);
        booking.setCreatedBy(authentication.getName());
        service.booking(booking);
        roomService.updateStatus(booking.getRoomId(), Constant.ROOM_STATUS_BOOKED);
        return ResponseEntity.ok(serviceResult);
    }

}
