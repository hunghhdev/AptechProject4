package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.DepartmentConverter;
import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.DepartmentDto;
import com.aptech.project.hotel.entity.Department;
import com.aptech.project.hotel.service.DepartmentService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.aptech.project.hotel.model.ServiceResult;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@RestController
@RequestMapping(Constant.API+"/department")
public class DepartmentApi {

    @Autowired
    private DepartmentService service;

    @Autowired
    private DepartmentConverter converter;

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_DEPARTMENT_READ')")
    public ResponseEntity<ServiceResult> list(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("name") String name, @RequestParam("fromDate") long fromDate,
            @RequestParam("toDate") long toDate){
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        ServiceResult serviceResult = new ServiceResult();

        Date from = new Date(fromDate==0? Constant.MIN_DATE:fromDate);
        Date to = new Date(toDate==0?Constant.MAX_DATE:toDate);

        serviceResult.setData(new Data(service.countAll(name, from, to),
                converter.toDepartmentsDto(service.listAll(name, from, to, pageable))));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('PERM_DEPARTMENT_CREATE')")
    public ResponseEntity<ServiceResult> create(@RequestBody DepartmentDto departmentDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Department department = converter.toDepartment(departmentDto);
        department.setCreatedBy(authentication.getName());
        serviceResult.setData(converter.toDepartmentDto(service.save(department)));
        return ResponseEntity.ok(serviceResult);
    }

//    @PutMapping(value = "/update")
//    @PreAuthorize("hasAuthority('PERM_DEPARTMENT_UPDATE')")
//    public ResponseEntity<ServiceResult> update(@RequestBody BranchPlaceDto branchPlaceDto, Authentication authentication) {
//        ServiceResult serviceResult = new ServiceResult();
//        BranchPlace role = converter.toBranchPlace(branchPlaceDto);
//        role.setUpdatedBy(authentication.getName());
//        serviceResult.setData(converter.toBranchPlaceDto(service.save(role)));
//        serviceResult.setMessage("Cập nhật chi nhánh thành công");
//        return ResponseEntity.ok(serviceResult);
//    }

//    @PutMapping(value = "/delete")
//    @PreAuthorize("hasAuthority('PERM_DEPARTMENT_DELETE')")
//    public ResponseEntity<ServiceResult> delete(@RequestParam("id") int id, Authentication authentication) {
//        ServiceResult serviceResult = new ServiceResult();
//        service.delete(id,authentication.getName());
//        serviceResult.setMessage("Xoá chi nhánh thành công");
//        return ResponseEntity.ok(serviceResult);
//    }
}
