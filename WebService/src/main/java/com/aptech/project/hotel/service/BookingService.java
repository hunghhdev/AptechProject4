package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.model.BookedDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    void saveBooking(Booking booking);
    List<BookedDto> listBooked();
}
