package org.example.beatmybet.controller;

import org.example.beatmybet.dto.BidDTO;
import org.example.beatmybet.dto.TestDto;
import org.example.beatmybet.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    BidRepository bidRepository;

    @GetMapping
    List<TestDto> get() {
        return bidRepository.tester(7L);
    }
}
