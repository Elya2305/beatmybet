package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "events")
public class Event {
    @Id
    private Long id;

    @JsonManagedReference
    @NonNull
    @ManyToOne
    @JoinColumn(name = "idcat")
    private Category category;

    @NonNull
    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStop;

    @NonNull
    @Column(name = "data_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<Term> terms;

    public Event() {
    }

}
