package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_customer")
@Getter@Setter
public class Customer extends BaseEntity {
    String customerName;
    String phoneNumber;
}
