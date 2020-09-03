package org.example.beatmybet.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_bid1")
    private Bid bid1;

    @ManyToOne
    @JoinColumn(name = "id_bid2")
    private Bid bid2;

    @Column(name = "dt")
    @CreationTimestamp
    private LocalDateTime date;
}
