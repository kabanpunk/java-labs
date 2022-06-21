package ru.nsu.lab3.controller;

import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private Scene menuScene, gameScene;
    private MenuController menuController;
    private GameController gameController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("Game.fxml"));

        menuScene = new Scene(mainLoader.load());
        gameScene = new Scene(gameLoader.load());

        // Storing JavaFX controllers in variables
        menuController = mainLoader.getController();
        gameController = gameLoader.getController();



        // Set event handlers for buttons on start screen
        menuController.startButton.setOnAction(e -> {
            gameController.initGame((int)menuController.numberOfLinesSlider.getValue(), (int)menuController.numberOfRowsSlider.getValue(), (int)menuController.numberOfMinesSlider.getValue());
            primaryStage.setScene(gameScene);
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




        // Shows main screen on app start
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Minesweeper");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }
}
