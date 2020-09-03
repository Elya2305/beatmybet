package org.example.beatmybet.controller;

import org.example.beatmybet.dto.BaseEventDTO;
import org.example.beatmybet.dto.MainEventDTO;
import org.example.beatmybet.dto.ResponseStatusDto;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/all/{order}")
    public List<? extends BaseEventDTO> getAllEvents(@PathVariable String order, @RequestParam int page) {
        return eventService.getAllEventWithMostPopularBid(Event.Order.valueOf(order), page);
    }

    @PostMapping("/add") //@Principal user
    public ResponseStatusDto addEvent(MainEventDTO eventDto) {
        eventService.addEvent(eventDto);
        return new ResponseStatusDto(HttpStatus.OK.value(),
                "new event " + eventDto.getContent() + " added");
    }

    @GetMapping("/{id}")
    public MainEventDTO termsByEvent(@PathVariable long id) {
        return eventService.termsByEvent(id);
    }
}
