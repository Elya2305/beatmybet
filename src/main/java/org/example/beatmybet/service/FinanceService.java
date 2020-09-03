package org.example.beatmybet.service;

import org.example.beatmybet.entity.financy.TypeOperation;

import java.io.Serializable;

public interface FinanceService {
    void post(Serializable entity1, Serializable entity2, double sum, TypeOperation.Type nameType);
}
