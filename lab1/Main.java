package com.company;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.company.Settings.INPUT_FILE;
import static com.company.Settings.OUTPUT_FILE;

public class Main {

    public static void main(String[] args) throws Exception {
        try (InputFileReader inputFileReader = new InputFileReader(args[INPUT_FILE]);
             CsvFileWriter csvFileWriter = new CsvFileWriter(args[OUTPUT_FILE]);
        ) {
            inputFileReader.fillFreqMap();

            List<Map.Entry<String, Integer>> sortedFreqList = inputFileReader.getFreqMap().entrySet().stream().sorted(
                    Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList()
            );

            csvFileWriter.writeData(sortedFreqList, inputFileReader.getNumberOfWords());
        }
        catch (Exception error) {
            System.err.println(error.getMessage());
        }
    }
}
