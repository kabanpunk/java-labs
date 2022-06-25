package ru.lab2.commands.notincluded;

import ru.lab2.exceptions.commands.CommandException;
import ru.lab2.exceptions.commands.IllegalNumArgsException;
import ru.lab2.exceptions.memory.UnableGettingUnitException;
import ru.lab2.memory.ExecutionContext;
import ru.lab2.memory.NumberStack;
import ru.lab2.memory.stackunits.StackUnit;
import ru.lab2.commands.Command;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.util.List;

public class Print implements Command {
    private static final Logger logger = LogManager.getLogger(Print.class);
    public Print(List<String> args) throws IllegalNumArgsException {
        if(args.size() != 0){
            throw IllegalNumArgsException.build(args.size(), 0);
        }
    }

    @Override
    public void exec(NumberStack stack, ExecutionContext context) throws CommandException {
        try {
            StackUnit num = stack.peek();
            PrintWriter writer = new PrintWriter(
                    context.getStandardOutputStream(),
                    true);
            writer.println(num.getValue());

            logger.log(Level.INFO, context.getNumberCurExecutableString() + ": " +
                    num + " printed");
        } catch (UnableGettingUnitException e) {
            logger.log(Level.ERROR, context.getNumberCurExecutableString() + ": " +
                    "unable print value " + e.getMessage());
            throw new CommandException(context.getNumberCurExecutableString() + ": " +
                    "unable print value: " + e.getMessage(), e);
        }
    }
}