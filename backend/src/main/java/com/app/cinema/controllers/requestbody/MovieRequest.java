package com.app.cinema.controllers.requestbody;

import lombok.Getter;
import java.time.Duration;

@Getter
public class MovieRequest {
    private String name;
    private Duration timeDuration;
}
