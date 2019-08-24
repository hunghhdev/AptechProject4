package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc

/**
 * Gets the object.
 *
 * @return the object
 */
@Getter

/**
 * Sets the object.
 *
 * @param object the new object
 */
@Setter
public class Data {
    
    /** The count row. */
    private int countRow;
    
    /** The object. */
    private Object object;

    /**
     * Instantiates a new data.
     *
     * @param countRow the count row
     * @param object the object
     */
    public Data(int countRow, Object object) {
        this.countRow = countRow;
        this.object = object;
    }
}
