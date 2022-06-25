package ru.lab2.exceptions.commands;

public class IllegalNumArgsException extends CommandException {
    public IllegalNumArgsException(){
        super();
    }

    public IllegalNumArgsException(String message){
        super(message);
    }

    public static IllegalNumArgsException build(int present, int mustHave){
        return new IllegalNumArgsException("must have " + mustHave +
                " arguments, but present " + present);
    }
}