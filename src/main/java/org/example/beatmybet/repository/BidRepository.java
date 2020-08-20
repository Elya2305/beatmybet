package org.example.beatmybet.repository;

import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.TermVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(value = "SELECT koef FROM `bid` " +
            "WHERE id_var = :id " +
            "ORDER BY koef DESC " +
            "LIMIT 1", nativeQuery = true)
    Float getBestKoefByTermVarID(Long id);


}
