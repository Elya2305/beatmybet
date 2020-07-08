package org.example.beatmybet.controllers;

import org.example.beatmybet.entity.financy.Journal;
import org.example.beatmybet.entity.financy.Posting;
import org.example.beatmybet.repositories.JournalRepository;
import org.example.beatmybet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posting")
public class PostingController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JournalRepository journalRepository;

    @GetMapping("/get")
    public Posting add(){
        Posting posting = new Posting(
                userRepository.findById(1L).get(),
                200,
                journalRepository.findById(1L).get()
        );
        System.out.println(posting);
        return posting;
    }
}
