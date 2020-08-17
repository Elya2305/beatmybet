package org.example.beatmybet.controller;

import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.dto.ResponseStatusDto;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/all")
    public Set<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable("id") Long id) {
        return eventService.getById(id);
    }

    @PostMapping("/add") //@Principal user
    public ResponseStatusDto addEvent(EventDTO eventDto) {
        eventService.addEvent(eventDto);
        return new ResponseStatusDto(HttpStatus.OK.value(),
                "new event " + eventDto.getContent() + " added");
    }
}
