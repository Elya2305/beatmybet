package org.example.beatmybet.repository;

import org.example.beatmybet.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Event> findAll(Pageable pageable);
}
