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
import java.util.Date;
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
    public List<Role> listAll(String name, Date fromDate, Date toDate, Pageable pageable) {
        return repository.listAll(name, fromDate, toDate, pageable);
    }

    @Override
    public int countAll(String name, Date fromDate, Date toDate) {
        return repository.countAll(name, fromDate, toDate);
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

    @Override
    public List<Role> roles() {
        return repository.roles();
    }

    @Override
    public List<Role> rolesByPersonnelLevel(int personnelLevel) {
        return repository.rolesByPersonnelLevel(personnelLevel);
    }

}
