package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.BranchPlace;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface BranchPlaceService {
    List<BranchPlace> findById(int id);
    List<BranchPlace> listAll();
    List<BranchPlace> listAll(int level);
    List<BranchPlace> listAll(String name, Date fromDate, Date toDate, Pageable pageable);
    int countAll(String name, Date fromDate, Date toDate);
    BranchPlace save(BranchPlace branchPlace);
    void delete(int id, String usernameUpdate);
}
