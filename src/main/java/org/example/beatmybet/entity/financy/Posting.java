package org.example.beatmybet.entity.financy;

import lombok.Data;
import org.hibernate.annotations.Any;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
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
