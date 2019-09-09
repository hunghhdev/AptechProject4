package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.RoleConverter;
import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.RoleDto;
import com.aptech.project.hotel.model.UserSecurity;
import com.aptech.project.hotel.service.PermissionService;
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
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.RoleService;

@RestController
@RequestMapping(Constant.API+"/role")
public class RoleApi {

    @Autowired
    private RoleService service;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleConverter converter;

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_ROLE_READ')")
    public ResponseEntity<ServiceResult> list(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("name") String name, @RequestParam("fromDate") long fromDate,
            @RequestParam("toDate") long toDate){
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(new Data(service.countAll(name, Constant.minDate(fromDate), Constant.maxDate(toDate)),
                converter.toRolesDto(service.listAll(name, Constant.minDate(fromDate), Constant.maxDate(toDate), pageable))));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/permissions")
    public ResponseEntity<ServiceResult> permissions(){
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(permissionService.permissions());
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('PERM_ROLE_CREATE')")
    public ResponseEntity<ServiceResult> create(@RequestBody RoleDto roleDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        if (service.existByName(roleDto.getRoleName())){
            serviceResult.setMessage("Đặt tên chức năng khác đi ạ");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            return ResponseEntity.ok(serviceResult);
        }
        Role role = converter.toRole(roleDto);
        role.setCreatedBy(authentication.getName());
        serviceResult.setData(converter.toRoleDto(service.save(role)));
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('PERM_ROLE_UPDATE')")
    public ResponseEntity<ServiceResult> update(@RequestBody RoleDto roleDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Role role = converter.toRole(roleDto);
        role.setUpdatedBy(authentication.getName());
        serviceResult.setData(converter.toRoleDto(service.save(role)));
        serviceResult.setMessage("Cập nhật chức năng thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/delete")
    @PreAuthorize("hasAuthority('PERM_ROLE_DELETE')")
    public ResponseEntity<ServiceResult> delete(@RequestParam("id") int id, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        if (userService.existByRoleId(id)) {
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            serviceResult.setMessage("Còn tồn tại người dùng, không thể xóa");
            return ResponseEntity.ok(serviceResult);
        }
        service.delete(id,authentication.getName());
        serviceResult.setMessage("Xoá chức năng thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<ServiceResult> roles(Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        int personnelLevel = ((UserSecurity) authentication.getPrincipal()).getPersonnelLevel();
        serviceResult.setData(converter.toRolesDtoIdAndName(
                personnelLevel==1?service.roles():service.rolesByPersonnelLevel(personnelLevel)));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/roles-by-level")
    public ResponseEntity<ServiceResult> rolesByLevel(@RequestParam("level") int level){
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(converter.toRolesDtoIdAndName(service.rolesByPersonnelLevel(level)));
        return ResponseEntity.ok(serviceResult);
    }
}
