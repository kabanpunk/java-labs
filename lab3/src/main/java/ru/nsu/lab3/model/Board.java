package ru.nsu.lab3.model;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import static ru.nsu.lab3.Settings.*;

public class Board {
    private final Cell[][] cells;

    public Board(int numberOfMines, Game game, Subscriber<Cell> subscriber) {
        cells = new Cell[numberOfBoardLines][numberOfBoardRows];
        for (int i = 0; i < numberOfBoardLines; i++) {
            for (int j = 0; j < numberOfBoardRows; j++) {
                cells[i][j] = new Cell(game, i ,j);
            }
        }

        if (numberOfMines >= numberOfBoardRows * numberOfBoardLines) {
            //throw new Exception("not valid number of mines");
        }

        for (int m = 0; m < numberOfMines; m++) {
            boolean newMineValid = false;
            while(!newMineValid){
                int row = ThreadLocalRandom.current().nextInt(0, numberOfBoardRows);
                int line = ThreadLocalRandom.current().nextInt(0, numberOfBoardLines);
                if (!cells[line][row].isMined()) {
                    cells[line][row].setMined(true);
                    newMineValid = true;
                    incrementAdjacentMinesOfCell(line - 1, row - 1);
                    incrementAdjacentMinesOfCell(line - 1, row);
                    incrementAdjacentMinesOfCell(line - 1, row + 1);
                    incrementAdjacentMinesOfCell(line, row - 1);
                    incrementAdjacentMinesOfCell(line, row + 1);
                    incrementAdjacentMinesOfCell(line + 1, row - 1);
                    incrementAdjacentMinesOfCell(line + 1, row);
                    incrementAdjacentMinesOfCell(line + 1, row + 1);
                }
            }
        }
    }

    public void uncoverArea(int line, int row) {
        if (row < 0 || line < 0 || line >= numberOfBoardLines || row >= numberOfBoardRows)
            return;
        for (Pair<Integer, Integer> perm: permutations) {
            int newLine = line + perm.getKey();
            int newRow = row + perm.getValue();
            if (0 <= newLine && newLine < numberOfBoardLines && 0 <= newRow && newRow < numberOfBoardRows) {
                uncoverArea(newLine, newRow);
            }
        }
    }

    /*
    public LinkedList<Cell> uncoverArea(int line, int row) {
        LinkedList<Cell> result = new LinkedList<>();

        if (cells[line][row].getAdjacentMines() == 0) {
            if (numberOfBoardLines == 0)
                return null;

            boolean[][] visited = new boolean[numberOfBoardLines][numberOfBoardRows];

            Queue<Cell> queue = new LinkedList<>();

            queue.add(cells[line][row]);

            while (!queue.isEmpty()) {
                Cell currentCell = queue.remove();
                int x = currentCell.getRow();
                int y = currentCell.getLine();

                if (x < 0 || y < 0 || y >= numberOfBoardLines || x >= numberOfBoardRows || visited[y][x])
                    continue;

                visited[y][x] = true;
                result.add(currentCell);

                if (cells[y][x].getAdjacentMines() > 0)
                    continue;

                for (Pair<Integer, Integer> perm: permutations) {
                    int newY = y + perm.getKey();
                    int newX = x + perm.getValue();
                    if (0 <= newY && newY < numberOfBoardLines && 0 <= newX && newX < numberOfBoardRows) {
                        queue.add(cells[newY][newX]);
                    }
                }
            }
            return result;
        }
        result.add(cells[line][row]);
        return result;
    }
    */

    private void incrementAdjacentMinesOfCell(int line, int row) {
        if (0 <= line && line < numberOfBoardLines && 0 <= row && row < numberOfBoardRows) {
            cells[line][row].incrementAdjacentMines();
        }
    }

    /*
    public void test(int line, int row) {
        System.out.print("   ");
        for(int i = 0; i < numberOfBoardLines;i++) {
            System.out.print(Integer.toString(i) + " ");
        }
        System.out.print("\n");
        System.out.print("   ");
        for(int i = 0; i < numberOfBoardLines;i++) {
            System.out.print("--");
        }
        System.out.print("\n");
        LinkedList<Cell> area = uncoverArea(line, row);
        for(int i=0; i < numberOfBoardLines;i++) {
            System.out.print(Integer.toString(i) + ": ");
            for(int j =0; j < numberOfBoardRows;j++) {
                boolean checker = false;
                for (int k = 0; k < area.size(); k++) {
                    if (area.get(k).getLine() == i && area.get(k).getRow() == j) {
                        System.out.print("+ ");
                        checker = true;
                    }
                }
                if (!checker)
                    System.out.print(cells[i][j].getString() + " ");
            }
            System.out.print("\n");
        }
    }
*/
    public void print() {
        System.out.print("   ");
        for(int i = 0; i < numberOfBoardLines;i++) {
            System.out.print(Integer.toString(i) + " ");
        }
        System.out.print("\n");
        System.out.print("   ");
        for(int i = 0; i < numberOfBoardLines;i++) {
            System.out.print("--");
        }
        System.out.print("\n");
        for(int i = 0; i < numberOfBoardLines;i++) {
            System.out.print(Integer.toString(i) + ": ");
            for(int j = 0; j < numberOfBoardRows;j++) {
                System.out.print(cells[i][j].getString() + " ");
            }
            System.out.print("\n");
        }
    }
}
