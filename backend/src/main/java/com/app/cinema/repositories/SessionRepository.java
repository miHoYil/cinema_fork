package com.app.cinema.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.cinema.models.Movie;
import com.app.cinema.models.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    List<Session> findByMovie(Movie movie);

}
