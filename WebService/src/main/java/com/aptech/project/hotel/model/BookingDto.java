package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class BookingDto extends BaseDto {
    private Date fromDate;
    private Date toDate;
    private int count;
    private int roomId;
    private int customerId;
    private String nameCus;
    private String phoneCus;
    private String identification;


}
