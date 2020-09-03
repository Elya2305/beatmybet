package org.example.beatmybet.service.impl;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.HomeBidDTO;
import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.User;
import org.example.beatmybet.entity.financy.TypeOperation;
import org.example.beatmybet.repository.BidRepository;
import org.example.beatmybet.repository.TermRepository;
import org.example.beatmybet.repository.TermVariantRepository;
import org.example.beatmybet.repository.UserRepository;
import org.example.beatmybet.service.BidService;
import org.example.beatmybet.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TermVariantRepository termVariantRepository;

    public void addBid(Bid bid, Double sum) { //TODO user principal
        User userEntity = userRepository.findById(1L).get();
        financeService.post(bid, userEntity, sum, TypeOperation.Type.BID_BALANCE);
    }

    public List<HomeBidDTO> homeBidDTOsByTerm(Term term) {
        return term.getVariants()
                .stream()
                .map(o -> HomeBidDTO.builder()
                        .variant(o.getName())
                        .koef(bidRepository.getBestKoefByTermVarID(o.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<BidDTO> bidsByTermVar(Long idTermVar) {
        return bidRepository.findByTermVar(idTermVar)
                .stream()
                .map(o -> convert(o, idTermVar))
                .collect(Collectors.toList());
    }

    private BidDTO convert(Double[] bid, long idVar) {
        return BidDTO.builder()
                .idVar(idVar)
                .koef((double) Math.round(oppositeKoef(bid[0]) * 1000d) / 1000d)
                .sum((double) Math.round((bid[1] * bid[0] - bid[1]) * 1000d) / 1000d)
                .build();
    }

    private double oppositeKoef(double k) {
        return k / (k - 1);
    }

}
