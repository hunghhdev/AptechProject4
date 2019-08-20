package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.repository.UserRepository;
import com.aptech.project.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User save(@Valid User user) {
        return repository.saveAndFlush(user);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username,password);
    }

    public List<GrantedAuthority> getAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        role.getPermissions().forEach(permission ->{
            authorities.add(new SimpleGrantedAuthority(permission.getPermissionKey()));
        });
        return authorities;
    }
}
