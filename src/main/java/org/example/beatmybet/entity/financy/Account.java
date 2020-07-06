package org.example.beatmybet.entity.financy;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Data
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "entity")
public class Account {
    @Id
    private Long id;


    public Account() {
    }
}
