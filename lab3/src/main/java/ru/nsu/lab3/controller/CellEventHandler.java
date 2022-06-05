package ru.nsu.lab3.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ru.nsu.lab3.model.Game;

public class CellEventHandler implements EventHandler<MouseEvent> {

    private final int LINE;
    private final int ROW;
    private final Game game;

    public CellEventHandler(int line, int row, Game game) {
        this.LINE = line;
        this.ROW = row;
        this.game = game;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            game.showCell(LINE, ROW);

        }
        //else if (event.getButton() == MouseButton.SECONDARY) {
         //   game.toggleTileState(LINE, ROW);
       // }
    }
}