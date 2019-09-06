package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.model.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleConverter {

    @Autowired
    private PermissionConverter permissionConverter;

    public RoleDto toRoleDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setDescription(role.getDescription());
        roleDto.setPermissions(permissionConverter.toPermissionsDto(role.getPermissions()));
        roleDto.setCreatedBy(role.getCreatedBy());
        roleDto.setCreatedDate(role.getCreatedDate());
        roleDto.setPersonnelLevel(role.getPersonnelLevel());
        return roleDto;
    }

    public List<RoleDto> toRolesDto(List<Role> roles){
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach(role -> roleDtos.add(toRoleDto(role)));
        return roleDtos;
    }

    public List<RoleDto> toRolesDtoIdAndName(List<Role> roles){
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setRoleName(role.getRoleName());
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    public Role toRole(RoleDto roleDto){
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setRoleName(roleDto.getRoleName());
        role.setDescription(roleDto.getDescription());
        role.setPermissions(permissionConverter.toPermissions(roleDto.getPermissions()));
        role.setPersonnelLevel(roleDto.getPersonnelLevel());
        return role;
    }
}
