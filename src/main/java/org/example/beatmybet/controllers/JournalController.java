package org.example.beatmybet.controllers;

import org.example.beatmybet.entity.financy.Journal;
import org.example.beatmybet.repositories.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    JournalRepository journalRepository;

    @GetMapping("/{id}")
    public Journal get(@PathVariable("id") Long id){
        return journalRepository.findById(id).get();
    }
}
