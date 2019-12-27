package com.aptech.project.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class UsingDto {
    private int id;
    private int userId;
    private String customerName;
    private String customerPhone;
    private String identification;
    private int roomId;
    private String roomCode;
    private int price;
    private int hourlyPrice;
    private Date fromDate;
    private Date toDate;
    private Date checkInTime;
    private Date checkOutTime;
    private float totalPriceExpected;
    private float totalPriceReality;
    private int status;
    private boolean type;
}
