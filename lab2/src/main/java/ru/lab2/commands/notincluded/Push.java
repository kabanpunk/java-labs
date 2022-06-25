package ru.lab2.commands.notincluded;

import ru.lab2.exceptions.commands.CommandException;
import ru.lab2.exceptions.commands.IllegalNumArgsException;
import ru.lab2.exceptions.memory.UseConstantException;
import ru.lab2.memory.ExecutionContext;
import ru.lab2.memory.NumberStack;
import ru.lab2.memory.stackunits.StackUnit;
import ru.lab2.commands.Command;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class Push implements Command {
    private final String variable;
    private static final Logger logger = LogManager.getLogger(Push.class);

    public Push(List<String> args) throws IllegalNumArgsException {
        if(args.size() != 1){
            throw IllegalNumArgsException.build(args.size(), 1);
        }

        variable = args.get(0);
    }

    @Override
    public void exec(NumberStack stack, ExecutionContext context) throws UseConstantException, CommandException {
        try {
            StackUnit num = context.getUnitByArg(variable);
            stack.push(num);
            logger.log(Level.INFO, context.getNumberCurExecutableString() + ": "
                    + num.toString() + " pushed");
        } catch (UseConstantException e){
            logger.log(Level.ERROR, context.getNumberCurExecutableString() + ": " + e.getMessage());
            throw new CommandException(context.getNumberCurExecutableString() + ": " + e.getMessage(), e);
        }
    }
}