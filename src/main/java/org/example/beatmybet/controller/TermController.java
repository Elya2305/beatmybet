package org.example.beatmybet.controller;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.ResponseStatusDto;
import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{idTerm}/add-bid")
    public ResponseStatusDto newBid(@PathVariable long idTerm, @RequestBody BidDTO bidDTO) {
        termService.addBid(bidDTO, idTerm);

        return null;
    }

}
