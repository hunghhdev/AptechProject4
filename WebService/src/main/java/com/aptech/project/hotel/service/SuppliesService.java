package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Supplies;
import com.aptech.project.hotel.model.SuppliesDto;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface SuppliesService {
    List<Supplies> listAll(String name, Date fromDate, Date toDate, Pageable pageable);
    List<Supplies> listAll();
    int countAll(String name, Date fromDate, Date toDate);
    Supplies save(Supplies supplies);
    void delete(int id, String usernameUpdate);
    SuppliesDto saveES(SuppliesDto customerDto);
    List<SuppliesDto> findByName(String name);
}
