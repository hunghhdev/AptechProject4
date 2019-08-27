package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
