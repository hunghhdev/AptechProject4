package com.aptech.project.hotel.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aptech.project.hotel.entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("FROM Department t1 WHERE t1.deleted = 0 AND t1.departmentName LIKE %:name% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    List<Department> listAll(@Param("name") String name, @Param("fromDate") Date fromDate
            , @Param("toDate") Date toDate, Pageable pageable);
    @Query("SELECT count(*) FROM Department t1 WHERE t1.deleted = 0 AND t1.departmentName LIKE %:name% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    int countAll(@Param("name") String name, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
