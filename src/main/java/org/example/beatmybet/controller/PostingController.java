package org.example.beatmybet.controller;

import org.example.beatmybet.entity.financy.Posting;
import org.example.beatmybet.repository.JournalRepository;
import org.example.beatmybet.repository.PostingRepository;
import org.example.beatmybet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/posting")
public class PostingController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JournalRepository journalRepository;
    @Autowired
    PostingRepository postingRepository;

    @GetMapping("/get")
    public Posting add(){
        Posting posting = new Posting(
                userRepository.findById(1L).get(),
                200,
                journalRepository.findById(1L).get()
        );
    return postingRepository.save(posting);
    }
}
