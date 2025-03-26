package com.app.cinema.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.cinema.models.Hall;

@Repository
public interface HallRepository extends CrudRepository<Hall, Long> {

}
