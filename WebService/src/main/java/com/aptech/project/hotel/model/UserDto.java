package com.aptech.project.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private int id;
    private String fullName;
    private String username;
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private int roleId;
    private String createdBy;
    private Date createdDate;
    private String avatar;
    private int personnelLevel;
    private int branchPlaceId;
}
