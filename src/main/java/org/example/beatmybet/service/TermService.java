package org.example.beatmybet.service;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;

import java.util.List;

public interface TermService {
    void create(TermDTO termDTO, long idEvent);

    List<TermVariant> variantsByName(List<String> names, Term term);

    Term mostPopularTermByEvent(Event event);

    List<TermDTO> allTermsByEvent(Event event);

    void addBid(BidDTO bidDTO);
}
