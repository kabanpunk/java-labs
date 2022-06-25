package ru.lab2.util;

import ru.lab2.exceptions.memory.UseConstantException;
import ru.lab2.memory.stackunits.StackUnit;

public interface ArgumentParser {
    StackUnit getUnit(String arg) throws UseConstantException;
    boolean isValidConstantName(String name);
}