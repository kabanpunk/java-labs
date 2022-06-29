package ru.nsu.lab3.model;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;
import static ru.nsu.lab3.model.GameState.*;

public class Board {
    private final Cell[][] cells;
    private int numberOfBoardLines, numberOfBoardRows;
    private int numberOfMines;
    private boolean isMinesGenerated;
    private GameState gameState;
    private long startTime;
    private long endTime;

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

    public Board(int numberOfBoardLines, int numberOfBoardRows, int numberOfMines) {
        this.numberOfBoardLines = numberOfBoardLines;
        this.numberOfBoardRows = numberOfBoardRows;
        this.numberOfMines = numberOfMines;

        isMinesGenerated = false;
        gameState = INPROGRESS;

        cells = new Cell[numberOfBoardLines][numberOfBoardRows];
        for (int y = 0; y < numberOfBoardLines; y++) {
            for (int x = 0; x < numberOfBoardRows; x++) {
                cells[y][x] = new Cell();
            }
        }

        if (numberOfMines >= numberOfBoardRows * numberOfBoardLines) {
            throw new IllegalArgumentException("not valid number of mines");
        }
    }

    private void generateRandomMines(int yOfException, int xOfException) {
        isMinesGenerated = true;
        for (int m = 0; m < numberOfMines; m++) {
            boolean newMineValid = false;
            while(!newMineValid){
                int x = ThreadLocalRandom.current().nextInt(0, numberOfBoardRows);
                int y = ThreadLocalRandom.current().nextInt(0, numberOfBoardLines);
                if (yOfException == y && xOfException == x) {
                    continue;
                }

                if (!cells[y][x].isMined()) {
                    cells[y][x].setMined();
                    newMineValid = true;
                    for (Pair<Integer, Integer> perm: permutations) {
                        incrementAdjacentMinesOfCell(y + perm.getKey(), x + perm.getValue());
                    }
                }
            }
        }
    }

    public void openCell(int y, int x) {
        if (!isMinesGenerated) {
            generateRandomMines(y, x);
            startTime = System.currentTimeMillis();
        }
        System.out.println(this);

        if (!cells[y][x].isFlagged() && !cells[y][x].isOpen()) {
            cells[y][x].open();
            System.out.println(this);
            if (cells[y][x].isMined()) {
                gameState = LOSE;
                endTime = System.currentTimeMillis();
                openMines();
            }
            else {
                openArea(y, x);
            }
        }
    }

    public void openArea(int y, int x) {

        if (cells[y][x].getAdjacentMines() == 0) {
            boolean[][] visited = new boolean[numberOfBoardLines][numberOfBoardRows];
            Queue<Pair<Integer, Integer>> queue = new LinkedList<>();

            queue.add(new Pair<>(y, x));

            while (!queue.isEmpty()) {
                Pair<Integer, Integer> currentCell = queue.remove();
                int currentY = currentCell.getKey();
                int currentX = currentCell.getValue();

                if (currentX < 0 || currentY < 0 || currentY >= numberOfBoardLines || currentX >= numberOfBoardRows || visited[currentY][currentX])
                    continue;

                visited[currentY][currentX] = true;
                cells[currentY][currentX].open();

                if (cells[currentY][currentX].getAdjacentMines() > 0)
                    continue;

                for (Pair<Integer, Integer> perm: permutations) {
                    int newY = currentY + perm.getKey();
                    int newX = currentX + perm.getValue();
                    if (0 <= newY && newY < numberOfBoardLines && 0 <= newX && newX < numberOfBoardRows) {
                        queue.add(new Pair<>(newY, newX));
                    }
                }
            }
        }
    }

    private void openMines() {
        for (int i = 0; i < numberOfBoardLines; i++) {
            for (int j = 0; j < numberOfBoardRows; j++) {
                if (cells[i][j].isMined()) {
                    cells[i][j].open();
                }
            }
        }
    }

    public boolean checkVictory() {
        for (int y = 0; y < numberOfBoardLines; y++) {
            for (int x = 0; x < numberOfBoardRows; x++) {
                if (!cells[y][x].isOpen() && !cells[y][x].isMined()) {
                    return false;
                }

                if (cells[y][x].isMined() && !cells[y][x].isFlagged()) {
                    return false;
                }
            }
        }
        endTime = System.currentTimeMillis();
        gameState = WON;
        return true;
    }

    private void incrementAdjacentMinesOfCell(int line, int row) {
        if (0 <= line && line < numberOfBoardLines && 0 <= row && row < numberOfBoardRows) {
            cells[line][row].incrementAdjacentMines();
        }
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("   ");
        for(int i = 0; i < numberOfBoardRows;i++) {
            result.append(Integer.toString(i)).append(" ");
        }
        result.append("\n   ");
        for(int i = 0; i < numberOfBoardLines;i++) {
            result.append("--");
        }
        result.append("\n");
        for(int i = 0; i < numberOfBoardLines;i++) {
            result.append(Integer.toString(i) + ": ");
            for(int j = 0; j < numberOfBoardRows;j++) {
                result.append(cells[i][j] + " ");
            }
            result.append("\n");
        }
        return String.valueOf(result);
    }

    public int getNumberOfLines() {
        return numberOfBoardLines;
    }

    public int getNumberOfRows() {
        return numberOfBoardRows;
    }

    public boolean isGameInProcess() {
        return gameState == INPROGRESS;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void flagCell(int y, int x) {
        if (cells[y][x].isFlagged()) {
            cells[y][x].removeFlag();
        }
        else if (!cells[y][x].isOpen()) {
            cells[y][x].setFlag();
        }

    }

    public Cell getCell(int y, int x) {
        return cells[y][x];
    }

    public double getScore() {
        if (gameState != INPROGRESS) {
            return round((double) numberOfBoardLines * numberOfBoardRows / abs((numberOfBoardLines * numberOfBoardRows - 1) / 2 - numberOfMines) / 2. / ((endTime - startTime) / 1000.), 2);
        }
        return 0.;
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
