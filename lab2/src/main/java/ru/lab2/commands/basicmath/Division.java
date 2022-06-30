package ru.lab2.commands.basicmath;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.lab2.commands.Command;
import ru.lab2.exceptions.commands.CommandException;
import ru.lab2.exceptions.commands.IllegalNumArgsException;
import ru.lab2.exceptions.memory.UnableGettingUnitException;
import ru.lab2.memory.ExecutionContext;
import ru.lab2.memory.NumberStack;
import ru.lab2.memory.stackunits.StackUnit;
import ru.lab2.memory.stackunits.Number;
import ru.lab2.memory.stackunits.Constant;

import java.util.List;

public class Division implements Command {
    private static final Logger logger = LogManager.getLogger(Division.class);
    public Division(List<String> args) throws IllegalNumArgsException {
        if(args.size() != 0){
            throw IllegalNumArgsException.build(args.size(), 0);
        }
    }

    @Override
    public void exec(NumberStack stack, ExecutionContext context) throws CommandException {
        try {
            StackUnit num1 = stack.pop();
            StackUnit num2 = stack.pop();
            StackUnit res = new Number(num2.getValue() / num1.getValue());
            if(!Double.isFinite(res.getValue())){
                logger.log(Level.ERROR, context.getNumberCurExecutableString() + ": " +
                        "operation error " + num2.getValue() + "/" + num1.getValue() + "=" + res.getValue());
                throw new CommandException(context.getNumberCurExecutableString() + ": " +
                        "operation error " + num2.getValue() + "/" + num1.getValue() + "=" + res.getValue());
            }
            stack.push(res);
            logger.log(Level.INFO, context.getNumberCurExecutableString() + ": " +
                    "operation performed " + num2.getValue() + "/" + num1.getValue() + "=" + res.getValue());
        } catch (UnableGettingUnitException e) {
            logger.log(Level.ERROR, context.getNumberCurExecutableString() + ": " +
                    "unable division " + e.getMessage());
            throw new CommandException(context.getNumberCurExecutableString() + ": " +
                    "unable division: " + e.getMessage(), e);
        }
    }
}