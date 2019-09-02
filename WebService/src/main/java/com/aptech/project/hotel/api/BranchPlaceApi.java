package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.BranchPlaceConverter;
import com.aptech.project.hotel.entity.BranchPlace;
import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.BranchPlaceDto;
import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.BranchPlaceService;
import com.aptech.project.hotel.service.UserService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(Constant.API+"/branch-place")
public class BranchPlaceApi {

    @Autowired
    private BranchPlaceService service;

    @Autowired
    private UserService userService;

    @Autowired
    private BranchPlaceConverter converter;

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_BRANCH_PLACE_READ')")
    public ResponseEntity<ServiceResult> list(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("name") String name, @RequestParam("fromDate") long fromDate,
            @RequestParam("toDate") long toDate){
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        ServiceResult serviceResult = new ServiceResult();

        Date from = new Date(fromDate==0? Constant.MIN_DATE:fromDate);
        Date to = new Date(toDate==0?Constant.MAX_DATE:toDate);

        serviceResult.setData(new Data(service.countAll(name, from, to),
                converter.toBranchPlacesDto(service.listAll(name, from, to, pageable))));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/list-branch-place")
    public ResponseEntity<ServiceResult> listBranchPlace(Authentication authentication){
        User user = userService.findByUsername(authentication.getName());
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(user.getPersonnelLevel()==1?converter.toBranchPlacesDto(service.listAll())
                :converter.toBranchPlaceDto(service.findById(user.getPersonnelLevel())));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('PERM_BRANCH_PLACE_CREATE')")
    public ResponseEntity<ServiceResult> create(@RequestBody BranchPlaceDto branchPlaceDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        BranchPlace branchPlace = converter.toBranchPlace(branchPlaceDto);
        branchPlace.setCreatedBy(authentication.getName());
        serviceResult.setMessage("Tạo chi nhánh thành công");
        serviceResult.setData(converter.toBranchPlaceDto(service.save(branchPlace)));
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('PERM_BRANCH_PLACE_UPDATE')")
    public ResponseEntity<ServiceResult> update(@RequestBody BranchPlaceDto branchPlaceDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        BranchPlace branchPlace = converter.toBranchPlace(branchPlaceDto);
        branchPlace.setUpdatedBy(authentication.getName());
        serviceResult.setData(converter.toBranchPlaceDto(service.save(branchPlace)));
        serviceResult.setMessage("Cập nhật chi nhánh thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/delete")
    @PreAuthorize("hasAuthority('PERM_BRANCH_PLACE_DELETE')")
    public ResponseEntity<ServiceResult> delete(@RequestParam("id") int id, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        service.delete(id,authentication.getName());
        serviceResult.setMessage("Xoá chi nhánh thành công");
        return ResponseEntity.ok(serviceResult);
    }
}
