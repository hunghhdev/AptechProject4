package com.aptech.project.hotel.controller;

import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.JwtService;
import com.aptech.project.hotel.service.UserService;
import com.aptech.project.hotel.util.Constant;
import com.aptech.project.hotel.util.EnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @GetMapping(Constant.API+"/login")
    public ResponseEntity<?> login(@RequestParam("username") String username,
                                               @RequestParam("password") String password){
        ServiceResult serviceResult = new ServiceResult();
        User user = service.findByUsernameAndPassword(username, EnCode.md5(password));
        if (user==null){
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            serviceResult.setMessage("Sai tài khoản hoặc mật khẩu");
            return ResponseEntity.ok(serviceResult);
        }
        String jwtKey = jwtService.generateTokenLogin(username);
        user.setJwtKey(jwtKey);
        service.save(user);
        serviceResult.setData(jwtKey);
        return ResponseEntity.ok(serviceResult);
    }
}
