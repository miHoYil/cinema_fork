package com.app.cinema.controllers.requestbody;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;

    private String password;
}
