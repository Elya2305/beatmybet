package org.example.beatmybet.entity.financy;

import lombok.Data;
import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.User;
import org.hibernate.annotations.Any;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@javax.persistence.Entity
@Data
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "entity")
public class Posting {
    @Id
    private Long id;

    @Any(
            metaDef = "EntityDescriptionMetaDef",
            metaColumn = @Column(name = "type_entity")
    )
    @JoinColumn(name = "id_entiry")
    private Serializable entity;

    @ManyToOne
    @JoinColumn(name = "id_journal")
    private Journal journal;

    private Double summ;

}
