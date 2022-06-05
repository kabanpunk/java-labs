package ru.nsu.lab3.controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.nsu.lab3.model.*;

import java.util.Arrays;
import java.util.Scanner;

import static ru.nsu.lab3.Settings.numberOfBoardLines;
import static ru.nsu.lab3.Settings.numberOfBoardRows;


public class WindowController extends Application implements Subscriber<Cell> {
    private ToggleButton[][] gameTiles;
    private Game game;

    @FXML
    GridPane gameBoard;

    @Override
    public void start(Stage stage) throws Exception {
        game = new Game(10, this);
        FXMLLoader fxmlLoader = new FXMLLoader(ru.nsu.lab3.HelloApplication.class.getResource("../view/Window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        populateGameBoard();
        stage.setScene(scene);
        stage.show();
    }

    void populateGameBoard() {
        gameTiles = new ToggleButton[numberOfBoardLines][numberOfBoardRows];
        for (int i = 0; i < numberOfBoardLines; i++) {
            for (int j = 0; j < numberOfBoardRows; j++) {
                gameTiles[i][j] = new ToggleButton();
                gameTiles[i][j].setMinSize(36, 36);
                CellEventHandler toggleButtonEventHandler = new CellEventHandler(i, j, game);
                gameTiles[i][j].setOnMouseReleased(toggleButtonEventHandler);
                // Disables keypresses on the ToggleButtons:
                gameTiles[i][j].setOnAction((event) -> {
                    ToggleButton sourceButton = (ToggleButton) event.getSource();
                    sourceButton.setSelected(false);
                });
                gameBoard.add(gameTiles[i][j], i, j);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void update(Publisher<Cell> sender, Cell argument) {

    }
}