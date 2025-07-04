package src.main.java.com.example.moneta.controllers;

import src.main.java.com.example.moneta.managers.ShipManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ShipMenuController {

    @FXML private TextField shipNumberField;
    @FXML private TextField dockPortField;
    @FXML private TextArea shipStatusArea;

    public ShipManager shipManager = new ShipManager();

    @FXML
    private void handleLogin() {
        String result = shipManager.login(shipNumberField.getText().trim());
        shipStatusArea.setText(result);
    }

    @FXML
    private void handleDock() {
        String result = shipManager.dock(dockPortField.getText().trim());
        shipStatusArea.setText(result);
    }

    @FXML
    private void handleDepart() {
        String result = shipManager.depart();
        shipStatusArea.setText(result);
    }

    public void setManager(ShipManager shipManager) {
    }
}
