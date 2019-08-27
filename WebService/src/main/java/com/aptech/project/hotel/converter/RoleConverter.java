package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.model.RoleDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleConverter {

    public RoleDto toRoleDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setDescription(role.getDescription());
        roleDto.setPermissions(role.getPermissions());
        roleDto.setCreatedBy(role.getCreatedBy());
        roleDto.setCreatedDate(role.getCreatedDate());
        return roleDto;
    }

    public List<RoleDto> toRolesDto(List<Role> roles){
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setRoleName(role.getRoleName());
            roleDto.setDescription(role.getDescription());
            roleDto.setPermissions(role.getPermissions());
            roleDto.setCreatedBy(role.getCreatedBy());
            roleDto.setCreatedDate(role.getCreatedDate());

            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    public Role toRole(RoleDto roleDto){
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setRoleName(roleDto.getRoleName());
        role.setDescription(roleDto.getDescription());
        role.setPermissions(roleDto.getPermissions());
        return role;
    }
}
