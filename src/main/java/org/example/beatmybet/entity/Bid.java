package org.example.beatmybet.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.example.beatmybet.entity.financy.FinanceType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bid")
public class Bid implements Serializable, GlobalFinanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public FinanceType getType() {
        return FinanceType.BID;
    }
}
