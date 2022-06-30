package ru.nsu.lab3.controller;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static ru.nsu.lab3.controller.Config.STATS_PATH;

public class Stats {
    private boolean isReaded = false;
    private HashMap<String, Double> stats;

    public Stats() {
        stats  = new HashMap<>();
        readStats();
    }

    private void readStats() {
        try (BufferedReader br = new BufferedReader(new FileReader(STATS_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                stats.put(parts[0], Double.parseDouble(parts[1]));
            }
            isReaded = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUserScore(String userName, double score) {
        if (stats.containsKey(userName)) {
            if (stats.get(userName) < score) {
                stats.put(userName, score);
            }
        }
        else {
            stats.put(userName, score);
        }
    }

    public void writeStats( ) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(STATS_PATH));
            for (HashMap.Entry<String, Double> entry : stats.entrySet()) {
                String userName = entry.getKey();
                Double score = entry.getValue();
                writer.write(userName + ";" + score + "\r\n");
            }
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public HashMap<String, Double> getStats() {
        return stats;
    }
}
