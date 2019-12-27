package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.BookingConverter;
import com.aptech.project.hotel.converter.CustomerConverter;
import com.aptech.project.hotel.converter.RoomConverter;
import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.entity.Room;
import com.aptech.project.hotel.entity.Supplies;
import com.aptech.project.hotel.model.*;
import com.aptech.project.hotel.service.BookingService;
import com.aptech.project.hotel.service.CustomerService;
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

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(Constant.API + "/booking")
public class BookingApi {

    @Autowired
    private BookingService service;

    @Autowired
    private RoomService roomService;

    @Autowired
    private SuppliesService suppliesService;

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
    public ResponseEntity<ServiceResult> listEmpty(@RequestParam("fromDate") long fromDate, @RequestParam("toDate") long toDate) {
        ServiceResult serviceResult = new ServiceResult();
        List<Room> rooms = roomService.listRoomEmpty(Constant.ROOM_STATUS_ACTIVE,
                Constant.minDate(fromDate), Constant.maxDate(toDate));
        rooms.forEach(room -> room.setSupplies(suppliesService.findByRoomId(room.getId())));
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_ACTIVE), roomConverter.toRoomsDto(rooms)));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/list-booked")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> listBooked() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_BOOKED),
                service.listBooked()));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/list-using")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> listUsing() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(new Data(roomService.count(Constant.ROOM_STATUS_BOOKED),
                service.listUsing()));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/book")
    @PreAuthorize("hasAuthority('PERM_BOOK_ROOM')")
    public ResponseEntity<ServiceResult> booking(@RequestBody BookingDto bookingDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        if (service.checkUserBook(bookingDto.getCustomerId(), bookingDto.getFromDate(), bookingDto.getToDate())){
            serviceResult.setMessage("Nguời dùng này đã đặt phòng khác trong khoảng thời gian");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            return ResponseEntity.ok(serviceResult);
        }
        if (service.checkUserUsing(bookingDto.getCustomerId())){
            serviceResult.setMessage("Người này đang trong khách sạn mà");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            return ResponseEntity.ok(serviceResult);
        }
        Customer customer = new Customer();
        if (bookingDto.getCustomerId() == 0) {
            if (customerService.existByIdentification(bookingDto.getIdentification())){
                serviceResult.setMessage("Người dùng đã tồn tại");
                serviceResult.setStatus(ServiceResult.Status.FAILED);
                return ResponseEntity.ok(serviceResult);
            }
            if (customerService.existByPhone(bookingDto.getCustomerPhone())){
                serviceResult.setMessage("Số điện thoại đã tồn tại");
                serviceResult.setStatus(ServiceResult.Status.FAILED);
                return ResponseEntity.ok(serviceResult);
            }
            customer.setCustomerName(bookingDto.getCustomerName());
            customer.setPhoneNumber(bookingDto.getCustomerPhone());
            customer.setIdentificationNumber(bookingDto.getIdentification());
            customer = customerService.save(customer);
            customerService.saveES(customerConverter.toCustomerDto(customer));
        }
        Booking booking = bookingConverter.toBooking(bookingDto);
        if (booking.getCustomerId() == 0) booking.setCustomerId(customer.getId());
        long time = bookingDto.getToDate().getTime() - bookingDto.getFromDate().getTime();
        booking.setType(time <= Constant.TIME_2_HOUR);
        int timeUse = Math.round((float) time / (1000 * 60 * 60 * (booking.isType() ? 1 : 24)));
        Room room = roomService.findById(booking.getRoomId());
        booking.setTotalPriceExpected(timeUse * (booking.isType() ? room.getHourlyPrice() : room.getPrice()));
        booking.setCreatedBy(authentication.getName());
        booking.setStatus(Constant.BOOKING_STATUS_BOOKED);
        service.saveBooking(booking);
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/cancel-book")
    public ResponseEntity<ServiceResult> cancelBooked(@RequestBody BookedDto bookedDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Booking booking = service.findById(bookedDto.getId());
        if (booking == null) {
            serviceResult.setMessage("Không tồn tại bản ghi nào");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            return ResponseEntity.ok(serviceResult);
        }
        booking.setStatus(Constant.BOOKING_STATUS_CANCEL);
        booking.setUpdatedBy(authentication.getName());
        service.saveBooking(booking);
        serviceResult.setStatus(ServiceResult.Status.SUCCESS);
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/check-in")
    public ResponseEntity<ServiceResult> checkIn(@RequestBody BookedDto bookedDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Booking booking = service.findById(bookedDto.getId());
        if (booking == null) {
            serviceResult.setMessage("Không tồn tại bản ghi nào");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            return ResponseEntity.ok(serviceResult);
        }
        booking.setStatus(Constant.BOOKING_STATUS_CHECKIN);
        booking.setCheckInTime(new Date());
        booking.setUpdatedBy(authentication.getName());
        service.saveBooking(booking);
        serviceResult.setStatus(ServiceResult.Status.SUCCESS);
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<ServiceResult> getById(@RequestParam int id) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(service.usingDetail(id));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/check-out")
    public ResponseEntity<ServiceResult> checkOut(@RequestBody UsingDto usingDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Booking booking = service.findById(usingDto.getId());
        long time = new Date().getTime() - booking.getFromDate().getTime();
        booking.setType(time <= Constant.TIME_2_HOUR);
        int timeUse = Math.round((float) time / (1000 * 60 * 60 * (booking.isType() ? 1 : 24)));
        Room room = roomService.findById(booking.getRoomId());
        booking.setTotalPriceReality(timeUse * (booking.isType() ? room.getHourlyPrice() : room.getPrice()));
        booking.setStatus(Constant.BOOKING_STATUS_CHECKOUT);
        booking.setUpdatedBy(authentication.getName());
        service.saveBooking(booking);
        return ResponseEntity.ok(serviceResult);
    }

}
