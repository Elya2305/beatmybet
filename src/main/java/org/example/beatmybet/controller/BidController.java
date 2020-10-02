package org.example.beatmybet.controller;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.DealDTO;
import org.example.beatmybet.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bid")
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping("/offers")
    public DealDTO offers(@RequestBody BidDTO bidDTO) {
        System.out.println(bidDTO);
        return bidService.offers(bidDTO);
    }
}
