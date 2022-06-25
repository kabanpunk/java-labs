package ru.lab2.memory;

import ru.lab2.exceptions.memory.UseConstantException;
import ru.lab2.memory.stackunits.Constant;
import ru.lab2.memory.stackunits.StackUnit;

import java.util.HashMap;
import java.util.Map;

public class ConstantStorageImpl implements ConstantStorage{
    private final Map<String, StackUnit> variables = new HashMap<>();

    @Override
    public StackUnit getConstantByName(String name) throws UseConstantException {
        StackUnit unit = variables.get(name);
        if(unit == null)
            throw UseConstantException.buildUnknownConstant(name);
        return unit;
    }

    @Override
    public void addNewConstant(String name, double value) throws UseConstantException {
        StackUnit unit = variables.putIfAbsent(name, new Constant(name, value));
        if(unit != null){
            throw UseConstantException.buildReDefine(name);
        }
    }



}