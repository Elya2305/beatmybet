package org.example.beatmybet.controllers;

import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired private EventRepository eventRepository;

    @GetMapping("/all")
    public Set<EventDTO> getAllEvents(){
        return eventRepository.findAll().stream().map(mapToEventDTO).collect(toSet());
    }

    Function<Event, EventDTO> mapToEventDTO = (event -> EventDTO.builder()
            .category(event.getCategory().getName())
            .superCategory(event.getCategory().getCategory().getName())
            .date_start(event.getDate())
            .date_stop(event.getDateStop())
            .id(event.getId())
            .build());

}
