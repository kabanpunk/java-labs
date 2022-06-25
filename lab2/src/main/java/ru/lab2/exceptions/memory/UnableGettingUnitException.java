package ru.lab2.exceptions.memory;

public class UnableGettingUnitException extends NumberStackException {
    public UnableGettingUnitException(){
        super();
    }

    public UnableGettingUnitException(String message) {
        super(message);
    }

    public static UnableGettingUnitException buildEmptyStack(){
        return new UnableGettingUnitException("stack is empty");
    }
}