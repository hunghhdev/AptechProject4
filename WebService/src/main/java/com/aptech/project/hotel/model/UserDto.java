package com.aptech.project.hotel.model;

import com.aptech.project.hotel.entity.Permission;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private int id;
    private String username;
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonIgnoreProperties({"createdDate","createdBy","deleted","description","updatedDate","updatedBy"})
    private Set<Permission> permission;
    private int role;
    private String createdBy;
    private Date createdDate;
    private String avatar;
}
