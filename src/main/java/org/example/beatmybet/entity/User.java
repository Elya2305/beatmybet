package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.beatmybet.entity.financy.Account;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "user")
public class User {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Integer phone;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    Set<Event> events;

    public User() {
    }
}
