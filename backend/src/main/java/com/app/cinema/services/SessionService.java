package com.app.cinema.services;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.cinema.controllers.requestbody.SessionRequest;
import com.app.cinema.models.Cinema;
import com.app.cinema.models.Hall;
import com.app.cinema.models.Movie;
import com.app.cinema.models.Session;
import com.app.cinema.repositories.SessionRepository;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    CinemaService cinemaService;

    @Autowired
    HallService hallService;

    public List<Session> getAllSessions() {
        return (List<Session>) sessionRepository.findAll();
    }

    public Session getSession(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public List<Session> getSessionsByMovie(Long movieId) {
        Movie movie = movieService.getMovie(movieId);
        return sessionRepository.findByMovie(movie);
    }

    public Session createSession(SessionRequest sessionRequest) {
        Movie movie = movieService.getMovie(sessionRequest.getMovieId());
        Cinema cinema = cinemaService.getCinema(sessionRequest.getCinemaId());
        Hall hall = hallService.getHall(sessionRequest.getHallId());
        Session session = new Session();
        session.setCinema(cinema);
        session.setHall(hall);
        session.setMovie(movie);
        session.setStart(sessionRequest.getStart());
        Long end = sessionRequest.getStart().toEpochMilli() + movie.getTimeDuration().toMillis();
        session.setEnd(Instant.ofEpochMilli(end));
        sessionRepository.save(session);
        return session;
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public void save(Session session) {
        sessionRepository.save(session);
    }

}
