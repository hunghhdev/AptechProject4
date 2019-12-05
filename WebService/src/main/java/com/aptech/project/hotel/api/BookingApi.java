package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.BookingConverter;
import com.aptech.project.hotel.converter.CustomerConverter;
import com.aptech.project.hotel.converter.RoomConverter;
import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.entity.Room;
import com.aptech.project.hotel.model.BookingDto;
import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.BookingService;
import com.aptech.project.hotel.service.CustomerService;
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
    private CustomerService customerService;

    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private BookingConverter bookingConverter;

    @Autowired
    private CustomerConverter customerConverter;

    @GetMapping(value = "/list-empty")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> listEmpty(@RequestParam("page") int page, @RequestParam("size") int size,
                                                   @RequestParam("fromDate") long fromDate, @RequestParam("toDate") long toDate){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_ACTIVE),
                                       roomConverter.toRoomsDto(roomService.listRoomEmpty(Constant.ROOM_STATUS_ACTIVE,
                                               Constant.minDate(fromDate), Constant.maxDate(toDate), pageable))));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/list-booked")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> listBooked(){
        ServiceResult serviceResult = new ServiceResult();
//        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_BOOKED),
                                       service.listBooked()));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/book")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> booking(@RequestBody BookingDto bookingDto, Authentication authentication){
        ServiceResult serviceResult = new ServiceResult();
        Customer customer = new Customer();
        if (bookingDto.getId()==0) {
            customer.setCustomerName(bookingDto.getNameCus());
            customer.setPhoneNumber(bookingDto.getPhoneCus());
            customer.setIdentificationNumber(bookingDto.getIdentification());
            customer = customerService.save(customer);
            customerService.saveES(customerConverter.toCustomerDto(customer));
        }
        Booking booking = bookingConverter.toBooking(bookingDto);
        if (booking.getCustomerId()==0) booking.setCustomerId(customer.getId());
        long time = bookingDto.getToDate().getTime()-bookingDto.getFromDate().getTime();
        booking.setType(time <= Constant.TIME_2_HOUR);
        int timeUse = Math.round((float) time / (1000 * 60 * 60 * (booking.isType()?1:24)));
        Room room = roomService.findById(booking.getRoomId());
        booking.setTotalPriceExpected(timeUse * (booking.isType() ? room.getHourlyPrice() : room.getPrice()));
        booking.setCreatedBy(authentication.getName());
        booking.setStatus(Constant.BOOKING_STATUS_BOOKED);
        service.saveBooking(booking);
        return ResponseEntity.ok(serviceResult);
    }

}
