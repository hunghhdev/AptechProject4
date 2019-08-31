package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role_id", nullable = false)
    private int roleId;

    @Column(name = "jwt_key")
    private String jwtKey;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "personnel_level")
    private int personnelLevel;

    @Column(name = "branch_place_id")
    private int branchPlaceId;
}
