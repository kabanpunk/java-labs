package ru.lab2.body;

import ru.lab2.commands.Command;
import ru.lab2.exceptions.CommandParseException;

import java.util.NavigableMap;

public interface CommandsParser {
    NavigableMap<Integer, Command> parse() throws CommandParseException;
}