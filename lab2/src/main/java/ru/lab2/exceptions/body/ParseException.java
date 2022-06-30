package ru.lab2.exceptions.body;

import ru.lab2.exceptions.StackCalculatorException;

public class ParseException extends StackCalculatorException {
    public ParseException() {
        super();
    }

    public ParseException(String massage) {
        super(massage);
    }

    public ParseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ParseException(Throwable throwable) {
        super(throwable);
    }
}