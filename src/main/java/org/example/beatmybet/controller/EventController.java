package org.example.beatmybet.controller;

import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.dto.ResponseStatusDto;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/all/{order}")
    public List<EventDTO> getAllEvents(@PathVariable String order, @RequestParam int page) {
        return eventService.getAllEventWithMostPopularBid(Event.Order.valueOf(order), page);
    }

    @GetMapping
    public List<Integer> sub() {

        return List.of(1,2,3,4).subList(0,2);
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
