package org.example.beatmybet.entity.financy;

import lombok.Data;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "posting")
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Any(
            metaDef = "EntityDescriptionMetaDef",
            metaColumn = @Column(name = "type_entity")
    )
    @JoinColumn(name = "id_entity")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Serializable entity;

    @ManyToOne
    @JoinColumn(name = "id_journal")
    private Journal journal;

    private Double summ;

    public Posting() {
    }

    public Posting(Serializable entity, double summ, Journal journal) {
        this.entity = entity;
        this.summ = summ;
        this.journal = journal;
    }
}
