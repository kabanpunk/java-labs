package ru.lab2.body;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.lab2.commands.Command;
import ru.lab2.exceptions.ExecutionException;
import ru.lab2.exceptions.commands.CommandException;
import ru.lab2.exceptions.memory.ExecutionEnvironmentException;
import ru.lab2.memory.ExecutionContext;
import ru.lab2.memory.ExecutionContextForStackUnits;
import ru.lab2.memory.NumberStack;
import ru.lab2.memory.NumberUnitStack;

import java.util.Map;

public class CommandsExecutorImpl implements CommandsExecutor{
    private static final Logger logger = LogManager.getLogger(CommandsExecutorImpl.class);
    @Override
    public void exec(Map<Integer, Command> storage) throws ExecutionException {
        logger.log(Level.INFO, "execution is in progress...");
        NumberStack stack = new NumberUnitStack();
        ExecutionContext context = new ExecutionContextForStackUnits();

        for(Map.Entry<Integer, Command> entry: storage.entrySet()){
            context.setNumberCurExecutableString(entry.getKey());
            try {
                entry.getValue().exec(stack, context);
            } catch (ExecutionEnvironmentException | CommandException e) {
                throw new ExecutionException(e.getMessage(), e);
            }
        }
        logger.log(Level.INFO, "successful execution");
    }
}