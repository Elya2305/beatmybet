package org.example.beatmybet.service;

import org.example.beatmybet.dto.*;
import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;
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

    @Autowired
    PostingRepository postingRepository;

    @Autowired
    TermVariantRepository termVariantRepository;

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

    public List<? extends BaseEventDTO> getAllEventWithMostPopularBid(Event.Order order, int page) {
        return sortBy(eventRepository.findAll()
                .stream()
                .map(this::getHomeEventDto)
                .collect(Collectors.toList()), order, page);
    }

    public HomeEventDTO getHomeEventDto(Event event) {
        HomeEventDTO eventDTO = new HomeEventDTO();
        setBasicsAttributes(event, eventDTO);
        List<Long> idTerms = event.getTerms().stream()
                .map(Term::getId)
                .collect(Collectors.toList());
        if (idTerms.size() != 0) {
            Long termId = termRepository.getMostPopularTermFromTerms(idTerms)[0];
            Term term = termRepository.findById(termId).orElseThrow(() -> new NotFoundException("term", termId));
            eventDTO.setTitleTerm(term.getName());
            eventDTO.setBids(term.getVariants()
                    .stream()
                    .map(o -> HomeBidDTO.builder()
                            .variant(o.getName())
                            .koef(bidRepository.getBestKoefByTermVarID(o.getId()))
                            .build())
                    .collect(Collectors.toList()));
        }
        return eventDTO;
    }

    private List<? extends BaseEventDTO> sortBy(List<? extends BaseEventDTO> list, Event.Order order, int page) {
        switch (order) {
            case date:
                list.sort((Comparator.comparing(BaseEventDTO::getDateStop)).reversed());
            case popular:
                list.sort((Comparator.comparingInt(BaseEventDTO::getAmountOfBids)));
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
            List<TermDTO> termDTOS = event.getTerms().stream()
                    .map(term -> TermDTO.builder()
                            .title(term.getName())
                            .variants(generateTermVariants(term))
                            .build())
                    .collect(Collectors.toList());
            mainEventDTO.setAllTerms(termDTOS);
            return mainEventDTO;
        } else {
            throw new NotFoundException("event", id);
        }
    }

    private List<TermVariantDTO> generateTermVariants(Term term) {
        return term.getVariants().stream()
                .map(variant -> TermVariantDTO.builder()
                        .termVarTitle(termVariantRepository.findOpposite(term.getId(), variant.getId()).getName())
                        .bids(generateBids(variant))
                        .build())
                .collect(Collectors.toList());
    }

    private List<BidDTO> generateBids(TermVariant termVar) {
        List<Bid> bids = bidRepository.findByTermVariant(termVar);
        List<BidDTO> bidDTOList = new ArrayList<>();
        for (Bid bid : bids) {
            Double sum = postingRepository.sumByBid(bid.getId());
            if (sum != null) {
                BidDTO bidDTO = BidDTO.builder()
                        .id(bid.getId())
                        .koef((double) Math.round(oppositeKoef(bid.getKoef()) * 1000d) / 1000d)
                        .sum((double) Math.round((sum * bid.getKoef() - sum) * 1000d) / 1000d)
                        .build();
                bidDTOList.add(bidDTO);
            }
        }
        return bidDTOList;
    }

    private double oppositeKoef(double k) {
        return k / (k - 1);
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
