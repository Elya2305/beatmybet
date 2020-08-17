package org.example.beatmybet.exception;

import org.example.beatmybet.dto.ResponseStatusDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseStatusDto notFound(Exception e) {
        e.printStackTrace();
        return new ResponseStatusDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}
