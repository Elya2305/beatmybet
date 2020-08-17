package org.example.beatmybet.repository;

import org.example.beatmybet.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value =  "SELECT COUNT(*) " +
                    "FROM `bid` b " +
                    "LEFT JOIN `term` t ON b.`id_term` = t.`id` " +
                    "WHERE t.`id_event` =:id",
    nativeQuery = true)
    Integer countAmountOfBids(@Param("id") Long id);

    List<Event> findAllByOrderByDateDesc();

    @Query(value =  "SELECT COUNT(*) FROM bid b " +
                    "WHERE id_term IN ( " +
                        "SELECT t.id " +
                        "FROM term t " +
                        "WHERE t.id_event =:id)",
            nativeQuery = true)
    int getAmountOfBid(@Param("id") Long id);


}
