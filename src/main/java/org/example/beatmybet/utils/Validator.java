package org.example.beatmybet.utils;

import org.example.beatmybet.dto.EventCreateUpdateDto;
import org.example.beatmybet.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Validator {

    public static boolean validateEvent(EventCreateUpdateDto event) {
        if(Objects.isNull(event)) {
            throw new ValidationException("Event must not be null");
        }
        if(Objects.isNull(event.getTitle()) || Objects.isNull(event.getDateEnd())) {
            throw new ValidationException("Event's title and date end must not be null " + event);
        }
        return true;
    }
}
