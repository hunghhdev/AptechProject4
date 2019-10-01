package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class BookingDto extends BaseDto {
    Date fromDate;
    Date toDate;
    int days;
    int count;
    int roomId;
    String nameCus;
    String phoneCus;
}
