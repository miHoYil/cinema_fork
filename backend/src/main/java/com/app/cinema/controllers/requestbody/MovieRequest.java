package com.app.cinema.controllers.requestbody;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Duration;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {
    private String name;
    private Duration timeDuration;
}
