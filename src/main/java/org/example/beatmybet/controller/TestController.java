package org.example.beatmybet.controller;

import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.TermRepository;
import org.example.beatmybet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    TermRepository termRepository;

    @Autowired
    EventService eventService;
}
