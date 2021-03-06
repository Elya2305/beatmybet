package org.example.beatmybet.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
