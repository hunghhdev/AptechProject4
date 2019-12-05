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

    @Column(name = "total_price_expected")
    private float totalPriceExpected;

    @Column(name = "check_in_time")
    private Date checkInTime;

    @Column(name = "check_out_time")
    private Date checkOutTime;

    @Column(name = "total_price_reality")
    private float totalPriceReality;

    @Column(name = "room_id")
    private int roomId;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "status", columnDefinition="default 1")
    private int status; // 1. Da dat, 2. Dang su dung, 3. Da tra phong, 4. Huy

    @Column(name = "type")
    private boolean type; // Theo giờ/Theo ngày
}
