package org.example.beatmybet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})

public class EventDTO {
    private Long id;
    private String category;
    private String superCategory;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_start;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_stop;
//    private Integer amountOfStavka; //?
}
