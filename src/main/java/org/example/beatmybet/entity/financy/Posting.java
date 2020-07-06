package org.example.beatmybet.entity.financy;

import lombok.Data;

import javax.persistence.*;

@javax.persistence.Entity
@Data
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "posting")
public class Posting {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dt")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_to")
    private TypeOperation typeOperation;

    private Double summ;

    @ManyToOne
    @JoinColumn(name = "id_journal")
    Journal journal;

    public Posting() {
    }
}
