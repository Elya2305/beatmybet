package org.example.beatmybet.repositories;

import org.example.beatmybet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    int getAmountOfBids();

}
