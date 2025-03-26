package com.app.cinema.controllers.requestbody;

import java.util.Map;

import lombok.Getter;

@Getter
public class HallRequest {
    private Integer number;

    private Long cinemaId;

    private Map<Integer, Integer> mapRowToSeatsNumber;
}
