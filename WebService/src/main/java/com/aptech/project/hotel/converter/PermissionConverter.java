package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.entity.Permission;
import com.aptech.project.hotel.model.PermissionDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionConverter {

    public PermissionDto toPermissionDto(Permission permission){
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(permission.getId());
        permissionDto.setPermissionKey(permission.getPermissionKey());
        permissionDto.setPermissionName(permission.getPermissionName());

        return permissionDto;
    }

    public Set<PermissionDto> toPermissionsDto(Set<Permission> permissions){
        Set<PermissionDto> permissionsDto = new HashSet<>();
        permissions.forEach(permission -> permissionsDto.add(toPermissionDto(permission)));
        return permissionsDto;
    }

    public Permission toPermission(PermissionDto permissionDto){
        Permission permission = new Permission();
        permission.setId(permissionDto.getId());
        permission.setPermissionName(permissionDto.getPermissionName());
        permission.setPermissionKey(permissionDto.getPermissionName());
        return permission;
    }

    public Set<Permission> toPermissions(Set<PermissionDto> permissionDtos){
        Set<Permission> permissions = new HashSet<>();
        permissionDtos.forEach(permissionDto -> permissions.add(toPermission(permissionDto)));

        return permissions;
    }
}
