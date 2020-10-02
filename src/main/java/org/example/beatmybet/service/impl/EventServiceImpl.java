package org.example.beatmybet.service.impl;

import lombok.AllArgsConstructor;
import org.example.beatmybet.dto.*;
import org.example.beatmybet.entity.*;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.exception.ValidationException;
import org.example.beatmybet.repository.*;
import org.example.beatmybet.service.BidService;
import org.example.beatmybet.service.CategoryService;
import org.example.beatmybet.service.EventService;
import org.example.beatmybet.service.TermService;
import org.example.beatmybet.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private static final int SIZE_OF_PAGE = 2;
    private static final long DEFAULT_BIDS_AMOUNT = 0;
    private final TermService termService;
    private final BidService bidService;
    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final User TEMPORARY_USER = userRepository.getOne(1L);


    public boolean create(EventCreateUpdateDto eventCreateDto) {
        if(Objects.isNull(eventCreateDto.getId()) && Validator.validateEvent(eventCreateDto)) {
            Event event = mapFromCreatedToEntity(eventCreateDto);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    public boolean update(EventCreateUpdateDto eventCreateDto) {
        if(Objects.nonNull(eventCreateDto.getId()) && Validator.validateEvent(eventCreateDto)) {
            Event event = mapFromCreatedToEntity(eventCreateDto);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

/*    //TODO remove
    public void addEvent(MainEventDto eventDto) {
        Event event = new Event();

        event.setName(eventDto.getContent());
        event.setCategory(categoryService.getEntityByTitle(eventDto.getCategory()));
        event.setUser(userRepository.findById(1L).get());
        event.setDateEnd(eventDto.getDataEnd().atStartOfDay());
        event.setDateStop(eventDto.getDateStop().atStartOfDay());

        eventRepository.save(event);
    }*/

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

    public MainEventDto termsByEvent(long id) {
        Optional<Event> eventEntity = eventRepository.findById(id);
        if (eventEntity.isPresent()) {
            Event event = eventRepository.getOne(id);
            MainEventDto mainEventDTO = new MainEventDto();
            setBasicsAttributes(event, mainEventDTO);
            mainEventDTO.setAllTerms(termService.allTermsByEvent(event));
            return mainEventDTO;
        } else {
            throw new NotFoundException("event", id);
        }
    }


    private void setBasicsAttributes(Event event, BaseEventDto baseEventDTO) {
        baseEventDTO.setId(event.getId());
        baseEventDTO.setCategory(event.getCategory().getName());
        baseEventDTO.setSuperCategory(event.getCategory().getCategory().getName());
        baseEventDTO.setDateStop(event.getDateStop().toLocalDate());
        baseEventDTO.setContent(event.getName());
        baseEventDTO.setAmountOfBids(eventRepository.countAmountOfBids(event.getId()));
    }

    private Event mapFromCreatedToEntity(EventCreateUpdateDto dto) {
        Event event = new Event();
        Category category = categoryService.getEntityByTitle(dto.getTitle());
        Category subcategory = category.getSubCategories().stream().filter(o -> dto.getTitle().equals(o.getName()))
                .findFirst().orElseThrow(()->new NotFoundException("No subcategory with title " + dto.getSubCategory()));
        event.setCategory(subcategory);
        event.setDateStop(dto.getDateStop());
        event.setDateEnd(Objects.isNull(dto.getDateEnd()) ? dto.getDateStop() : dto.getDateEnd());
        event.setTotalBid(DEFAULT_BIDS_AMOUNT);
        event.setUser(TEMPORARY_USER); //TODO set user
        return event;
    }
}
