package org.example.beatmybet.repository;

import org.example.beatmybet.entity.financy.TypeOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOperationRepository extends JpaRepository<TypeOperation, Long> {
    TypeOperation findByNameType(TypeOperation.Type nameType);
}
