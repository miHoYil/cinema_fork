package com.app.cinema.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.cinema.models.Cinema;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Long> {

}
