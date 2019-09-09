package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface UserService {
    List<User> findUsers(String username, Date fromDate, Date toDate, Pageable pageable);
    List<User> findUsers(String username, Date fromDate, Date toDate, int branch, Pageable pageable);
    int countUsers(String username, Date fromDate, Date toDate);
    int countUsers(String username, Date fromDate, Date toDate, int branch);
    User findByUsername(String username);
    User save(User user);
    boolean existByUsername(String username);
    void delete(int id, String usernameUpdate);
    User findByUsernameAndPassword(String username, String password);
    void updateJwtKey(String jwtKey, int id);
    boolean existByRoleId(int roleId);
}
