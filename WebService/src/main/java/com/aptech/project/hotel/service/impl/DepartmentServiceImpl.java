package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Department;
import com.aptech.project.hotel.repository.DepartmentRepository;
import com.aptech.project.hotel.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    @Override
    public List<Department> listAll(String name, Date fromDate, Date toDate, Pageable pageable) {
        return repository.listAll(name, fromDate, toDate, pageable);
    }

    @Override
    public int countAll(String name, Date fromDate, Date toDate) {
        return repository.countAll(name, fromDate, toDate);
    }

    @Override
    public Department save(Department department) {
        return repository.saveAndFlush(department);
    }
}
