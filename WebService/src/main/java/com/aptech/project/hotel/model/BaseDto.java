package com.aptech.project.hotel.model;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public abstract class BaseDto {
    @Id
    private int id;
    private Date createdDate;
    private String createdBy;

}
