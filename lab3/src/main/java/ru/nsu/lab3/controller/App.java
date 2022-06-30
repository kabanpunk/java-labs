package ru.nsu.lab3.controller;

import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class App extends Application {
    private Scene menuScene, gameScene, tableScene;
    private MenuController menuController;
    private GameController gameController;
    private TableController tableController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("Table.fxml"));

        menuScene = new Scene(mainLoader.load());
        gameScene = new Scene(gameLoader.load());
        tableScene = new Scene(tableLoader.load());

        // Storing JavaFX controllers in variables
        menuController = mainLoader.getController();
        gameController = gameLoader.getController();
        tableController = tableLoader.getController();

        Stats stats = new Stats();

        // Set event handlers for buttons on start screen
        menuController.startButton.setOnAction(e -> {
            String userName = menuController.userName.getText();
            if (!Objects.equals(userName, "")) {
                gameController.initGame(userName, stats, (int)menuController.numberOfLinesSlider.getValue(), (int)menuController.numberOfRowsSlider.getValue(), (int)menuController.numberOfMinesSlider.getValue());
                primaryStage.setScene(gameScene);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.NONE, "PLS INPUT USERNAME", ButtonType.OK);
                alert.show();
            }
        });

        menuController.aboutButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "This is Minesweeper \r\n blablabbla \r\n @2022 ", ButtonType.OK);
            alert.show();
        });

        menuController.statsButton.setOnAction(e -> {
            tableController.initTable(stats);
            primaryStage.setScene(tableScene);
        });

        menuController.easyButton.setOnAction(e -> {
            menuController.numberOfLinesSlider.setValue(9);
            menuController.numberOfRowsSlider.setValue(9);
            menuController.numberOfMinesSlider.setValue(10);
        });

        menuController.mediumButton.setOnAction(e -> {
            menuController.numberOfLinesSlider.setValue(16);
            menuController.numberOfRowsSlider.setValue(16);
            menuController.numberOfMinesSlider.setValue(40);
        });

        menuController.hardButton.setOnAction(e -> {
            menuController.numberOfLinesSlider.setValue(30);
            menuController.numberOfRowsSlider.setValue(16);
            menuController.numberOfMinesSlider.setValue(99);
        });

        menuController.numberOfLinesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                        menuController.numberOfLinesLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
                        menuController.numberOfMinesSlider.setMax(
                                (int) menuController.numberOfLinesSlider.getValue() * (int) menuController.numberOfRowsSlider.getValue() - 1
                        );
            }
        });

        menuController.numberOfRowsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                menuController.numberOfRowsLabel.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
                menuController.numberOfMinesSlider.setMax(
                        (int) menuController.numberOfLinesSlider.getValue() * (int) menuController.numberOfRowsSlider.getValue() - 1
                );
            }
        });

        menuController.numberOfMinesSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                menuController.numberOfMinesLabel.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
            }
        });

        // Set event handlers for game screen

        gameController.newGameButton.setOnAction(e -> primaryStage.setScene(menuScene));

        tableController.backButton.setOnAction(e -> primaryStage.setScene(menuScene));


        // Shows main screen on app start
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Minesweeper");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }
}
