package org.example.beatmybet.service;

import org.example.beatmybet.dto.EventCreateUpdateDto;
import org.example.beatmybet.dto.HomeEventDTO;
import org.example.beatmybet.dto.MainEventDto;
import org.example.beatmybet.entity.Event;

import java.util.List;

public interface EventService {
    boolean create(EventCreateUpdateDto eventCreateDto);

    List<HomeEventDTO> getAllEventWithMostPopularBid(Event.Order order, int page);

    HomeEventDTO getHomeEventDto(Event event);

    MainEventDto termsByEvent(long id);

    boolean update(EventCreateUpdateDto eventCreateDto);
}
