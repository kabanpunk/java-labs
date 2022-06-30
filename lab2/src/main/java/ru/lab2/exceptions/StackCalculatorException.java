package ru.lab2.exceptions;

public class StackCalculatorException extends Exception{
    public StackCalculatorException(){
        super();
    }

    public StackCalculatorException(String massage){
        super(massage);
    }

    public StackCalculatorException(String message, Throwable throwable){
        super(message, throwable);
    }

    public StackCalculatorException(Throwable throwable){
        super(throwable);
    }
}