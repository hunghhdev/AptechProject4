package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Room;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface RoomService {
    Room save(Room room);
    boolean existByCode(String code);
    List<Room> listAll(String type, Date fromDate, Date toDate, Pageable pageable);
    List<Room> listRoomEmpty(String status, Date fromDate, Date toDate);
    int count(String type, Date fromDate, Date toDate);
    int count(String status);
    void updateStatus(int id, String status);
    Room findById(int id);
}
