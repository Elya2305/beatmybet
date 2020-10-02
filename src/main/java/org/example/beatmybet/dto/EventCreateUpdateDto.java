package org.example.beatmybet.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventCreateUpdateDto {
    private Long id;
    private String title;
    private String category;
    private String subCategory;
    private LocalDateTime dateStop;
    private LocalDateTime dateEnd;
}
