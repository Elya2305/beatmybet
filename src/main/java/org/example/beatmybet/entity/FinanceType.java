package org.example.beatmybet.entity;

import lombok.Data;
import org.example.beatmybet.entity.financy.Account;

import javax.persistence.*;

@Entity
@Data
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "finance_type")
public class FinanceType {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    private String name;
}
