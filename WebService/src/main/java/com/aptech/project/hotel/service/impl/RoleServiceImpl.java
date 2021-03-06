package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.repository.RoleRepository;
import com.aptech.project.hotel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> listAll(String name, Pageable pageable) {
        return repository.listAll(name, pageable);
    }

    @Override
    public List<Role> listAllBySupperUser() {
        return repository.listAllBySupperUser();
    }

    @Override
    public List<Role> listAll() {
        return repository.listAll();
    }

    @Override
    public int countAll(String name) {
        return repository.countAll(name);
    }

    @Override
    public List<GrantedAuthority> getAuthorities(int roleId) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        findById(roleId).getPermissions().forEach(permission ->
            authorities.add(new SimpleGrantedAuthority(permission.getPermissionKey()))
        );
        return authorities;
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public boolean existByName(String name) {
        return repository.existByName(name);
    }

    @Override
    public void delete(int id, String usernameUpdate) {
        repository.delete(id, usernameUpdate);
    }

}
