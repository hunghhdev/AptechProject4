package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.UserConverter;
import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.model.UserInfoDto;
import com.aptech.project.hotel.service.JwtService;
import com.aptech.project.hotel.service.RoleService;
import com.aptech.project.hotel.service.UserService;
import com.aptech.project.hotel.util.Constant;
import com.aptech.project.hotel.util.EnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginApi {

    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserConverter converter;

    @GetMapping(Constant.API+"/login")
    public ResponseEntity<ServiceResult> login(@RequestParam("username") String username,
                                               @RequestParam("password") String password){
        ServiceResult serviceResult = new ServiceResult();
        User user = service.findByUsernameAndPassword(username, EnCode.md5(password));
        if (user==null){
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            serviceResult.setMessage("Sai tài khoản hoặc mật khẩu");
            return ResponseEntity.ok(serviceResult);
        }
        String jwtKey = jwtService.generateTokenLogin(username);
        service.updateJwtKey(jwtKey,user.getId());
        serviceResult.setData(jwtKey);
        serviceResult.setMessage("Bạn đã đăng nhập thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(Constant.API+"/getInfo")
    public ResponseEntity<ServiceResult> getInfo(@RequestParam("token") String token){
        ServiceResult serviceResult = new ServiceResult();
        User user = service.findByUsername(jwtService.getUsernameFromToken(token));
        if (user!=null){
            UserInfoDto userInfoDto = converter.toUserInfoDto(user);
            Role role = roleService.findById(user.getRoleId());
            userInfoDto.setPermissions(role.getPermissions());
            serviceResult.setData(userInfoDto);
        } else {
            serviceResult.setMessage("Not logged in");
            serviceResult.setStatus(ServiceResult.Status.TOKEN_FAIl);
        }
        return ResponseEntity.ok(serviceResult);
    }
}
