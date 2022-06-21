package ru.nsu.lab3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class MenuController {
    @FXML
    Button startButton;
    @FXML
    Button easyButton;

    @FXML
    Button mediumButton;

    @FXML
    Button hardButton;
    @FXML
    Slider numberOfMinesSlider;
    @FXML
    Text numberOfMinesLabel;
    @FXML
    Slider numberOfLinesSlider;
    @FXML
    Text numberOfLinesLabel;
    @FXML
    Slider numberOfRowsSlider;
    @FXML
    Text numberOfRowsLabel;


}
