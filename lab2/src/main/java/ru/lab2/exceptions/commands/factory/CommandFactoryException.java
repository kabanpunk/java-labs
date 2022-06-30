package ru.lab2.exceptions.commands.factory;

import ru.lab2.exceptions.StackCalculatorException;

public class CommandFactoryException extends StackCalculatorException {

    public CommandFactoryException() {
        super();
    }

    public CommandFactoryException(String massage) {
        super(massage);
    }

    public CommandFactoryException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CommandFactoryException(Throwable throwable) {
        super(throwable);
    }
}