package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.model.BookedDto;
import com.aptech.project.hotel.model.BookingDto;
import com.aptech.project.hotel.util.Constant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT NEW com.aptech.project.hotel.model.BookedDto" +
            "(t1.id, t1.totalPriceExpected, t2.roomCode, t1.fromDate, t1.toDate, t3.customerName, t3.phoneNumber, t3.identificationNumber) " +
            "FROM Booking t1 LEFT JOIN Room t2 ON t1.roomId = t2.id " +
            "LEFT JOIN Customer t3 ON t1.customerId = t3.id " +
            "WHERE t1.deleted = 0 AND t1.status = "+ Constant.BOOKING_STATUS_BOOKED +" ORDER BY t1.fromDate ASC ")
    List<BookedDto> findAllBooked();
}
