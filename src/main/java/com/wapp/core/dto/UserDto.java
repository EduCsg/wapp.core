package com.wapp.core.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private Double weight;
    private Double height;
    private Double bodyFat;
    private String gender;
    private Integer age;
    private String createdAt;

    private String username;
    private String name;
    private String email;

}
