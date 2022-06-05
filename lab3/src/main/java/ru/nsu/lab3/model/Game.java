package ru.nsu.lab3.model;

public class Game {
    private final Board board;
    //private final State state;

    public Game(Integer numberOfMines, Subscriber<Cell> subscriber) {
        //gameState = new GameState(difficulty, this);
        board = new Board(numberOfMines, this, subscriber );
    }

    public void showCell(int line, int row) {
        board.uncoverArea(line, row);
    }

    public void lose() {
        System.out.println("u lose");
    }

    /*
      public void win() {
    gameBoard.setMinesAsFlags();
  }

  public void revealTile(int row, int column) {
    gameBoard.revealTileArea(row, column);
  }

  public void toggleTileState(int row, int column) {
    gameBoard.toggleTileState(row, column);
  }

  public void incrementFlagCount() {
    gameState.incrementFlagCount();
  }

  public void decrementFlagCount() {
    gameState.decrementFlagCount();
  }

  public void lose() {
    gameState.lose();
    gameBoard.revealMines();
    gameBoard.setWrongFlagsAsCrossed();
  }

  public GameStates getGameState() {
    return gameState.getState();
  }

  public void incrementTilesRevealed() {
    gameState.incrementTilesRevealed();
  }

  public void hideMines() {
    gameBoard.hideMines();
  }

  public void revealMines() {
    gameBoard.revealMines();
  }

  public int getFlagCount() {
    return gameState.getFlagCount();
  }
     */
}
