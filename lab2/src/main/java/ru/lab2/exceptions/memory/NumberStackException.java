package ru.lab2.exceptions.memory;

public class NumberStackException extends ExecutionEnvironmentException{
    public NumberStackException() {
        super();
    }

    public NumberStackException(String massage) {
        super(massage);
    }

    public NumberStackException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NumberStackException(Throwable throwable) {
        super(throwable);
    }
}