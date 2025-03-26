package com.app.cinema.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cinema.controllers.requestbody.CinemaRequest;
import com.app.cinema.models.Cinema;
import com.app.cinema.security.AuthExc;
import com.app.cinema.services.CinemaService;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {
    @Autowired
    CinemaService cinemaService;

    @GetMapping("")
    public ResponseEntity<?> getAllCinemas() {
        try {
            return ResponseEntity.ok().body(cinemaService.getAllCinemas());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCinema(@PathVariable(name = "id") Long id) {
        try {
            Cinema cinema = cinemaService.getCinema(id);
            if (cinema != null) {
                return ResponseEntity.ok().body(cinema);
            } else {
                return new ResponseEntity<>(new AuthExc("Не найдено"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createCinema(@RequestBody CinemaRequest cinemaRequest) {
        try {
            Cinema cinema = cinemaService.createCinema(cinemaRequest);
            return ResponseEntity.ok().body(cinema);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteCinema(@PathVariable(name = "id") Long id) {
        try {
            cinemaService.deleteCinema(id);
            return ResponseEntity.ok().body("Удалено");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
