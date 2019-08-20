package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.User;

import javax.validation.Valid;

public interface UserService {
    User findByUsername(String username);
    User save(@Valid User user);
    User findByUsernameAndPassword(String username, String password);
}
