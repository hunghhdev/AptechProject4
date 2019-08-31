package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter@Setter
@Table(name = "t_personnel_level")
public class PersonnelLevel extends BaseEntity {
    private String levelName;
}
