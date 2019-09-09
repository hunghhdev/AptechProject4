package com.aptech.project.hotel.util;

import java.util.Date;

public class Constant {
    public static final String API = "/api";

    public static final long MIN_DATE_MYSQL = -30609817200000l;
    public static final long MAX_DATE_MYSQL = 253402275599000l;

    public static Date minDate(long date){
        return new Date(date==0?MIN_DATE_MYSQL:date);
    }

    public static Date maxDate(long date){
        return new Date(date==0?MAX_DATE_MYSQL:date);
    }

    private Constant() {throw new IllegalStateException("Constant class");}
}
