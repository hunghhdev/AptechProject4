package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Room;
import com.aptech.project.hotel.repository.RoomRepository;
import com.aptech.project.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository repository;

    @Override
    public Room save(Room room) {
        return repository.saveAndFlush(room);
    }

    @Override
    public boolean existByCode(String code) {
        return repository.existByCode(code);
    }

    @Override
    public List<Room> listAll(String branch, String code, Date fromDate, Date toDate, Pageable pageable) {
        return repository.listAll(branch, code, fromDate, toDate, pageable);
    }

    @Override
    public List<Room> listAll(String status, Pageable pageable) {
        return repository.listAll(status, pageable);
    }

    @Override
    public int count(String branch, String code, Date fromDate, Date toDate) {
        return repository.count(branch, code, fromDate, toDate);
    }

    @Override
    public int count(String status) {
        return repository.count(status);
    }

    @Override
    public void delete(int id, String usernameUpdate) {
        repository.delete(id, usernameUpdate);
    }
}
