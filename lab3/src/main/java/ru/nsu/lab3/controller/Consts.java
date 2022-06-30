package ru.nsu.lab3.controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

final public class Consts {
    public static final String resourcesPath = "/resources/"; // Path to images
    public static final int CELL_SIZE = 30;

    public static final Image IMAGE_ONE_MINE = loadImage("1.png");
    public static final Image IMAGE_TWO_MINES = loadImage("2.png");
    public static final Image IMAGE_THREE_MINES = loadImage("3.png");
    public static final Image IMAGE_FOUR_MINES = loadImage("4.png");
    public static final Image IMAGE_FIVE_MINES = loadImage("5.png");
    public static final Image IMAGE_SIX_MINES = loadImage("6.png");
    public static final Image IMAGE_SEVEN_MINES = loadImage("7.png");
    public static final Image IMAGE_EIGHT_MINES = loadImage("8.png");
    public static final Image IMAGE_FLAG = loadImage("flag.png");
    public static final Image IMAGE_MINE = loadImage("mine.png");

    private static Image loadImage(String path) {
        try {
            InputStream imageStream = Consts.class.getResourceAsStream( path );
            Image image = new Image(imageStream);
            imageStream.close();
            return image;
        } catch (NullPointerException | IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR,
                    "Failed to load game resources. Try reinstalling the game.", ButtonType.OK);
            errorAlert.showAndWait();
            return null;
        }
    }
}
