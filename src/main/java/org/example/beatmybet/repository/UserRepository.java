package org.example.beatmybet.repository;

import org.example.beatmybet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    int getAmountOfBids();

}
