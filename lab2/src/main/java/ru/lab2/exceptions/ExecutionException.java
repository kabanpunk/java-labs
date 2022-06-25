package ru.lab2.exceptions;

public class ExecutionException extends StackCalculatorException{
    public ExecutionException() {
        super();
    }

    public ExecutionException(String massage) {
        super(massage);
    }

    public ExecutionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ExecutionException(Throwable throwable) {
        super(throwable);
    }
}