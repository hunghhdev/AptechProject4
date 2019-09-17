package com.aptech.project.hotel.model;

import com.aptech.project.hotel.entity.Permission;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserInfoDto {
    private int id;
    private String username;
    private String avatar;
    @JsonIgnoreProperties({"id","children","permissionName"})
    private Set<String> permissions;
}
