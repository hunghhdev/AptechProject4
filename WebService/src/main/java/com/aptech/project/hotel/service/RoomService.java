package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Room;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface RoomService {
    Room save(Room room);
    boolean existByCode(String code);
    List<Room> listAll(String branch, String code, Date fromDate, Date toDate, Pageable pageable);
    void delete(int id, String usernameUpdate);
}
