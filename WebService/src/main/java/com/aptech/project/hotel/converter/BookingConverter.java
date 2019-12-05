package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.model.BookingDto;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {

    public BookingDto toBookingDto(Booking booking){
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setRoomId(booking.getRoomId());
        bookingDto.setFromDate(booking.getFromDate());
        bookingDto.setToDate(booking.getToDate());
//        bookingDto.set(booking.getTotalDate());
//        bookingDto.setCount(booking.getTotalPrice());
        bookingDto.setCustomerId(booking.getCustomerId());
        bookingDto.setCreatedDate(booking.getCreatedDate());
        bookingDto.setCreatedBy(booking.getCreatedBy());

        return bookingDto;
    }

    public Booking toBooking(BookingDto bookingDto){
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setFromDate(bookingDto.getFromDate());
        booking.setToDate(bookingDto.getToDate());
        booking.setRoomId(bookingDto.getRoomId());
        booking.setCustomerId(bookingDto.getCustomerId());
//        booking.setTotalDate(bookingDto.getDays());
//        booking.setTotalPrice(bookingDto.getCount());
        booking.setCreatedDate(bookingDto.getCreatedDate());
        booking.setCreatedBy(bookingDto.getCreatedBy());

        return booking;
    }
}
