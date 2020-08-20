package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(of = {"id", "date", "phone", "password"})
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dt")
    @CreationTimestamp
    private LocalDate date;

    private Integer phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    @JsonBackReference
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Event> events;

    public User() {
    }


}
