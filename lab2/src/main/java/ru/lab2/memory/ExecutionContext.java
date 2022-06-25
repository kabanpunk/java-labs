package ru.lab2.memory;

import ru.lab2.exceptions.memory.UseConstantException;
import ru.lab2.memory.stackunits.StackUnit;

import java.io.OutputStream;

public interface ExecutionContext {
    StackUnit getUnitByArg(String arg) throws UseConstantException;
    void addNewConstant(String name, double value) throws UseConstantException;
    void setNumberCurExecutableString(int num);
    int getNumberCurExecutableString();
    OutputStream getStandardOutputStream();
}