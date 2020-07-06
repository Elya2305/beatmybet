package org.example.beatmybet.entity.financy;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@Table(name = "journal")
public class Journal {
    @Id
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_type_operation")
    private TypeOperation typeOperation;
}
