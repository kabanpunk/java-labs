package ru.nsu.lab3.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ru.nsu.lab3.model.Board;
import ru.nsu.lab3.model.Cell;
import static ru.nsu.lab3.controller.Consts.CELL_SIZE;
import static ru.nsu.lab3.controller.Consts.*;

public class GameController {
    @FXML
    Button newGameButton;
    @FXML
    Pane boardPane;
    @FXML
    GridPane gridPane;

    private Board board;

    public void initGame(int numberOfBoardLines, int numberOfBoardRows, int numberOfMines) {
        boardPane.getChildren().clear();

        boardPane.setPrefHeight(numberOfBoardLines * CELL_SIZE);
        boardPane.setPrefWidth(numberOfBoardRows * CELL_SIZE);

        gridPane.setPrefHeight(numberOfBoardLines * CELL_SIZE + 50);
        gridPane.setPrefWidth(numberOfBoardRows * CELL_SIZE);

        board = new Board(numberOfBoardLines, numberOfBoardRows, numberOfMines);

        int tmp = board.getNumberOfLines();
        for (int i = 0; i < board.getNumberOfLines(); i++) {
            for (int j = 0; j < board.getNumberOfRows(); j++) {
                Pane cell = new Pane( );

                cell.setTranslateX(j * CELL_SIZE);
                cell.setTranslateY(i * CELL_SIZE);
                cell.setPrefWidth(CELL_SIZE);
                cell.setPrefHeight(CELL_SIZE);
                cell.getProperties().put("x", j);
                cell.getProperties().put("y", i);

                cell.setOnMouseClicked(e -> {
                    if (board.isGameInProcess()) {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            board.flagCell(
                                    (int) cell.getProperties().get("y"),
                                    (int) cell.getProperties().get("x")
                            );
                        } else if (e.getButton() == MouseButton.PRIMARY) {
                            board.openCell(
                                    (int) cell.getProperties().get("y"),
                                    (int) cell.getProperties().get("x")
                            );
                        }
                        draw();
                    }
                });

                cell.setStyle("-fx-border-color: #2a2525; -fx-border-width: 1px; -fx-background-color: #949494;");

                boardPane.getChildren().add(cell);
            }
        }
    }



    public void draw() {
        for (int y = 0; y < board.getNumberOfLines(); y++) {
            for (int x = 0; x < board.getNumberOfRows(); x++) {
                Cell cell = board.getCell(y, x);
                Pane cellPane = (Pane) boardPane.getChildren().get(y * board.getNumberOfRows() + x);
                cellPane.getChildren().clear();

                if (cell.isFlagged()) {
                    setCellPaneTexture(cellPane, IMAGE_FLAG);
                }

                if (cell.isOpen()) {
                    cellPane.setStyle("-fx-border-color: #2a2525; -fx-border-width: 1px; -fx-background-color: #d3d3d3;");

                    if (cell.isMined()) {
                        setCellPaneTexture(cellPane, IMAGE_MINE);
                    } else if (cell.getAdjacentMines() > 0) {
                        switch (cell.getAdjacentMines()) {
                            case 1 -> setCellPaneTexture(cellPane, IMAGE_ONE_MINE);
                            case 2 -> setCellPaneTexture(cellPane, IMAGE_TWO_MINES);
                            case 3 -> setCellPaneTexture(cellPane, IMAGE_THREE_MINES);
                            case 4 -> setCellPaneTexture(cellPane, IMAGE_FOUR_MINES);
                            case 5 -> setCellPaneTexture(cellPane, IMAGE_FIVE_MINES);
                            case 6 -> setCellPaneTexture(cellPane, IMAGE_SIX_MINES);
                            case 7 -> setCellPaneTexture(cellPane, IMAGE_SEVEN_MINES);
                            case 8 -> setCellPaneTexture(cellPane, IMAGE_EIGHT_MINES);
                        }
                    }
                }
            }
        }

        if (board.checkVictory()) {
            Alert winAlert = new Alert(Alert.AlertType.NONE, "You Won!", ButtonType.OK);
            winAlert.show();
        }
    }

    private void setCellPaneTexture(Pane cellPane, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);
        cellPane.getChildren().add(imageView);
    }


}
