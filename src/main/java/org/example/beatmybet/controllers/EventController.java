package org.example.beatmybet.controllers;

import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired EventService eventService;

    @GetMapping("/all")
    public Set<EventDTO> getAllEvents(){
        return eventService.getAllEvents();
    }

    @PostMapping("/add") //@Principal user
    public EventDTO addEvent(@RequestBody EventDTO eventDto){
        eventService.addEvent(eventDto);
        System.out.println(eventDto);
/*        Event event = new Event(
                categoryRepository.findByName(eventDto.getCategory()),
                eventDto.getDate(),
                eventDto.getName(),
                eventDto.getDate_stop(),
                eventDto.getDate_end()
        );
        System.out.println(event);*/
        return eventDto;
    }


}
