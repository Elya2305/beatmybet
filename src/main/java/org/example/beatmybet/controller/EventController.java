package org.example.beatmybet.controller;

import lombok.AllArgsConstructor;
import org.example.beatmybet.dto.BaseEventDto;
import org.example.beatmybet.dto.BoolResponse;
import org.example.beatmybet.dto.EventCreateUpdateDto;
import org.example.beatmybet.dto.MainEventDto;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.service.EventService;
import org.example.beatmybet.web.ApiResponse;
import org.example.beatmybet.web.Responses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/all/{order}")
    public ApiResponse<List<? extends BaseEventDto>> getAllEvents(@PathVariable String order, @RequestParam int page) {
        return Responses.okResponse(eventService.getAllEventWithMostPopularBid(Event.Order.valueOf(order), page));
    }

    @PostMapping("/add")
    public ApiResponse<BoolResponse> addEvent(EventCreateUpdateDto eventDto) {
        boolean result = eventService.create(eventDto);
        return Responses.okResponse(BoolResponse.of(result));
    }

    @GetMapping("/{id}")
    public MainEventDto termsByEvent(@PathVariable long id) {
        return eventService.termsByEvent(id);
    }
}
