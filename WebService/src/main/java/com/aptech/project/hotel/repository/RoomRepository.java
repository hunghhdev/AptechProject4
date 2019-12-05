package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT (COUNT(t1) > 0) FROM Room t1 WHERE t1.roomCode = :code")
    boolean existByCode(@Param("code") String code);

    @Query("FROM Room t1 JOIN fetch t1.supplies WHERE t1.deleted = 0 AND t1.roomType LIKE %:type% " +
            "AND t1.createdDate BETWEEN :fromDate AND :toDate")
    List<Room> listAll(@Param("type") String type,
                       @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

    @Query("SELECT t1 FROM Room t1 LEFT JOIN FETCH t1.supplies LEFT JOIN Booking t2 ON t2.roomId = t1.id " +
            "WHERE t1.deleted = 0 AND t1.status LIKE %:status% AND " +
            "t1.id NOT IN (SELECT t3.roomId FROM Booking t3 WHERE t3.fromDate BETWEEN :fromDate AND :toDate) AND " +
            "t1.id NOT IN (SELECT t4.roomId FROM Booking t4 WHERE t4.toDate BETWEEN  :fromDate AND :toDate)")
    List<Room> listRoomEmpty(@Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

    @Query("SELECT COUNT(t1) FROM Room t1 WHERE t1.deleted = 0 AND t1.roomType LIKE %:type%" +
            " AND t1.createdDate BETWEEN :fromDate AND :toDate")
    int count(@Param("type") String type,
              @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT COUNT(t1) FROM Room t1 WHERE t1.deleted = 0 AND t1.status LIKE %:status%")
    int count(@Param("status") String status);

    @Modifying
    @Transactional
    @Query("UPDATE Room t1 set t1.status = ?2 WHERE t1.id = ?1")
    void updateStatus(int id, String status);

    @Query("FROM Room t1 LEFT JOIN fetch t1.supplies WHERE t1.id = :id ")
    Room findById(int id);
}
