package org.example.beatmybet.service.impl;

import org.example.beatmybet.dto.*;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.*;
import org.example.beatmybet.service.BidService;
import org.example.beatmybet.service.EventService;
import org.example.beatmybet.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private static final int SIZE_OF_PAGE = 2;

    private TermService termService;

    private BidService bidService;

    private EventRepository eventRepository;

    private CategoryRepository categoryRepository;

    private UserRepository userRepository;

    @Autowired
    public EventServiceImpl(TermService termService, BidService bidService, EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.termService = termService;
        this.bidService = bidService;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    //TODO add user
    public void addEvent(MainEventDTO eventDto) {
        Event event = new Event();

        event.setName(eventDto.getContent());
        event.setCategory(categoryRepository.findByName(eventDto.getCategory()));
        event.setUser(userRepository.findById(1L).get());
        event.setDateEnd(eventDto.getDataEnd().atStartOfDay());
        event.setDateStop(eventDto.getDateStop().atStartOfDay());

        eventRepository.save(event);
    }

    public List<HomeEventDTO> getAllEventWithMostPopularBid(Event.Order order, int page) {
        return sortBy(eventRepository.findAll()
                .stream()
                .map(this::getHomeEventDto)
                .collect(Collectors.toList()), order, page);
    }

    public HomeEventDTO getHomeEventDto(Event event) {
        HomeEventDTO eventDTO = new HomeEventDTO();
        setBasicsAttributes(event, eventDTO);
        Term popularTerm = termService.mostPopularTermByEvent(event);
        if(popularTerm != null) {
            eventDTO.setTitleTerm(popularTerm.getName());
            eventDTO.setBids(bidService.homeBidDTOsByTerm(popularTerm));
        }
        return eventDTO;
    }

    private List<HomeEventDTO> sortBy(List<HomeEventDTO> list, Event.Order order, int page) {
        switch (order) {
            case date:
                list.sort((Comparator.comparing(HomeEventDTO::getDateStop)));
                break;
            case popular:
                list.sort((Comparator.comparingInt(HomeEventDTO::getAmountOfBids)).reversed());
        }
        int first = SIZE_OF_PAGE * (page - 1);
        int last = Math.min(SIZE_OF_PAGE * (page - 1) + 2, list.size());
        return first >= list.size() ? List.of() : list.subList(first, last);
    }

    public MainEventDTO termsByEvent(long id) {
        Optional<Event> eventEntity = eventRepository.findById(id);
        if (eventEntity.isPresent()) {
            Event event = eventRepository.getOne(id);
            MainEventDTO mainEventDTO = new MainEventDTO();
            setBasicsAttributes(event, mainEventDTO);
            mainEventDTO.setAllTerms(termService.allTermsByEvent(event));
            return mainEventDTO;
        } else {
            throw new NotFoundException("event", id);
        }
    }


    private void setBasicsAttributes(Event event, BaseEventDTO baseEventDTO) {
        baseEventDTO.setId(event.getId());
        baseEventDTO.setCategory(event.getCategory().getName());
        baseEventDTO.setSuperCategory(event.getCategory().getCategory().getName());
        baseEventDTO.setDateStop(event.getDateStop().toLocalDate());
        baseEventDTO.setContent(event.getName());
        baseEventDTO.setAmountOfBids(eventRepository.countAmountOfBids(event.getId()));
    }
}
