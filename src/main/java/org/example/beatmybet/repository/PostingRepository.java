package org.example.beatmybet.repository;

import org.example.beatmybet.entity.User;
import org.example.beatmybet.entity.financy.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    @Query("select sum(p.summ) from Posting p where p.entity=:user")
    double getBalance(@Param(("user")) User user);

}
