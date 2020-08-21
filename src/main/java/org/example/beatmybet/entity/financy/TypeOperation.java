package org.example.beatmybet.entity.financy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(of = {"id", "nameType"})
@Table(name = "type_operation")
public class TypeOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_type")
    @Enumerated(EnumType.STRING)
    private Type nameType;

    @Enumerated(EnumType.STRING)
    private FinanceType plus;

    @Enumerated(EnumType.STRING)
    private FinanceType minus;

    @JsonIgnore
    @OneToMany(mappedBy = "typeOperation")
    private List<Journal> journals;

    public enum Type{
        REPLENISHMENT,
        BID_BALANCE,
        WITHDRAWING,
        DEAL_BALANCE,
        DEAL_PAYMENT,
        COMMISSION
    }
}

