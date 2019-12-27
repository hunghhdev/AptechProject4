package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.model.BookedDto;
import com.aptech.project.hotel.model.UsingDto;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking findById(int id);
    void saveBooking(Booking booking);
    List<BookedDto> listBooked();
    List<BookedDto> listUsing();
    UsingDto usingDetail(int id);
    boolean checkUserBook(int customerId, Date fromDate, Date toDate);
    boolean checkUserUsing(int customerId);
    int dashboardUsing();
    int dashboardBooked();
    int dashboardNumberOfReservations();
}
