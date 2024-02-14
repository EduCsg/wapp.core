package com.wapp.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserMetadataDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    private Float weight;
    private Integer height;
    private Float bodyFat;
    private String gender;
    private Integer age;
    private String insertedAt;

}
