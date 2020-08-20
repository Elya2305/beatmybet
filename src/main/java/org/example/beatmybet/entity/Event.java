package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Cascade;
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
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idcat")
    private Category category;

    @Column(name = "dt")
    @CreationTimestamp
    private LocalDateTime date;

//    @NonNull
//    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NonNull
    @Column(name = "name_event")
    private String name;

    @NonNull
    @Column(name = "data_stop")
    private LocalDateTime dateStop;

    @NonNull
    @Column(name = "data_end")
    private LocalDateTime dateEnd;

    @JsonBackReference
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    List<Term> terms;

    public enum Order{
        date, popular
    }
}
