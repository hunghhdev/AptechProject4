package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDto {
    private int id;
    private String permissionName;
    private String permissionKey;
    private int parentId;

    public PermissionDto(int id, String permissionName, String permissionKey, int parentId) {
        this.id = id;
        this.permissionName = permissionName;
        this.permissionKey = permissionKey;
        this.parentId = parentId;
    }

    public PermissionDto() {
    }
}
