package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.BranchPlace;
import com.aptech.project.hotel.repository.BranchPlaceRepository;
import com.aptech.project.hotel.service.BranchPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BranchPlaceServiceImpl implements BranchPlaceService {

    @Autowired
    private BranchPlaceRepository repository;

    @Override
    public BranchPlace findById(int id) {
        return repository.getOne(id);
    }

    @Override
    public List<BranchPlace> listAll() {
        return repository.listAll();
    }

    @Override
    public List<BranchPlace> listAll(String name, Date fromDate, Date toDate, Pageable pageable) {
        return repository.listAll(name, fromDate, toDate, pageable);
    }

    @Override
    public int countAll(String name, Date fromDate, Date toDate) {
        return repository.countAll(name, fromDate, toDate);
    }

    @Override
    public BranchPlace save(BranchPlace branchPlace) {
        return repository.saveAndFlush(branchPlace);
    }

    @Override
    public void delete(int id, String usernameUpdate) {
        repository.delete(id, usernameUpdate);
    }
}
