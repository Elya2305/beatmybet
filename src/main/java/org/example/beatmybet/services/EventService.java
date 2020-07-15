package org.example.beatmybet.services;

import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.exceptions.NotFoundException;
import org.example.beatmybet.repositories.CategoryRepository;
import org.example.beatmybet.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

@Service
public class EventService {

    @Autowired EventRepository eventRepository;
    @Autowired CategoryRepository categoryRepository;

    public Set<EventDTO> getAllEvents() {
        return eventRepository.findAllByOrderByDateDesc()
                .stream()
                .map(mapToEventDTO)
                .collect(toSet());
    }

    //add user
    public void addEvent(EventDTO eventDto) {
        Event event = new Event(
                categoryRepository.findByName(eventDto.getCategory()),
                new Date(),
                eventDto.getContent(),
                eventDto.getDate_stop(),
                eventDto.getDate_end()
        );

        System.out.println("Event: "  + event);
    }

    public EventDTO getEventById(Long id) {
        return mapToEventDTO.apply(eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("event ", id)));
    }

    public List<EventDTO> getTop3EventDTO(){

        return null;
    }

    Function<Event, EventDTO> mapToEventDTO = (event -> EventDTO.builder()
            .category(event.getCategory().getName())
            .superCategory(event.getCategory().getCategory().getName())
            .date_stop(event.getDateStop())
            .content(event.getName())
            .amountOfBids(eventRepository.countAmountOfBids(event.getId()))
            .build());
}
