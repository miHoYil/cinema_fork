package com.app.cinema.controllers.requestbody;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;

    private String password;
}
