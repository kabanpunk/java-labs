package ru.nsu.lab3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;

public class TableController {
    @FXML
    TableView<Object> tableView;

    @FXML
    Button backButton;

    public void initTable(Stats stats) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        TableColumn<Object, Object> nameColumn = new TableColumn<>("Nickname");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn statColumn = new TableColumn("Stat");
        statColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));

        tableView.getColumns().addAll(nameColumn, statColumn);

        for (HashMap.Entry<String, Double> entry : stats.getStats().entrySet()) {
            String userName = entry.getKey();
            Double score = entry.getValue();

            PlayerStat playerStat = new PlayerStat(userName, score);

            tableView.getItems().addAll(playerStat);
        }
    }
}
