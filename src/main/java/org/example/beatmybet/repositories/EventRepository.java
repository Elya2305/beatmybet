package org.example.beatmybet.repositories;

import org.example.beatmybet.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
