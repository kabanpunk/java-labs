package ru.nsu.lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static ru.nsu.lab1.Settings.*;

public class Main {

    public static void main(String[] args) {
        int wordCounter = 0;
        Map<String, Integer> freqMap = new HashMap<String, Integer>();
        try {
            Scanner scanner = new Scanner(new File(args[INPUT_FILE]));
            scanner.useDelimiter(WORD_SEPARATOR);
            while (scanner.hasNext()) {
                String word  = scanner.next().toLowerCase();
                if (freqMap.containsKey(word)) {
                    freqMap.put(word, freqMap.get(word) + 1);
                }
                else {
                    freqMap.put(word, 1);
                }
                wordCounter++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        LinkedHashMap<String, Integer> reverseSortedFreqMap = new LinkedHashMap<>();
        freqMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedFreqMap.put(x.getKey(), x.getValue()));

        try {
            FileWriter myWriter = new FileWriter(args[OUTPUT_FILE]);
            for (Map.Entry<String, Integer> entry : reverseSortedFreqMap.entrySet()) {
                myWriter.write(entry.getKey() + ";" + entry.getValue() + ";" + entry.getValue() / (double) wordCounter * 100);
                myWriter.write(System.lineSeparator());
            }
            myWriter.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
