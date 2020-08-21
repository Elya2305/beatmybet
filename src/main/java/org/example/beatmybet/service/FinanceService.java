package org.example.beatmybet.service;

import org.example.beatmybet.entity.GlobalFinanceEntity;
import org.example.beatmybet.entity.financy.*;
import org.example.beatmybet.exception.NegativeSumException;
import org.example.beatmybet.repository.JournalRepository;
import org.example.beatmybet.repository.PostingRepository;
import org.example.beatmybet.repository.TypeOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FinanceService {

    @Autowired
    PostingRepository postingRepository;

    @Autowired
    JournalRepository journalRepository;

    @Autowired
    TypeOperationRepository typeOperationRepository;

    // TODO check person's balance
    public boolean post(Serializable entity1, Serializable entity2,
                        double sum, TypeOperation.Type nameType) {
        checkSum(sum);
        TypeOperation typeOperation = typeOperationRepository.findByNameType(nameType);
        Journal journal = new Journal(typeOperation);
        Posting posting1 = new Posting(entity1,
                typeOperation.getPlus().equals(((GlobalFinanceEntity) entity1).getType())
                        ? sum : -sum,
                journal);
        Posting posting2 = new Posting(entity2,
                typeOperation.getMinus().equals(((GlobalFinanceEntity) entity2).getType())
                        ? -sum : sum,
                journal);
        journal.setPostings(List.of(posting1, posting2));
        journalRepository.save(journal);
        return true;
    }

    public void checkSum(double sum) {
        if (sum <= 0) {
            throw new NegativeSumException(sum);
        }
    }


}
