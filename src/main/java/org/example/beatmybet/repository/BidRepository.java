package org.example.beatmybet.repository;

import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.Term;
import org.example.beatmybet.entity.TermVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(value = "SELECT koef FROM `bid` " +
            "WHERE id_var = :id " +
            "ORDER BY koef DESC " +
            "LIMIT 1", nativeQuery = true)
    Double getBestKoefByTermVarID(Long id);

    List<Bid> findByTermVariant(TermVariant termVariant);

    @Query(value = "SELECT summ " +
            "FROM `posting` " +
            "WHERE id_entity =:idEntity AND type_entity = 'BID'", nativeQuery = true)
    Double sumByBid(long idEntity);

}
