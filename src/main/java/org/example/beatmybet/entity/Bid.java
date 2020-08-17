package org.example.beatmybet.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
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
    private Float koef;

    @ManyToOne
    @JoinColumn(name = "id_var")
    private TermVariant termVariant;

    public Bid() {
    }
}
