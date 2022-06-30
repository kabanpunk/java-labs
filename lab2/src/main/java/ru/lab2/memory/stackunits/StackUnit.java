package ru.lab2.memory.stackunits;

import ru.lab2.exceptions.memory.UseConstantException;

public interface StackUnit {
    double getValue();
    void setValue(double value) throws UseConstantException;
}