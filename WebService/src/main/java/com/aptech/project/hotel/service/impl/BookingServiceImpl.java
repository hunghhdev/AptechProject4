package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.model.BookedDto;
import com.aptech.project.hotel.repository.BookingRepository;
import com.aptech.project.hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository repository;

    @Override
    public void saveBooking(Booking booking) {
        repository.save(booking);
    }

    @Override
    public List<BookedDto> listBooked() {
        return repository.findAllBooked();
    }
}
