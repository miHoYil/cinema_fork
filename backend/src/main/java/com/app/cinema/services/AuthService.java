package com.app.cinema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.cinema.controllers.requestbody.SignInRequest;
import com.app.cinema.controllers.requestbody.SignUpRequest;
import com.app.cinema.models.User;
import com.app.cinema.repositories.UserRepository;
import com.app.cinema.security.UserDetailsServiceImpl;
import com.app.cinema.security.jwt.JwtUtils;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtUtils jwtUtils;

    public String loginUser(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(signInRequest.getUsername());
        String token = jwtUtils.generateJwtToken(userDetails);
        return token;
    }

    public User registerUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        if (signUpRequest.getUsername().equals("admin")) {
            user.setRole("ROLE_ADMIN");
        } else {
            user.setRole("ROLE_USER");
        }
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        return userRepository.save(user);
    }

}
