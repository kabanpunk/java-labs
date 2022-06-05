package ru.nsu.lab3;

import javafx.util.Pair;

public class Settings {
    public static int numberOfBoardLines = 10;
    public static int numberOfBoardRows = 10;

    public static Pair<Integer, Integer>[] permutations = new Pair[]{
            new Pair<>(-1, -1),
            new Pair<>(-1, +0),
            new Pair<>(-1, +1),
            new Pair<>(+0, -1),
            new Pair<>(+0, +1),
            new Pair<>(+1, -1),
            new Pair<>(+1, +0),
            new Pair<>(+1, +1),
    };
}