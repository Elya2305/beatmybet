package org.example.beatmybet.repositories;

import org.example.beatmybet.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {

    @Query(value =  "SELECT `b`.`id_term`, COUNT(`b`.`id_term`) as \"amount\" " +
                    "FROM `bid` b " +
                    "GROUP BY `b`.`id_term` " +
                    "ORDER BY `amount` DESC " +
                    "LIMIT 2",
    nativeQuery = true)
    List<Integer[]> getMostPopularTerm();
}
