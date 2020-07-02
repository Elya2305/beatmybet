package org.example.beatmybet.services;

import org.example.beatmybet.entity.User;
import org.example.beatmybet.exceptions.NotFoundException;
import org.example.beatmybet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user", id));
    }
}
