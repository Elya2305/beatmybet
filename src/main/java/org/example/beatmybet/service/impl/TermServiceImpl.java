package org.example.beatmybet.service.impl;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.dto.TermVariantDTO;
import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.TermRepository;
import org.example.beatmybet.repository.TermVariantRepository;
import org.example.beatmybet.repository.UserRepository;
import org.example.beatmybet.service.BidService;
import org.example.beatmybet.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TermServiceImpl implements TermService {
    private TermRepository termRepository;

    private EventRepository eventRepository;

    private UserRepository userRepository;

    private TermVariantRepository termVariantRepository;

    private BidService bidService;

    @Autowired
    public TermServiceImpl(TermRepository termRepository, EventRepository eventRepository, UserRepository userRepository,
                           TermVariantRepository termVariantRepository, BidService bidService) {
        this.termRepository = termRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.termVariantRepository = termVariantRepository;
        this.bidService = bidService;
    }

    public void create(TermDTO termDTO, long idEvent) {
        Optional<Event> event = eventRepository.findById(idEvent);
        if (event.isPresent()) {
            Term term = new Term();
            term.setEvent(event.get());
            term.setName(termDTO.getTitle());
            term.setUser(userRepository.findById(1L).get()); // TODO getPrincipal()
            termRepository.save(term);
        } else {
            throw new NotFoundException("event", idEvent);
        }
    }

    public List<TermVariant> variantsByName(List<String> names, Term term) {
        return names.stream().map(o -> new TermVariant(term, o)).collect(Collectors.toList());
    }

    public Term mostPopularTermByEvent(Event event) {
        List<Long> idTerms = event.getTerms().stream()
                .map(Term::getId)
                .collect(Collectors.toList());
        if (idTerms.size() != 0) {
            Long termId = termRepository.getMostPopularTermFromTerms(idTerms)[0];
            return termRepository.getOne(termId);
        }
        return null;
    }

    public List<TermDTO> allTermsByEvent(Event event) {
        return event.getTerms().stream()
                .map(term -> TermDTO.builder()
                        .title(term.getName())
                        .variants(generateTermVariants(term))
                        .build())
                .collect(Collectors.toList());
    }

    private List<TermVariantDTO> generateTermVariants(Term term) {
        return term.getVariants().stream()
                .map(variant -> {
                    TermVariant opposite = termVariantRepository.findOpposite(term.getId(), variant.getId());
                    return TermVariantDTO.builder()
                            .termId(opposite.getId())
                            .termVarTitle(opposite.getName())
                            .bids(bidService.bidsByTermVar(variant.getId()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void addBid(BidDTO bidDTO) {
        Optional<TermVariant> termVarEntity = termVariantRepository.findById(bidDTO.getIdVar());
        if (termVarEntity.isPresent()) {
            TermVariant termVariant = termVariantRepository.getOne(bidDTO.getIdVar());
            Bid bid = new Bid();
            bid.setTerm(termVariant.getTerm());
            bid.setKoef(bidDTO.getKoef());
            bid.setTermVariant(termVariant);
            bidService.addBid(bid, bidDTO.getSum());
        } else {
            throw new NotFoundException("term variant", bidDTO.getIdVar());
        }
    }
}
