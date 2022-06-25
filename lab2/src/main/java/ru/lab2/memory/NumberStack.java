package ru.lab2.memory;

import ru.lab2.exceptions.memory.UnableGettingUnitException;
import ru.lab2.exceptions.memory.UnableGettingUnitException;
import ru.lab2.memory.stackunits.StackUnit;
import ru.lab2.memory.stackunits.StackUnit;

public interface NumberStack {
    StackUnit pop() throws UnableGettingUnitException;
    void push(StackUnit unit);
    StackUnit peek() throws UnableGettingUnitException;
    boolean isEmpty();
    int size();
}