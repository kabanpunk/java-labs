package ru.lab2.commands.factory;

import ru.lab2.commands.Command;
import ru.lab2.exceptions.commands.IllegalNumArgsException;
import ru.lab2.exceptions.commands.factory.BuildFactoryException;
import ru.lab2.exceptions.commands.factory.CommandFactoryException;
import ru.lab2.exceptions.commands.factory.InvalidCommandNameException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class StandardCommandFactory implements CommandFactory{
    private final Properties commands;

    public StandardCommandFactory(){
        commands = new Properties();
    }

    @Override
    public Command makeCommand(String commandName, List<String> args) throws CommandFactoryException, IllegalNumArgsException {
        String commandPath = commands.getProperty(commandName);
        if(commandPath == null){
            throw InvalidCommandNameException.buildUnknownCommand(commandName);
        }
        try {
            Constructor<?> constructor = Class.forName(commandPath).getConstructor(List.class);
            return (Command) constructor.newInstance(args);
        }catch (InvocationTargetException ex){
            throw (IllegalNumArgsException)ex.getCause();
        } catch (ClassNotFoundException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException ex) {
            throw new CommandFactoryException(ex);
        }
    }


    public void include(String fileName) throws BuildFactoryException {
        try(InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            commands.load(stream);
        }catch (IOException ex){
            throw new BuildFactoryException("include " + fileName + " failed", ex);
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        StandardCommandFactory factory = new StandardCommandFactory();

        public StandardCommandFactory build() throws CommandFactoryException {
            try(Scanner scanner = new Scanner(
                    Objects.requireNonNull(
                            getClass().getClassLoader().getResourceAsStream("configuration_paths")))) {
                while (scanner.hasNextLine()){
                    factory.include(scanner.nextLine());
                }
            }

            return factory;
        }
    }
}