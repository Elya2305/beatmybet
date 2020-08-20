package org.example.beatmybet.service;

import org.example.beatmybet.entity.financy.*;
import org.example.beatmybet.repository.JournalRepository;
import org.example.beatmybet.repository.PostingRepository;
import org.example.beatmybet.repository.TypeOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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

    public boolean post(Serializable entity1, Serializable entity2, //TODO think about bool
                        double sum, TypeOperation.Type nameType, boolean firstProfit) {
        Journal journal = new Journal(getTypeOperation(nameType));
        if(!firstProfit) sum = -sum;
        Posting posting1 = new Posting(entity1, sum, journal);
        Posting posting2 = new Posting(entity2, -sum, journal);
        journal.setPostings(List.of(posting1, posting2));
        journalRepository.save(journal);
        return true;
    }

    // TODO check person's balance

    public TypeOperation getTypeOperation(TypeOperation.Type nameType) {
        TypeOperation typeOperation = typeOperationRepository.findByNameType(nameType);
        if(typeOperation == null) {
            typeOperation = new TypeOperation();
            typeOperation.setNameType(nameType);
            return typeOperationRepository.save(typeOperation);
        }
        return typeOperation;
    }


}
