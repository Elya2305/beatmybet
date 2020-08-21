package org.example.beatmybet.exception;

public class NegativeSumException extends IllegalArgumentException {
    public NegativeSumException(double sum) {
        super("Sum " + sum + " must not be negative");
    }
}
