package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public abstract class BaseDto {
    private int id;
    private Date createdDate;
    private String createdBy;
}
