package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@ToString(of = {"id", "date", "phone", "password"})
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "user")
public class User implements Serializable {
    @Id
    private Long id;

    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Integer phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    @JsonBackReference
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    Set<Event> events;

    public User() {
    }


}
