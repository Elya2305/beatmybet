package org.example.beatmybet.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "bid")
public class Bid implements Serializable {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_term")
    private Term term;

    @NonNull
    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "koef")
    private Double koef;

    @ManyToOne
    @JoinColumn(name = "id_var")
    private TermVariant termVariant;

    public Bid() {
    }
}