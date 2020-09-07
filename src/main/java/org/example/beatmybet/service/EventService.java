package org.example.beatmybet.service;

import org.example.beatmybet.dto.BaseEventDTO;
import org.example.beatmybet.dto.HomeEventDTO;
import org.example.beatmybet.dto.MainEventDTO;
import org.example.beatmybet.entity.Event;

import java.util.List;

public interface EventService {
    void addEvent(MainEventDTO eventDto);

    List<HomeEventDTO> getAllEventWithMostPopularBid(Event.Order order, int page);

    HomeEventDTO getHomeEventDto(Event event);

    MainEventDTO termsByEvent(long id);
}
