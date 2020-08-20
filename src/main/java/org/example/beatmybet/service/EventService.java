package org.example.beatmybet.service;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EventService {

    private static final int SIZE_OF_PAGE = 2;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TermRepository termRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    UserRepository userRepository;

    //add user
    public void addEvent(EventDTO eventDto) {
        Event event = new Event();

        event.setName(eventDto.getContent());
        event.setCategory(categoryRepository.findByName(eventDto.getCategory()));
        event.setUser(userRepository.findById(1L).get());
        event.setDateEnd(eventDto.getDataEnd());
        event.setDateStop(eventDto.getDateStop());

        eventRepository.save(event);
    }

    public EventDTO getById(Long id) {
        return mapToEventDTO.apply(eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("event", id)));
    }

    public List<EventDTO> getAllEventWithMostPopularBid(Event.Order order, int page) {
        List<EventDTO> list = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for (Event event : allEvents) {
            EventDTO eventDTO = mapToEventDTO.apply(event);
            List<Long> idTerm = event.getTerms().stream()
                    .map(Term::getId)
                    .collect(Collectors.toList());
            if (idTerm.size() != 0) {
                eventDTO.setTerm(mostPopular(termRepository.getMostPopularTermFromTerms(idTerm)[0]));
            }
            list.add(eventDTO);
        }
        return sortBy(list, order, page);
    }

    private List<EventDTO> sortBy(List<EventDTO> list, Event.Order order, int page) {
        switch (order) {
            case date:
                list.sort((Comparator.comparing(EventDTO::getDateStop)).reversed());
            case popular:
                list.sort((Comparator.comparingInt(EventDTO::getAmountOfBids)));
        }
        int first = SIZE_OF_PAGE * (page - 1);
        int last = Math.min(SIZE_OF_PAGE * (page - 1) + 2, list.size());
        return first >= list.size() ? List.of() : list.subList(first, last);
    }

    private TermDTO mostPopular(Long termId) {
        if (termRepository.findById(termId).isPresent()) {
            Term mostPopularTerm = termRepository.getOne(termId);
            List<BidDTO> bidDTOList = mostPopularTerm.getVariants()
                    .stream()
                    .map(o -> BidDTO.builder()
                            .variant(o.getName())
                            .koef(bidRepository.getBestKoefByTermVarID(o.getId()))
                            .build())
                    .collect(Collectors.toList());
            return TermDTO.builder()
                    .title(mostPopularTerm.getName())
                    .bids(bidDTOList)
                    .build();
        }
        return null;
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
