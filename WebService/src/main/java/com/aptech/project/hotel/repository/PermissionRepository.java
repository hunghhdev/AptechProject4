package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("FROM Permission t1 JOIN FETCH t1.children ORDER BY t1.id")
    List<Permission> findAll();
}
