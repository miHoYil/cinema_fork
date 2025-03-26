package com.app.cinema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.cinema.exceptions.MessageException;
import com.app.cinema.models.User;
import com.app.cinema.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void banUser(Long id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setIsBanned(!user.getIsBanned());
            userRepository.save(user);
        } else {
            throw new MessageException("Пользователь не найден");
        }
    }
}