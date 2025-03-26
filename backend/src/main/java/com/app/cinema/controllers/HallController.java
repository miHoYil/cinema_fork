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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.cinema.controllers.requestbody.HallRequest;
import com.app.cinema.models.Hall;
import com.app.cinema.security.AuthExc;
import com.app.cinema.services.HallService;

@RestController
@RequestMapping("/api/halls")
public class HallController {
    @Autowired
    HallService hallService;

    @GetMapping("")
    public ResponseEntity<?> getAllHalls() {
        try {
            return ResponseEntity.ok().body(hallService.getAllHalls());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHall(@PathVariable(name = "id") Long id) {
        try {
            Hall hall = hallService.getHall(id);
            if (hall != null) {
                return ResponseEntity.ok().body(hall);
            } else {
                return new ResponseEntity<>(new AuthExc("Не найдено"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createHall(@RequestBody HallRequest hallRequest,
            @RequestParam(required = false) Long movieId) {
        try {
            Hall hall = hallService.createHall(hallRequest);
            return ResponseEntity.ok().body(hall);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable(name = "id") Long id) {
        try {
            hallService.deleteHall(id);
            return ResponseEntity.ok().body("Удалено");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
