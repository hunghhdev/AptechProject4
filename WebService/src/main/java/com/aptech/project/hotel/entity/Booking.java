package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_booking")
@Getter@Setter
public class Booking extends BaseEntity {
    Date fromDate;
    Date toDate;
    int roomId;
    int customerId;
}
