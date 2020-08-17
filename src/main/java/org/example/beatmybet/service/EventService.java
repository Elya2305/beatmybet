package org.example.beatmybet.service;

import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.CategoryRepository;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    public Set<EventDTO> getAllEvents() {
        return eventRepository.findAllByOrderByDateDesc()
                .stream()
                .map(mapToEventDTO)
                .collect(toSet());
    }

    //add user
    public void addEvent(EventDTO eventDto) {
        Event event = new Event();

        event.setName(eventDto.getContent());
        event.setCategory(categoryRepository.findByName(eventDto.getCategory()));
        event.setUser(userRepository.findById(1L).get());
        event.setDateEnd(eventDto.getDataEnd());
        event.setDateStop(eventDto.getDateStop());

        System.out.println("Event: " + event);

        eventRepository.save(event);
    }

    public EventDTO getById(Long id) {
        return mapToEventDTO.apply(eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("event", id)));
    }

    Function<Event, EventDTO> mapToEventDTO = (event -> EventDTO.builder()
            .id(event.getId())
            .category(event.getCategory().getName())
            .superCategory(event.getCategory().getCategory().getName())
            .dataEnd(event.getDateEnd())
            .dateStop(event.getDateStop())
            .content(event.getName())
            .amountOfBids(eventRepository.countAmountOfBids(event.getId()))
            .build());
}
