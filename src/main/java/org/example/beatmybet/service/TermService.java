package org.example.beatmybet.service;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.dto.TermVariantDTO;
import org.example.beatmybet.entity.*;
import org.example.beatmybet.entity.financy.TypeOperation;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.TermRepository;
import org.example.beatmybet.repository.TermVariantRepository;
import org.example.beatmybet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TermService {
    @Autowired
    private TermRepository termRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TermVariantRepository termVariantRepository;

    @Autowired
    private BidService bidService;

//    @Autowired
//    private FinanceService financeService;

    public void create(TermDTO termDTO, long idEvent) {
        Optional<Event> event = eventRepository.findById(idEvent);
        if (event.isPresent()) {
            Term term = new Term();
            term.setEvent(event.get());
            term.setName(termDTO.getTitle());
            term.setUser(userRepository.findById(1L).get()); // TODO getPrincipal()
//            term.setVariants(variantsByName(termDTO.getVariants(), term));
            termRepository.save(term);
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
            return termRepository.findById(termId).orElseThrow(() -> new NotFoundException("term", termId));
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
                .map(variant -> TermVariantDTO.builder()
                        .termVarTitle(termVariantRepository.findOpposite(term.getId(), variant.getId()).getName())
                        .bids(bidService.bidsByTermVar(variant))
                        .build())
                .collect(Collectors.toList());
    }

    public void addBid(BidDTO bidDTO) {
        Optional<TermVariant> termVarEntity = termVariantRepository.findById(bidDTO.getIdVar());
        if (termVarEntity.isPresent()) {
            TermVariant termVariant = termVariantRepository.getOne(bidDTO.getIdVar());
            Bid bid = new Bid();
            bid.setTerm(termVariant.getTerm());
            bid.setTermVariant(termVariant);
            bid.setKoef(bidDTO.getKoef());
            bidService.addBid(bid, bidDTO.getSum());
        }else {
            throw new NotFoundException("tern variant", bidDTO.getIdVar());
        }
    }
}
