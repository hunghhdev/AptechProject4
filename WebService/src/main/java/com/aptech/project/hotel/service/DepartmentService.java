package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Department;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface DepartmentService {
    List<Department> listAll(String name, Date fromDate, Date toDate, Pageable pageable);
    int countAll(String name, Date fromDate, Date toDate);
    Department save(Department department);
}
