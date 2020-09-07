package org.example.beatmybet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude()
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HomeBidDTO {
    private String variant;

    private Double koef;
}
