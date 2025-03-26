package com.app.cinema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.cinema.controllers.requestbody.CinemaRequest;
import com.app.cinema.models.Cinema;
import com.app.cinema.models.Movie;
import com.app.cinema.repositories.CinemaRepository;

import java.util.List;
import java.util.Set;

@Service
public class CinemaService {
    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    MovieService movieService;

    public Cinema getCinema(Long id) {
        return cinemaRepository.findById(id).orElse(null);
    }

    public List<Cinema> getAllCinemas() {
        // List<Cinema> c = (List<Cinema>) cinemaRepository.findAll();
        // System.out.println(c.get(1).getSetOfMovies());
        return (List<Cinema>) cinemaRepository.findAll();
    }

    public Cinema createCinema(CinemaRequest cinemaRequest) {
        Cinema cinema = new Cinema();
        cinema.setName(cinemaRequest.getName());
        cinema.setAddress(cinemaRequest.getAddress());
        if (cinemaRequest.getListMoviesId() != null) {
            Set<Movie> movies = movieService.getAllMoviesById(cinemaRequest.getListMoviesId());
            System.out.println(movies);
            cinema.setSetOfMovies(movies);
        }
        return cinemaRepository.save(cinema);
    }

    public void deleteCinema(Long id) {
        cinemaRepository.deleteById(id);
    }
}
