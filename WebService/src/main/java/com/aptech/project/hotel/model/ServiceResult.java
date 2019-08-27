package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResult {
    private String message;
    private Object data;
    private Status status = Status.SUCCESS;
    public enum Status {
        SUCCESS, FAILED, TOKEN_FAIl
    }
}
