package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "term")
public class Term {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_event")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "name_term")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "term")
    List<TermVariant> variants;
}
