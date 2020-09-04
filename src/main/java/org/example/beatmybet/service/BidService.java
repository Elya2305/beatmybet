package org.example.beatmybet.service;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.DealDTO;
import org.example.beatmybet.dto.HomeBidDTO;
import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.Term;

import java.util.List;

public interface BidService {

    List<HomeBidDTO> homeBidDTOsByTerm(Term term);

    List<BidDTO> bidsByTermVar(Long idTermVar);

    void addBid(Bid bid, Double sum);

    DealDTO offers(BidDTO bidDTO);
}
