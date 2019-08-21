package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.repository.RoleRepository;
import com.aptech.project.hotel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Optional<Role> findById(int id) {
        return repository.findById(id);
    }
}
