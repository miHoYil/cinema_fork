package com.app.cinema.controllers.requestbody;

import java.time.Instant;

import lombok.Getter;

@Getter
public class SessionRequest {
    private Instant start;

    private Long movieId;

    private Long cinemaId;

    private Long hallId;
}