package ru.lab2.body;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.lab2.exceptions.body.ParseException;

public class Parser {
    private String fileName = "";
    private static final Logger logger = LogManager.getLogger(Parser.class);
    ReadMode readMode;

    public Parser(String[] args) throws ParseException {
        parse(args);
    }

    private void parse(String[] args) throws ParseException {
        logger.log(Level.INFO, "parse cline arguments...");
        if (args.length == 1) {
            fileName = args[0];
            readMode = ReadMode.FROM_FILE;
        } else if (args.length == 0) {
            readMode = ReadMode.FROM_SYSTEM_INPUT;
        } else {
            logger.log(Level.ERROR, "invalid format of command line arguments");
            throw new ParseException("a lot of arguments");
        }
        logger.log(Level.INFO, "parse cline arguments successful");
    }

    public String getFileName(){
        return fileName;
    }

    public ReadMode getReadMode(){
        return readMode;
    }
}