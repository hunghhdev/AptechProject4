package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Permission;
import com.aptech.project.hotel.model.PermissionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Set<Permission> permissions();
    List<PermissionDto> list(String name, Pageable pageable);
    int count(String name);
}
