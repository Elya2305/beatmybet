package org.example.beatmybet.repository;

import org.example.beatmybet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT SUM(summ) " +
            "FROM `posting` " +
            "WHERE id_entity =:id AND type_entity = 'USER'", nativeQuery = true)
    double getBalance(long id);
}
