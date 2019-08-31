package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
