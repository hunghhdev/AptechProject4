package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_permission")
@Getter
@Setter
public class Permission extends BaseEntity {
    @Column(name = "permission_key")
    private String permissionKey = "NULL";

    @Column(name = "description")
    private String description = "NULL";
}
