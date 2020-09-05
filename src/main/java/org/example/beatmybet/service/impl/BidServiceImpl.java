package org.example.beatmybet.service.impl;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.DealDTO;
import org.example.beatmybet.dto.HomeBidDTO;
import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;
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

import java.util.ArrayList;
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
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public DealDTO offers(BidDTO bidDTO) {
        TermVariant opposite = termVariantRepository.findOpposite2(bidDTO.getIdVar()); // for db

        List<BidDTO> offers = bidRepository.findByTermVarAndKoefGroupedBySum(
                opposite.getId(),
                oppositeKoef(bidDTO.getKoef()))
                .stream()
                .map(o -> convertWithVar(o, bidDTO.getIdVar()))
                .collect(Collectors.toList());

        List<BidDTO> bids = new ArrayList<>();
        Double sumUser = bidDTO.getSum();
        int counter = 0;
        while (sumUser > 0 && counter < offers.size()) {
            BidDTO offerBid = offers.get(counter++);

            if (offerBid.getSum().equals(sumUser) || offerBid.getSum() > sumUser) {
                if(offerBid.getSum() > sumUser) {
                    offerBid.setSum(sumUser);
                }
                offerBid.setFutureDeal(true);
                bids.add(offerBid);
                sumUser = 0.0;
                break;
            } else {
                offerBid.setFutureDeal(true);
                sumUser -= offerBid.getSum();
                bids.add(offerBid);
            }
        }

        if (sumUser > 0) {
            bids.add(BidDTO.builder()
                    .koef(bidDTO.getKoef())
                    .sum(sumUser)
                    .idVar(bidDTO.getIdVar())
                    .futureDeal(false)
                    .build());
        }
        return DealDTO.builder()
                .offers(bids)
                .bid(bidDTO)
                .term(termRepository.getOne(termVariantRepository.findTerm(bidDTO.getIdVar())).getName())
                .termVar(termVariantRepository.getOne(bidDTO.getIdVar()).getName())
                .build();
    }

    private BidDTO convert(Double[] bid) {
        return BidDTO.builder()
                .koef((double) Math.round(oppositeKoef(bid[0]) * 1000d) / 1000d)
                .sum((double) Math.round((bid[1] * bid[0] - bid[1]) * 1000d) / 1000d)
                .build();
    }

    private BidDTO convertWithVar(Double[] bid, long idVar) {
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
