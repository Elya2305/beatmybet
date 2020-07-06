package org.example.beatmybet.controllers;

import org.example.beatmybet.dto.TermDTO;
import org.example.beatmybet.services.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TermController {

    @Autowired TermService termService;

}
