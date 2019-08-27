package com.aptech.project.hotel.model;

import com.aptech.project.hotel.entity.Permission;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter@Getter
public class RoleDto {
    private int id;
    private String roleName;
    private String description;
    @JsonIgnoreProperties({"createdDate","createdBy","deleted","description","updatedDate","updatedBy"})
    private Set<Permission> permissions;
    private String createdBy;
    private Date createdDate;
}
