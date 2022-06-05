package ru.nsu.lab3.model;


public class Cell {
    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }

    private boolean isMined;
    private final int line;
    private final int row;
    private int adjacentMines;
    private CellStatus status = CellStatus.HIDE;
    private Game game;

    public Cell(Game game, int line, int row) {
        this.line = line;
        this.row = row;
        this.game = game;
    }

    public String getString() {
        if (isMined)
            return "M";
        return Integer.toString(adjacentMines);
    }

    public void uncover(int line, int row) {
        if (status == CellStatus.HIDE) {
            status = CellStatus.REVEALED;
            if (isMined)
                game.lose();
        }
    }

    public int getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    public void incrementAdjacentMines() {
        adjacentMines++;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }
}
