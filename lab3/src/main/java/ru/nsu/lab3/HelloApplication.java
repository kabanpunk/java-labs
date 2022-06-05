package ru.nsu.lab3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import ru.nsu.lab3.model.Board;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /*
        Board board = new Board(20);
        Scanner sc = new Scanner(System. in );
        board.print();
        System.out.print("\n");
        while (true) {
            String line = sc.nextLine() ;
            if (line.equals(""))
                break;
            int[] values = Arrays.stream(line.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            board.test(values[0],values[1]);
            System.out.print("\n");
        }
        System.out.print("\n");
*/

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        //stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}