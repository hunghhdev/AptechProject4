package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_customer")
@Getter@Setter
public class Customer extends BaseEntity {

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "phone_number", length = 30)
    String phoneNumber;

    @Column(name = "identification_number", length = 30)
    String identificationNumber;
}
