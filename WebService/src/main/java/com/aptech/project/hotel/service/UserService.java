package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.User;

public interface UserService {
    User findByUsername(String username);
    User save(User user);
    User findByUsernameAndPassword(String username, String password);
    void updateJwtKey(String jwtKey, int id);
    User create(User user);
}
