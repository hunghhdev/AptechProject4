package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Permission;
import com.aptech.project.hotel.repository.PermissionRepository;
import com.aptech.project.hotel.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository repository;

    @Override
    public Set<Permission> permissions() {
        Set<Permission> permissions = new HashSet<>();
        for (Permission permission: repository.findAll()) {
            if (!permission.getChildren().isEmpty()) permissions.add(permission);
        }
        return permissions;
    }
}
