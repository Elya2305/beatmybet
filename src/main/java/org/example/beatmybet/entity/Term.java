package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "term")
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_event")
    private Event event;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "dt")
    @CreationTimestamp
    private LocalDateTime date;

    @Column(name = "name_term")
    @NonNull
    private String name;

    @NonNull
    @ToString.Exclude
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "term", cascade = CascadeType.ALL)
    List<TermVariant> variants;
}
