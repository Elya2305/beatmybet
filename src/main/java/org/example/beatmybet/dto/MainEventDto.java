package org.example.beatmybet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude()
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MainEventDto extends BaseEventDto {

    private List<TermDto> allTerms;
}

