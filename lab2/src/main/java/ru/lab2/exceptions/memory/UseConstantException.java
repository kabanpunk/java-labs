package ru.lab2.exceptions.memory;

public class UseConstantException extends ExecutionEnvironmentException{
    public UseConstantException() {
        super();
    }

    public UseConstantException(String massage) {
        super(massage);
    }

    public static UseConstantException buildReDefine(String name){
        return new UseConstantException("constant with name " + name + " define early");
    }

    public static UseConstantException buildUnknownConstant(String name){
        return new UseConstantException("constant with name " + name + " didn't define");
    }

    public static UseConstantException buildWrongNameFormat(){
        return new UseConstantException("invalid format of constant name");
    }
}