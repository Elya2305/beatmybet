package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "events")
public class Event {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idcat")
    private Category category;
    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @Column(name = "name_event")
    private String name;
    @Column(name = "data_stop")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStop;
    @Column(name = "data_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    public Event() {
    }
}
