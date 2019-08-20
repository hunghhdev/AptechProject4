package com.aptech.project.hotel.controller;

import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.impl.UserServiceImpl;
import com.aptech.project.hotel.util.Constant;
import com.aptech.project.hotel.util.EnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Constant.API+"/user")
public class UserController {
    @Autowired
    UserServiceImpl service;

    @PostMapping(value = "/create")
    public User createUser(@Valid @RequestBody User user) {
        user.setPassword(EnCode.md5(user.getPassword()));
        return service.save(user);
    }
}
