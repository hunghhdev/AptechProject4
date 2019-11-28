package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter@Setter
public class RoomDto extends BaseDto {
    private String code;
    private String type;
    private int size;
    private int price;
    private int hourlyPrice;
    private String status;
    private Set<Integer> supplies;
    private String description;
}
