package com.app.cinema.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cinema.exceptions.MessageException;
import com.app.cinema.models.User;
import com.app.cinema.security.AuthExc;
import com.app.cinema.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok().body(userService.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") Long id) {
        try {
            User user = userService.getUser(id);
            if (user != null) {
                return ResponseEntity.ok().body(user);
            } else {
                return new ResponseEntity<>(new AuthExc("Не найдено"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/admin/ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable(name = "id") Long id) {
        try {
            userService.banUser(id);
            return ResponseEntity.ok().body("Пользователь забанен");
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable(name = "id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("Удалено");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
