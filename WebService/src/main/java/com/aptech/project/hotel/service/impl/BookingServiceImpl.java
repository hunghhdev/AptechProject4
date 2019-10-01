package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.repository.BookingRepository;
import com.aptech.project.hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository repository;

    @Override
    public void booking(Booking booking) {
        repository.save(booking);
    }
}
