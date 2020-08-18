package org.example.beatmybet.controller;

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
                "term '" + termDTO.getName() + "' was added");
    }
}
