package com.app.cinema.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.cinema.models.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Set<Movie> findAllById(Iterable<Long> id);

    List<Movie> findAll();
}
