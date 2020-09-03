package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.example.beatmybet.entity.financy.FinanceType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@ToString(of = {"id", "date", "phone", "password"})
@Table(name = "user")
public class User implements Serializable, GlobalFinanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dt")
    @CreationTimestamp
    private LocalDateTime date;

    private Integer phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Event> events;

    public User() {
    }

    @Override
    public FinanceType getType() {
        return FinanceType.USER;
    }

}
