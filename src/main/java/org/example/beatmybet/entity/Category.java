package org.example.beatmybet.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class) //fullName -> full_name
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "event_cat")
public class Category {
    @Id
    @Getter
    @Setter
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "name_cat")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent")
    @Getter
    @Setter
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Setter
    private List<Category> subCategories;

    @JsonIgnore
    public List<Category> getSubCategories() {
        return subCategories;
    }
}
