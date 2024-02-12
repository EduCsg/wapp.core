package com.wapp.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserModel {

    private String id;
    private String username;
    private String name;
    private String email;
    private String token;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String identification;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
