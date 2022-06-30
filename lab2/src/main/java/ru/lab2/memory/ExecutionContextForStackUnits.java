package ru.lab2.memory;

import ru.lab2.exceptions.memory.UseConstantException;
import ru.lab2.memory.stackunits.StackUnit;
import ru.lab2.util.ArgumentParser;
import ru.lab2.util.ArgumentParserImpl;

import java.io.OutputStream;

public class ExecutionContextForStackUnits implements ExecutionContext {
    private final ArgumentParser argParser;
    private final ConstantStorage cStorage;
    private int numCurExecutableString = 0;

    public ExecutionContextForStackUnits(){
        cStorage = new ConstantStorageImpl();
        argParser = new ArgumentParserImpl(cStorage);
    }
    @Override
    public StackUnit getUnitByArg(String arg) throws UseConstantException {
        return argParser.getUnit(arg);
    }


    @Override
    public void addNewConstant(String name, double value) throws UseConstantException {
        if(!argParser.isValidConstantName(name))
            throw UseConstantException.buildWrongNameFormat();
        cStorage.addNewConstant(name, value);
    }

    @Override
    public void setNumberCurExecutableString(int num) {
        numCurExecutableString = num;
    }

    @Override
    public int getNumberCurExecutableString() {
        return numCurExecutableString;
    }

    @Override
    public OutputStream getStandardOutputStream() {
        return System.out;
    }
}