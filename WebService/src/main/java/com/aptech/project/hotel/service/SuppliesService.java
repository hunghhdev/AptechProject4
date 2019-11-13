package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Supplies;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface SuppliesService {
    List<Supplies> listAll(String name, Date fromDate, Date toDate, Pageable pageable);
    List<Supplies> listAllAvailability();
    int countAll(String name, Date fromDate, Date toDate);
    Supplies save(Supplies supplies);
    void delete(int id, String usernameUpdate);
    Set<Supplies> findByRoomId(int id);
    void addUsed(int id);
    void subtractUsed(int id);
}
