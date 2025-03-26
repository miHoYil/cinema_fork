package com.app.cinema.controllers;

import java.util.List;

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

import com.app.cinema.controllers.requestbody.SessionRequest;
import com.app.cinema.models.Session;
import com.app.cinema.security.AuthExc;
import com.app.cinema.services.SessionService;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    @Autowired
    SessionService sessionService;

    @GetMapping("")
    public ResponseEntity<?> getAllSessions() {
        try {
            List<Session> session = sessionService.getAllSessions();
            return ResponseEntity.ok().body(session);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSession(@PathVariable(name = "id") Long id) {
        try {
            Session session = sessionService.getSession(id);
            if (session != null) {
                return ResponseEntity.ok().body(session);
            } else {
                return new ResponseEntity<>(new AuthExc("Не найдено"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-movie/{movieId}")
    public ResponseEntity<?> getSessionsByMovie(@PathVariable(name = "movieId", required = true) Long movieId) {
        try {
            List<Session> session = sessionService.getSessionsByMovie(movieId);
            return ResponseEntity.ok().body(session);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createSession(@RequestBody SessionRequest sessionRequest) {
        try {
            Session session = sessionService.createSession(sessionRequest);
            return ResponseEntity.ok().body(session);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable(name = "id") Long id) {
        try {
            sessionService.deleteSession(id);
            return ResponseEntity.ok().body("Удалено");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
