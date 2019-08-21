package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.UserConverter;
import com.aptech.project.hotel.dto.UserDto;
import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.service.RoleService;
import com.aptech.project.hotel.service.UserService;
import com.aptech.project.hotel.util.Constant;
import com.aptech.project.hotel.util.EnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(Constant.API+"/user")
public class UserApi {

    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserConverter converter;

    @PostMapping(value = "/create")
    public UserDto createUser(@RequestBody UserDto userDto) {
        userDto.setPassword(EnCode.md5(userDto.getPassword()));
        User user = converter.toUser(userDto);
//        Optional<Role> role = roleService.findById(userDto.getRole());
//        if (role.isPresent()) user.setRole(role.get());
        return converter.toUserDto(service.save(user));
    }
}
