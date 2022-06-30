package ru.lab2.memory.stackunits;

import ru.lab2.exceptions.memory.UseConstantException;

public class Constant implements StackUnit{
    private final double value;
    private final String name;

    public Constant(String name, double value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) throws UseConstantException {
        throw new UseConstantException("unable to change the value of a constant");
    }

    @Override
    public String toString() {
        return value + name;
    }
}