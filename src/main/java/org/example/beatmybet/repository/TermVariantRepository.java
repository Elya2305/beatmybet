package org.example.beatmybet.repository;

import org.example.beatmybet.entity.TermVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermVariantRepository extends JpaRepository<TermVariant, Long> {

    @Query(value = "SELECT * FROM `term_variant` " +
            "WHERE id_term = 1", nativeQuery = true)
    List<TermVariant> findByIdTerm(long id);

    @Query(value = "SELECT * FROM " +
            "`term_variant` " +
            "WHERE id_term =:idTerm AND id !=:id", nativeQuery = true)
    TermVariant findOpposite(long idTerm, long id);


}
