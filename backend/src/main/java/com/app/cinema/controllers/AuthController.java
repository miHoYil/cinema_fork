package com.app.cinema.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cinema.controllers.requestbody.SignInRequest;
import com.app.cinema.controllers.requestbody.SignUpRequest;
import com.app.cinema.models.User;
import com.app.cinema.repositories.UserRepository;
import com.app.cinema.security.AuthExc;
import com.app.cinema.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Этот логин уже занят");
        }
        User user = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest) {
        try {
            String token = authService.loginUser(signInRequest);
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthExc("Не правильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
    }
}
