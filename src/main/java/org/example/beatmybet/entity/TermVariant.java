package org.example.beatmybet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "term_variant")
public class TermVariant {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_term")
    private Term term;

    @Column(name = "name_var")
    private String name;

}
