package com.aptech.project.hotel.util;

import java.util.Date;

public class Constant {
    public static final String API = "/api";

    private static final long MIN_DATE_MYSQL = -30609817200000l;
    private static final long MAX_DATE_MYSQL = 253402275599000l;
    public static final String ROOM_STATUS_BOOKED = "Maintenance";
    public static final String ROOM_STATUS_ACTIVE = "Active";
    public static final int BOOKING_STATUS_BOOKED = 1;
    public static final int BOOKING_STATUS_CHECKIN = 2;
    public static final int BOOKING_STATUS_CHECKOUT = 3;
    public static final int BOOKING_STATUS_CANCEL = 3;
    public static final long TIME_2_HOUR = 7200000l;

    public static Date minDate(long date){
        return new Date(date==0?MIN_DATE_MYSQL:date);
    }

    public static Date maxDate(long date){
        return new Date(date==0?MAX_DATE_MYSQL:date);
    }

    private Constant() {throw new IllegalStateException("Constant class");}
}
