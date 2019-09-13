package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RoomDto extends BaseDto {
    private String name;
    private String code;
    private int branchId;
    private int price;
    private String status;
    private String[] supplies;
    private String description;
}
