package com.wapp.core.models;

import lombok.Data;

@Data
public class ResponseModel {
    private String message;
    private Object data;
    private Boolean success;
    private String status;
}
