package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

public interface RoleService {
    Role findById(int id);
    List<Role> listAll(String name, Pageable pageable);
    int countAll(String name);
    List<GrantedAuthority> getAuthorities(int roleId);
    Role save(Role role);
    boolean existByName(String name);
    void delete(int id, String usernameUpdate);
    List<Role> roles();
    List<Role> rolesByPersonnelLevel(int personnelLevel);
}
