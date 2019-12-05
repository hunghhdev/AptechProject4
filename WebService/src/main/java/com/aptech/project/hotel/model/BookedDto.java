package com.aptech.project.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookedDto{
    private int id;
    private float totalPriceExpected;
    private String roomCode;
    private Date fromDate;
    private Date toDate;
    private String customerName;
    private String customerPhone;
    private String identification;
}
