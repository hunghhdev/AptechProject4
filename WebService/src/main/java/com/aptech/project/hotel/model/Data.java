package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

@Getter

@Setter
public class Data {
    
    private int countRow;
    
    private Object object;

    public Data(int countRow, Object object) {
        this.countRow = countRow;
        this.object = object;
    }
}
