package org.example.beatmybet.service;

import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;
import org.example.beatmybet.repository.EventRepository;
import org.example.beatmybet.repository.TermRepository;
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

    public void query(){
        List<Integer[]> mostPopularTerm = termRepository.getMostPopularTerm();
        for(Integer[] o : mostPopularTerm){
            System.out.println(Arrays.toString(o));
        }
    }

    public boolean create(TermDTO termDTO, long idEvent) {
        Optional<Event> event = eventRepository.findById(idEvent);
        if(event.isPresent()) {
            Term term = new Term();
            term.setEvent(event.get());
            term.setName(termDTO.getName());
            term.setUser(userRepository.findById(1L).get()); // TODO getPrincipal()
            term.setVariants(variantsByName(termDTO.getVariants(), term));
            termRepository.save(term);
        }
        return false;
    }

    public List<TermVariant> variantsByName(List<String> names, Term term) {
        return names.stream().map(o -> new TermVariant(term, o)).collect(Collectors.toList());
    }
}
