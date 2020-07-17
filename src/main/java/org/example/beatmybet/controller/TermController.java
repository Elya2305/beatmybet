package org.example.beatmybet.controller;

import org.example.beatmybet.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/term")
public class TermController {

    @Autowired TermService termService;

    @GetMapping("/query")
    public String query(){
        termService.query();
        return "OK";
    }
}
