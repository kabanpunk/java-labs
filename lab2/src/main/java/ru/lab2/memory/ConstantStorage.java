package ru.lab2.memory;

import ru.lab2.exceptions.memory.UseConstantException;
import ru.lab2.memory.stackunits.StackUnit;

public interface ConstantStorage {
    StackUnit getConstantByName(String name) throws UseConstantException;
    void addNewConstant(String name, double value) throws UseConstantException;
}