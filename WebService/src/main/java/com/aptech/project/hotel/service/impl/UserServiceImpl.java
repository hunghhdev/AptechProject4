package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.repository.UserRepository;
import com.aptech.project.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findUsers(String username, Date fromDate, Date toDate, Pageable pageable) {
        return repository.findUsers(username, fromDate, toDate, pageable);
    }

    @Override
    public List<User> findUsers(String username, Date fromDate, Date toDate, int branch, Pageable pageable) {
        return repository.findUsers(username, fromDate, toDate, branch, pageable);
    }

    @Override
    public int countUsers(String username, Date fromDate, Date toDate) {
        return repository.countUsers(username,fromDate,toDate);
    }

    @Override
    public int countUsers(String username, Date fromDate, Date toDate, int branch) {
        return repository.countUsers(username,fromDate,toDate, branch);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return repository.saveAndFlush(user);
    }

    @Override
    public boolean existByUsername(String username) {
        return repository.existByUsername(username);
    }

    @Override
    public void delete(int id, String usernameUpdate) {
        repository.delete(id, usernameUpdate);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username,password);
    }

    @Override
    public void updateJwtKey(String jwtKey, int id) {
        repository.updateJwtKey(jwtKey,id);
    }

    @Override
    public boolean existByRoleId(int roleId) {
        return repository.existByRoleId(roleId);
    }

}
