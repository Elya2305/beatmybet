package org.example.beatmybet.service;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.HomeBidDTO;
import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;
import org.example.beatmybet.entity.User;
import org.example.beatmybet.entity.financy.TypeOperation;
import org.example.beatmybet.repository.BidRepository;
import org.example.beatmybet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private UserRepository userRepository;

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

    public List<BidDTO> bidsByTermVar(TermVariant termVar) {
        List<Bid> bids = bidRepository.findByTermVariant(termVar);
        List<BidDTO> bidDTOList = new ArrayList<>();
        for (Bid bid : bids) {
            Double sum = bidRepository.sumByBid(bid.getId());
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

}
