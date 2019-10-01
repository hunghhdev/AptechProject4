package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_supplies")
public class Supplies extends BaseEntity {
    private String name;
    private int branchId;
}
