package com.aptech.project.hotel.util;

public class StringUtils {

    public int[] toArraysInteger(String[] s){
        int length = s.length;
        final int[] ints = new int[length];
        for ( int i=0; i < length; i++) {
            ints[i] = Integer.parseInt(s[i]);
        }
        return ints;
    }
}
