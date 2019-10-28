package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_supplies")
public class Supplies extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private int quantity = 0;
    @Column(name = "note")
    private String note;
    @Column(name = "used")
    private int used;
}
