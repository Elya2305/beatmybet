package org.example.beatmybet.service;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.TermDto;
import org.example.beatmybet.entity.Event;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;

import java.util.List;

public interface TermService {
    void create(TermDto termDTO, long idEvent);

    List<TermVariant> variantsByName(List<String> names, Term term);

    Term mostPopularTermByEvent(Event event);

    List<TermDto> allTermsByEvent(Event event);

    void addBid(BidDTO bidDTO);
}
