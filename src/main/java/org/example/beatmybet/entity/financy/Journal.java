package org.example.beatmybet.entity.financy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(of = {"id", "date", "typeOperation"})
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

    @JsonBackReference
    @OneToMany(mappedBy = "journal")
    private Set<Posting> postings;
}
