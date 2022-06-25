package ru.lab2.memory.stackunits;

public class Number implements StackUnit {
    private double value;

    public Number(double value){
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return Double.toString(value);
    }
}