package com.app.cinema.controllers.requestbody;

import java.util.List;

import lombok.Getter;

@Getter
public class CinemaRequest {
    private String name;
    private String address;
    private List<Long> listMoviesId;
}
