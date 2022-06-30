package ru.nsu.lab3.model;

public class Cell {



    private int adjacentMines;
    private boolean hidden;
    private boolean mined;
    private boolean opened;
    private boolean flagged;


    public Cell() {
        hidden = true;
    }

    public String getString() {
        if (isMined())
            return "M";
        return Integer.toString(adjacentMines);
    }



    public void open() {
        opened = true;
    }

    public boolean isOpen() {
        return opened;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isMined() {
        return mined;
    }

    public void setMined() {
        mined = true;
    }

    public void setFlag() {
        flagged = true;
    }

    public void removeFlag() {
        flagged = false;
    }

    public void incrementAdjacentMines() {
        adjacentMines++;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    @Override
    public String toString() {
        if (isMined()) {
            return "B"; //💣
        }
        else if (isFlagged()) {
            return "F"; //🚩
        }
        else if (isOpen()) {
            return Integer.toString(adjacentMines);
        }
        return " ";
    }
}
