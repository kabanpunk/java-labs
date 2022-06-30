package ru.lab2.commands;

import ru.lab2.exceptions.commands.CommandException;
import ru.lab2.exceptions.memory.ExecutionEnvironmentException;
import ru.lab2.memory.ExecutionContext;
import ru.lab2.memory.NumberStack;

public interface Command {
    void exec(NumberStack stack, ExecutionContext context) throws ExecutionEnvironmentException, CommandException;
}