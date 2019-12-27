package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Booking;
import com.aptech.project.hotel.model.BookedDto;
import com.aptech.project.hotel.model.BookingDto;
import com.aptech.project.hotel.model.UsingDto;
import com.aptech.project.hotel.util.Constant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT NEW com.aptech.project.hotel.model.BookedDto" +
            "(t1.id, t1.totalPriceExpected, t2.roomCode, t1.fromDate, t1.toDate, t3.customerName, t3.phoneNumber, t3.identificationNumber) " +
            "FROM Booking t1 LEFT JOIN Room t2 ON t1.roomId = t2.id " +
            "LEFT JOIN Customer t3 ON t1.customerId = t3.id " +
            "WHERE t1.deleted = 0 AND t1.status = "+ Constant.BOOKING_STATUS_BOOKED +" ORDER BY t1.fromDate ASC ")
    List<BookedDto> findAllBooked();

    @Query("SELECT NEW com.aptech.project.hotel.model.BookedDto" +
            "(t1.id, t1.totalPriceExpected, t2.roomCode, t1.fromDate, t1.toDate, t3.customerName, t3.phoneNumber, t3.identificationNumber) " +
            "FROM Booking t1 LEFT JOIN Room t2 ON t1.roomId = t2.id " +
            "LEFT JOIN Customer t3 ON t1.customerId = t3.id " +
            "WHERE t1.deleted = 0 AND t1.status = "+ Constant.BOOKING_STATUS_CHECKIN +" ORDER BY t1.fromDate ASC ")
    List<BookedDto> findAllUsing();

    @Query("SELECT NEW com.aptech.project.hotel.model.UsingDto" +
            "(t1.id, t3.id, t3.customerName, t3.phoneNumber, t3.identificationNumber, t2.id, t2.roomCode, t2.price, t2.hourlyPrice," +
            "t1.fromDate, t1.toDate" +
            ", t1.checkInTime, t1.checkOutTime, t1.totalPriceExpected, t1.totalPriceReality, t1.status, t1.type) " +
            "FROM Booking t1 LEFT JOIN Room t2 ON t1.roomId = t2.id " +
            "LEFT JOIN Customer t3 ON t1.customerId = t3.id " +
            "WHERE t1.deleted = 0 AND t1.id = ?1 AND " +
            "t1.status = "+ Constant.BOOKING_STATUS_CHECKIN)
    UsingDto usingDetail(int id);

    @Query("SELECT count(t1) FROM Booking t1 WHERE t1.deleted = 0 AND t1.status = "+ Constant.BOOKING_STATUS_CHECKIN)
    int dashboardUsing();

    @Query("SELECT count(t1) FROM Booking t1 WHERE t1.deleted = 0 AND t1.status = "+ Constant.BOOKING_STATUS_BOOKED)
    int dashboardBooked();

    @Query("SELECT count(t1) FROM Booking t1 WHERE t1.deleted = 0 AND t1.status = "+ Constant.BOOKING_STATUS_CHECKOUT)
    int dashboardNumberOfReservations();

    @Query("SELECT (COUNT(t1) > 0) FROM Booking t1 " +
            "WHERE (t1.deleted = 0) AND (t1.customerId = ?1) " +
            "AND ((t1.fromDate BETWEEN ?2 AND ?3) OR (t1.toDate BETWEEN ?2 AND ?3)) " +
            "AND (t1.status = " + Constant.BOOKING_STATUS_BOOKED +")")
    boolean checkUserBook(int customerId, Date fromDate, Date toDate);

    @Query("SELECT (COUNT(t1) > 0) FROM Booking t1 WHERE t1.deleted = 0 AND t1.customerId = ?1 AND t1.status = " + Constant.BOOKING_STATUS_CHECKIN)
    boolean checkUserUsing(int customerId);
}
