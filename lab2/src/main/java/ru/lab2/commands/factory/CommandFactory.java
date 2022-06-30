package ru.lab2.commands.factory;

import ru.lab2.commands.Command;
import ru.lab2.exceptions.commands.IllegalNumArgsException;
import ru.lab2.exceptions.commands.factory.CommandFactoryException;

import java.util.List;

public interface CommandFactory {
    Command makeCommand(String commandName, List<String> args) throws CommandFactoryException, IllegalNumArgsException;
    void include(String fileName) throws CommandFactoryException;
}