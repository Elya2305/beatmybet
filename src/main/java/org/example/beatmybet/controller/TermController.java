package org.example.beatmybet.controller;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.EventDTO;
import org.example.beatmybet.dto.ResponseStatusDto;
import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/term")
public class TermController {

    @Autowired TermService termService;

    @PostMapping("/{idEvent}/add")
    public ResponseStatusDto create(@PathVariable long idEvent, @RequestBody TermDTO termDTO) {
        termService.create(termDTO, idEvent);
        return new ResponseStatusDto(HttpStatus.OK.value(),
                "term '" + termDTO.getTitle() + "' was added");
    }

    @PostMapping("/add-bid")
    public ResponseStatusDto newBid(@RequestBody BidDTO bidDTO) {
        termService.addBid(bidDTO);
        return new ResponseStatusDto(HttpStatus.OK.value(),
                "new bid was sent by user 1 on variant " + bidDTO.getIdVar());
    }

}
