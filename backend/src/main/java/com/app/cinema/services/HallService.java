package com.app.cinema.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.cinema.controllers.requestbody.HallRequest;
import com.app.cinema.models.Cinema;
import com.app.cinema.models.Hall;
import com.app.cinema.models.Seat;
import com.app.cinema.repositories.HallRepository;
import com.app.cinema.repositories.SeatRepository;

@Service
public class HallService {

    @Autowired
    HallRepository hallRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    CinemaService cinemaService;

    public List<Hall> getAllHalls() {
        return (List<Hall>) hallRepository.findAll();
    }

    public Hall getHall(Long id) {
        return hallRepository.findById(id).orElse(null);
    }

    public void deleteHall(Long id) {
        hallRepository.deleteById(id);
    }

    public Hall createHall(HallRequest hallRequest) {
        Hall hall = new Hall();
        hall.setNumber(hallRequest.getNumber());
        Cinema cinema = cinemaService.getCinema(hallRequest.getCinemaId());
        hall.setCinema(cinema);
        hall = hallRepository.save(hall);
        if (hallRequest.getMapRowToSeatsNumber() != null) {
            for (Map.Entry<Integer, Integer> entry : hallRequest.getMapRowToSeatsNumber().entrySet()) {
                for (int i = 1; i <= entry.getValue(); i++) {
                    Seat seat = new Seat();
                    seat.setHall(hall);
                    seat.setRowNumber(entry.getKey());
                    seat.setNumber(i);
                    seatRepository.save(seat);
                }
            }
        }
        return hallRepository.save(hall);
    }

}
