package org.example.beatmybet.service;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.entity.*;
import org.example.beatmybet.entity.financy.TypeOperation;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.TermRepository;
import org.example.beatmybet.repository.TermVariantRepository;
import org.example.beatmybet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TermService {
    @Autowired
    TermRepository termRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TermVariantRepository termVariantRepository;

    @Autowired
    FinanceService financeService;

    public void query() {
        List<Integer[]> mostPopularTerm = termRepository.getMostPopularTerm();
        for (Integer[] o : mostPopularTerm) {
            System.out.println(Arrays.toString(o));
        }
    }

    public boolean create(TermDTO termDTO, long idEvent) {
        Optional<Event> event = eventRepository.findById(idEvent);
        if (event.isPresent()) {
//            Term term = new Term();
//            term.setEvent(event.get());
//            term.setName(termDTO.getName());
//            term.setUser(userRepository.findById(1L).get()); // TODO getPrincipal()
//            term.setVariants(variantsByName(termDTO.getVariants(), term));
//            termRepository.save(term);
            return true;
        }
        return false;
    }

    public List<TermVariant> variantsByName(List<String> names, Term term) {
        return names.stream().map(o -> new TermVariant(term, o)).collect(Collectors.toList());
    }

    public boolean addBid(BidDTO bidDTO, long idTerm) { //TODO user principal
        User userEntity = userRepository.findById(1L).get();

        Optional<Term> term = termRepository.findById(idTerm);
        if (term.isPresent()) {
            Term termEntity = termRepository.getOne(idTerm);
            Optional<TermVariant> termVariant = termVariantRepository.findById(bidDTO.getIdVar());
            if (termVariant.isPresent() && termEntity.getVariants().contains(termVariant.get())) {
                Bid bid = new Bid();
                bid.setTerm(termEntity);
                bid.setTermVariant(termVariant.get());
                bid.setKoef(bidDTO.getKoef());

                financeService.post(bid, userEntity, bidDTO.getSum(), TypeOperation.Type.BID_BALANCE, true);
            }
        }


        return false;
    }
}