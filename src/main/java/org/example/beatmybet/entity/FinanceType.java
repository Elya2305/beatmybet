package org.example.beatmybet.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "finance_type")
public class FinanceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
