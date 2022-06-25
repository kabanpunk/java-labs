package ru.lab2;

import ru.lab2.body.Parser;
import ru.lab2.body.CommandsExecutor;
import ru.lab2.body.CommandsExecutorImpl;
import ru.lab2.body.CommandsParser;
import ru.lab2.body.CommandsParserImpl;
import ru.lab2.exceptions.CommandParseException;
import ru.lab2.exceptions.ExecutionException;
import ru.lab2.exceptions.body.ParseException;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {
    public static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.log(Level.INFO, "start");
            Parser cLParser = new Parser(args);
            CommandsParser commandsParser = CommandsParserImpl.buildWithCLineArguments(cLParser);
            var commands  = commandsParser.parse();
            CommandsExecutor executor = new CommandsExecutorImpl();
            executor.exec(commands);
            logger.log(Level.INFO, "successful completion");
        } catch (ParseException e) {
            System.err.println("Error parse command line args: \n\t" + e.getMessage());
            logger.log(Level.ERROR, "unsuccessful completion");
        } catch (CommandParseException e) {
            System.err.println("Error parse commands: \n\t" + e.getMessage());
            logger.log(Level.ERROR, "unsuccessful completion");
        } catch (ExecutionException e) {
            System.err.println("Error execution commands: \n\t" + e.getMessage());
            logger.log(Level.ERROR, "unsuccessful completion");
        }
    }
}