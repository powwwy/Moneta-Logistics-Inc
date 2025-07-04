module com.example.moneta {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;

    opens com.example.moneta to javafx.fxml;
    opens com.example.moneta.helper to javafx.base;
    opens com.example.moneta.models to javafx.base;
    exports com.example.moneta;
    opens com.example.moneta.controllers to javafx.base, javafx.fxml;
    opens com.example.moneta.managers to javafx.base, javafx.fxml;
    opens com.example.moneta.navigation to javafx.base, javafx.fxml;
}