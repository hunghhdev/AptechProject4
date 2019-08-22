package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.UserConverter;
import com.aptech.project.hotel.dto.UserDto;
import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.RoleService;
import com.aptech.project.hotel.service.UserService;
import com.aptech.project.hotel.util.Constant;
import com.aptech.project.hotel.util.EnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ServiceResult> create(@RequestBody UserDto userDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        User user = converter.toUser(userDto);
        user.setPassword(EnCode.md5(userDto.getPassword()));
        user.setCreatedBy(authentication.getName());
        serviceResult.setData(converter.toUserDto(service.save(user)));
        serviceResult.setMessage("Tạo tài khoản thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ServiceResult> update(@RequestBody UserDto userDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        User user = converter.toUser(userDto);
        user.setPassword(EnCode.md5(userDto.getPassword()));
        user.setUpdatedBy(authentication.getName());
        serviceResult.setData(converter.toUserDto(service.save(user)));
        serviceResult.setMessage("Cập nhật tài khoản thành công");
        return ResponseEntity.ok(serviceResult);
    }
}
