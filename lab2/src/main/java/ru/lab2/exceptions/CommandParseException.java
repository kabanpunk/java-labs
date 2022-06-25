package ru.lab2.exceptions;

public class CommandParseException extends StackCalculatorException {
    public CommandParseException() {
        super();
    }

    public CommandParseException(String massage) {
        super(massage);
    }

    public CommandParseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CommandParseException(Throwable throwable) {
        super(throwable);
    }
}