package org.example.beatmybet.services;

import org.example.beatmybet.repositories.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TermService {
    @Autowired
    TermRepository termRepository;

    public void query(){
        List<Integer[]> mostPopularTerm = termRepository.getMostPopularTerm();
        for(Integer[] o : mostPopularTerm){
            System.out.println(Arrays.toString(o));
        }
    }
//    public List<>
}
