package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role findById(int id);
    List<Role> listAll(String name, Date fromDate, Date toDate, Pageable pageable);
    int countAll(String name, Date fromDate, Date toDate);
    List<GrantedAuthority> getAuthorities(int roleId);
    Role save(Role role);
    boolean existByName(String name);
    void delete(int id, String usernameUpdate);
    List<Role> roles();
}
