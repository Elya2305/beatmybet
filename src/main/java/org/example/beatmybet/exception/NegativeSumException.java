package org.example.beatmybet.exception;

public class NegativeSumException extends IllegalArgumentException {
    public NegativeSumException(double sum) {
        super("Sum " + sum + " must not be negative");
    }
    public NegativeSumException(long id) {
        super("There's not balance enough of user " + id);
    }
}
