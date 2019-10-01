package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Room;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface RoomService {
    Room save(Room room);
    boolean existByCode(String code);
    List<Room> listAll(String branch, String type, Date fromDate, Date toDate, Pageable pageable);
    List<Room> listAll(String status, Pageable pageable);
    int count(String branch, String type, Date fromDate, Date toDate);
    int count(String status);
    void delete(int id, String usernameUpdate);
    void updateStatus(int id, String status);
}
