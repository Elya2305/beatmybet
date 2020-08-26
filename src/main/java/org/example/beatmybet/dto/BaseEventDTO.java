package org.example.beatmybet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BaseEventDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String content;

    private String category;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String superCategory;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateStop;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate dataEnd;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer amountOfBids;

}
