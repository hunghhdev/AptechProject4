package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "t_department")
public class Department extends BaseEntity {
    @Column(name = "department_name", nullable = false)
    private String departmentName;
}
