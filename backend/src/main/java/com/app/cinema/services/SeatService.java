package com.app.cinema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.cinema.models.Seat;
import com.app.cinema.repositories.SeatRepository;

@Service
public class SeatService {
    @Autowired
    SeatRepository seatRepository;

    public Seat getSeat(Long id) {
        return seatRepository.findById(id).orElse(null);
    }
}
