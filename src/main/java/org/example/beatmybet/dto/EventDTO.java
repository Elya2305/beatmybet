package org.example.beatmybet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
public class EventDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String content;

    private String category;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String superCategory;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dateStop;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime dataEnd;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer amountOfBids;

//    @JsonInclude(JsonInclude.Include.ALWAYS)
    private TermDTO term;

    private List<TermDTO> allTerms;
}

