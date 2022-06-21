module ru.nsu.lab3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens ru.nsu.lab3 to javafx.fxml;
    exports ru.nsu.lab3;
    exports ru.nsu.lab3.controller;
    opens ru.nsu.lab3.controller to javafx.fxml;
}