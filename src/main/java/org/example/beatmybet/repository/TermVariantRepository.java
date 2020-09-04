package org.example.beatmybet.repository;

import org.example.beatmybet.entity.TermVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermVariantRepository extends JpaRepository<TermVariant, Long> {

    @Query(value = "SELECT * FROM `term_variant` " +
            "WHERE id_term =:id", nativeQuery = true)
    List<TermVariant> findByIdTerm(long id);

    @Query(value = "SELECT * FROM " +
            "`term_variant` " +
            "WHERE id_term =:idTerm AND id !=:id", nativeQuery = true)
    TermVariant findOpposite(long idTerm, long id);

    @Query(value = "SELECT * FROM `term_variant` t " +
            "WHERE t.id !=:idVar AND t.id_term = (SELECT t2.id_term " +
            "                                 FROM `term_variant` t2 " +
            "                                 WHERE t2.id =:idVar)", nativeQuery = true)
    TermVariant findOpposite2(long idVar);

    @Query(value = "SELECT t.id_term FROM term_variant t " +
            "WHERE id=:id", nativeQuery = true)
    Long findTerm(Long id);

}
