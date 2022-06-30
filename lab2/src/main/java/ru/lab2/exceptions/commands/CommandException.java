package ru.lab2.exceptions.commands;

import ru.lab2.exceptions.StackCalculatorException;

public class CommandException extends StackCalculatorException {
    public CommandException() {
        super();
    }

    public CommandException(String massage) {
        super(massage);
    }

    public CommandException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CommandException(Throwable throwable) {
        super(throwable);
    }
}