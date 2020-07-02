package org.example.beatmybet.services;

import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.repositories.CategoryRepository;
import org.example.beatmybet.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

@Service
public class EventService {

    @Autowired EventRepository eventRepository;
    @Autowired CategoryRepository categoryRepository;

    public Set<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(mapToEventDTO).collect(toSet());
    }

    //add user
    public void addEvent(EventDTO eventDto) {
        Event event = new Event(
                categoryRepository.findByName(eventDto.getCategory()),
                new Date(),
                eventDto.getName(),
                eventDto.getDate_stop(),
                eventDto.getDate_end()
        );

        System.out.println("Event: "  + event);


    }
    Function<Event, EventDTO> mapToEventDTO = (event -> EventDTO.builder()
            .category(event.getCategory().getName())
            .superCategory(event.getCategory().getCategory().getName())
            .date_stop(event.getDateStop())
            .date_end(event.getDateEnd())
            .id(event.getId())
            .name(event.getName())
            .build());

}
