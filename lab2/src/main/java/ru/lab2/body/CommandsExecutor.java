package ru.lab2.body;

import ru.lab2.commands.Command;
import ru.lab2.exceptions.ExecutionException;

import java.util.Map;

public interface CommandsExecutor {
    void exec(Map<Integer, Command> storage) throws ExecutionException;
}