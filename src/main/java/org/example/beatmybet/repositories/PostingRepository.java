package org.example.beatmybet.repositories;

import org.example.beatmybet.entity.financy.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Long> {
}
