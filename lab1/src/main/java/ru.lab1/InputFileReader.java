package ru.lab1;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ru.lab1.Settings.WORD_SEPARATOR;

public class InputFileReader implements AutoCloseable{
    private final Scanner scanner;
    private Map<String, Integer> freqMap;
    private int numberOfWords;

    public InputFileReader(String path) throws Exception {
        scanner = new Scanner(new File(path));
    }

    public void fillFreqMap() throws Exception {
        freqMap = new HashMap<String, Integer>();
        numberOfWords = 0;
        scanner.useDelimiter(WORD_SEPARATOR);
        while (scanner.hasNext()) {
            String word  = scanner.next().toLowerCase();
            if (freqMap.containsKey(word)) {
                freqMap.put(word, freqMap.get(word) + 1);
            }
            else {
                freqMap.put(word, 1);
            }
            numberOfWords++;
        }
        scanner.close();
    }

    public Map<String, Integer> getFreqMap() {
        return freqMap;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}
