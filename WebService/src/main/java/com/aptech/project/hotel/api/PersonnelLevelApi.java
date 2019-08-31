package com.aptech.project.hotel.api;

import com.aptech.project.hotel.model.UserSecurity;
import com.aptech.project.hotel.service.PersonnelLevelService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aptech.project.hotel.model.ServiceResult;

@RestController
@RequestMapping(Constant.API+"/personnel-level")
public class PersonnelLevelApi {

    @Autowired
    private PersonnelLevelService service;

    @GetMapping(value = "/list")
    public ResponseEntity<ServiceResult> list(Authentication authentication){
        ServiceResult serviceResult = new ServiceResult();
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        serviceResult.setData(service.list(userSecurity.getPersonnelLevel()));
        return ResponseEntity.ok(serviceResult);
    }
}
