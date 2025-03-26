package com.app.cinema.controllers.requestbody;

import lombok.Getter;
import java.time.Instant;

@Getter
public class OrdersRequest {
    Instant start;
    Instant end;
}
