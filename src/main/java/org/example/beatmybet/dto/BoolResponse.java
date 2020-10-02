package org.example.beatmybet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoolResponse {
    private Boolean check;

    public static BoolResponse of(boolean result) {
        return new BoolResponse(result);
    }
}
