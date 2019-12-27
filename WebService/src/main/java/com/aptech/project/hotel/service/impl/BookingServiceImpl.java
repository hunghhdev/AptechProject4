package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.model.BookedDto;
import com.aptech.project.hotel.model.UsingDto;
import com.aptech.project.hotel.repository.BookingRepository;
import com.aptech.project.hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository repository;

    @Override
    public Booking findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void saveBooking(Booking booking) {
        repository.save(booking);
    }

    @Override
    public List<BookedDto> listBooked() {
        return repository.findAllBooked();
    }

    @Override
    public List<BookedDto> listUsing() {
        return repository.findAllUsing();
    }

    @Override
    public UsingDto usingDetail(int id) {
        return repository.usingDetail(id);
    }

    @Override
    public boolean checkUserBook(int customerId, Date fromDate, Date toDate) {
        return repository.checkUserBook(customerId, fromDate, toDate);
    }

    @Override
    public boolean checkUserUsing(int customerId) {
        return repository.checkUserUsing(customerId);
    }

    @Override
    public int dashboardUsing() {
        return repository.dashboardUsing();
    }

    @Override
    public int dashboardBooked() {
        return repository.dashboardBooked();
    }

    @Override
    public int dashboardNumberOfReservations() {
        return repository.dashboardNumberOfReservations();
    }
}
