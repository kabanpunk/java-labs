package com.company;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import static com.company.Settings.TABLE_VALUE_SEPARATOR;

public class CsvFileWriter implements AutoCloseable {
    private final FileWriter writer;

    public CsvFileWriter(String path) throws Exception {
        writer  = new FileWriter(path);
    }

    public void writeData(List<Map.Entry<String, Integer>> sortedFreqList, int numberOfWords) throws Exception {
        for (Map.Entry<String, Integer> entry : sortedFreqList) {
            double percentageFrequency = entry.getValue() / (double) numberOfWords * 100;
            writer.write(entry.getKey() + TABLE_VALUE_SEPARATOR + entry.getValue() + TABLE_VALUE_SEPARATOR + percentageFrequency);
            writer.write(System.lineSeparator());
        }
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}
