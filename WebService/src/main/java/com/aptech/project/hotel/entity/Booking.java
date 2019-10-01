package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_booking")
@Getter@Setter
public class Booking extends BaseEntity {

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "total_date")
    int totalDate;

    @Column(name = "total_price")
    int totalPrice;

    @Column(name = "room_id")
    int roomId;

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "customer_phone")
    String customerPhone;
}
