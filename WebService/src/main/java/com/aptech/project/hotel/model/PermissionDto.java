package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDto {
    private int id;
    private String permissionName;
    private String permissionKey;
    private int parent_id;

    public PermissionDto(int id, String permissionName, String permissionKey, int parent_id) {
        this.id = id;
        this.permissionName = permissionName;
        this.permissionKey = permissionKey;
        this.parent_id = parent_id;
    }

    public PermissionDto() {
    }
}
