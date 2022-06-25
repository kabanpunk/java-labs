package ru.lab2.body;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.lab2.commands.Command;
import ru.lab2.commands.factory.CommandFactory;
import ru.lab2.commands.factory.StandardCommandFactory;
import ru.lab2.exceptions.CommandParseException;
import ru.lab2.exceptions.commands.IllegalNumArgsException;
import ru.lab2.exceptions.commands.factory.BuildFactoryException;
import ru.lab2.exceptions.commands.factory.CommandFactoryException;
import ru.lab2.exceptions.commands.factory.InvalidCommandNameException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class CommandsParserImpl implements CommandsParser{
    private ReadMode readMode;
    private String filePath;
    private Scanner scanner;
    private static final Logger logger = LogManager.getLogger(CommandsParserImpl.class);
    private static final Pattern comment = Pattern.compile("\\s*#.*");

    public CommandsParserImpl(Scanner scanner){
        readMode = ReadMode.USER_SCANNER;
        this.scanner = scanner;
    }

    private CommandsParserImpl(){
    }

    public static CommandsParserImpl buildWithCLineArguments(Parser cLineParser){
        CommandsParserImpl parser = new CommandsParserImpl();
        parser.readMode = cLineParser.getReadMode();
        parser.filePath = cLineParser.getFileName();
        return parser;
    }

    private void buildScanner() throws FileNotFoundException {
        switch (readMode){
            case FROM_FILE -> {
                logger.log(Level.INFO, "start parse from file...");
                scanner = new Scanner(new FileReader(filePath));
            }
            case FROM_SYSTEM_INPUT -> {
                logger.log(Level.INFO, "start parse from console...");
                scanner = new Scanner(System.in);
            }
            case USER_SCANNER -> logger.log(Level.INFO, "start parse from user scanner...");
        }
    }

    @Override
    public NavigableMap<Integer, Command> parse() throws CommandParseException {
        TreeMap<Integer, Command> treeMap = new TreeMap<>();

        int lineNum = 0;
        try {
            CommandFactory factory = StandardCommandFactory.builder().build();
            buildScanner();
            String str;
            while (scanner.hasNextLine()){
                lineNum++;
                str = scanner.nextLine();
                if(!str.isEmpty() &&
                        !comment.matcher(str).matches()){
                    List<String> commandBody = List.of(str.split("\\s+"));
                    Command command = factory.makeCommand(commandBody.get(0), commandBody.subList(1, commandBody.size()));
                    treeMap.put(lineNum, command);
                }
            }

        } catch (InvalidCommandNameException | IllegalNumArgsException e) {
            logger.log(Level.ERROR, lineNum + ": " + e.getMessage());
            throw new CommandParseException(lineNum + ": " + e.getMessage(), e);
        }catch (FileNotFoundException e) {
            logger.log(Level.ERROR, "file \"" + filePath + "\" not found");
            throw new CommandParseException(e.getMessage(), e);
        }catch(BuildFactoryException e){
            logger.log(Level.ERROR, "factory build error " + e.getMessage());
            throw new CommandParseException("factory build error " + e.getMessage(), e);
        } catch (CommandFactoryException e) {
            logger.log(Level.ERROR, "unknown factory exception");
            throw new CommandParseException("unknown factory exception ", e);
        } finally {
            if(readMode == ReadMode.FROM_FILE){
                scanner.close();
            }
        }
        logger.log(Level.INFO, "successful parse");
        return treeMap;
    }
}