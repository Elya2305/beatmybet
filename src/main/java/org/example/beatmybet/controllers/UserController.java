package org.example.beatmybet.controllers;

import org.example.beatmybet.entity.User;
import org.example.beatmybet.exceptions.NotFoundException;
import org.example.beatmybet.repositories.UserRepository;
import org.example.beatmybet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired UserService userService;

    @GetMapping("/{id}")
    public User get(@PathVariable("id") Long id){
        User user = userService.findById(id);
        System.out.println(user);
        return user;
    }

    @GetMapping("/2/{id}")
    public User get2(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @GetMapping("/balance/{id}")
    public double getBalance(@PathVariable long id){
        return userService.getBalance(get(id));
    }
}
