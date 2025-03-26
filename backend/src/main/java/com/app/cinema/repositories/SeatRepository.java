package com.app.cinema.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.cinema.models.Seat;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Long> {

}
