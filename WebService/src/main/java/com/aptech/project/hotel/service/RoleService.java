package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(int id);
}
