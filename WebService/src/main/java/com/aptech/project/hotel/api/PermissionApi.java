package com.aptech.project.hotel.api;

import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.PermissionService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Constant.API+"/permission")
public class PermissionApi {

    @Autowired
    private PermissionService service;

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_PERMISSION_READ')")
    public ResponseEntity<ServiceResult> list(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("name") String name){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("id").ascending());
        serviceResult.setData(new Data(service.count(name), service.list(name, pageable)));
        return ResponseEntity.ok(serviceResult);
    }
}
