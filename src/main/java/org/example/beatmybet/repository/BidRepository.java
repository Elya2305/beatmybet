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

    @Query(value = "SELECT b.koef, SUM(p.summ) " +
            "FROM `bid` b " +
            "LEFT JOIN `posting` p ON b.id = p.id_entity " +
            "WHERE p.type_entity = 'BID' AND b.id_var =:idVar " +
            "GROUP BY b.koef", nativeQuery = true)
    List<Double[]> findByTermVar(Long idVar);

    @Query(value = "SELECT b.koef, p.summ " +
            "FROM `bid` b " +
            "LEFT JOIN `posting` p ON b.id = p.id_entity " +
            "WHERE p.type_entity = 'BID' AND b.id_var =:idVar AND b.koef <=:koef " +
            "ORDER BY koef ASC", nativeQuery = true)
    List<Double[]> findByTermVarAndKoef(Long idVar, Double koef);


    @Query(value = "SELECT b.koef, SUM(p.summ) " +
            "FROM `bid` b " +
            "LEFT JOIN `posting` p ON b.id = p.id_entity " +
            "WHERE p.type_entity = 'BID' AND b.id_var =:idVar AND b.koef <=:koef " +
            "GROUP BY b.koef " +
            "ORDER BY koef ", nativeQuery = true)
    List<Double[]> findByTermVarAndKoefGroupedBySum(Long idVar, Double koef);


    @Query(value = "SELECT summ " +
            "FROM `posting` " +
            "WHERE id_entity =:idEntity AND type_entity = 'BID'", nativeQuery = true)
    Double sumByBid(long idEntity);

    List<Bid> findByTermVariantAndKoefOrderByDateDesc(TermVariant termVariant, Double koef);

}
