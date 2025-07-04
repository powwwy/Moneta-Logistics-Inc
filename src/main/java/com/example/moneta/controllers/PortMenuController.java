package src.main.java.com.example.moneta.controllers;

import src.main.java.com.example.moneta.helper.PortTableRow;
import src.main.java.com.example.moneta.helper.ShipTableRow;
import src.main.java.com.example.moneta.managers.PortManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PortMenuController {

    private final PortManager portManager = new PortManager();

    @FXML private TableView<PortTableRow> portTable;
    @FXML private TableColumn<PortTableRow, String> portNumberCol, portNameCol, countryCol;

    @FXML private TextField portDetailsInput;
    @FXML private TextArea portDetailsArea;

    @FXML private TextField dockPortField;
    @FXML private TextArea dockedShipsArea;

    @FXML private TableView<ShipTableRow> shipsTable;
    @FXML private TableColumn<ShipTableRow, String> shipNameCol, locationCol, statusCol;

    @FXML
    private void initialize() {
        portNumberCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortNumber()));
        portNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPortName()));
        countryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCountry()));

        shipNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getShipName()));
        locationCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
    }

    @FXML
    private void handleViewPorts(ActionEvent event) {
        try {
            ObservableList<PortTableRow> ports = portManager.getAllPorts();
            portTable.setItems(ports);
        } catch (Exception e) {
            portDetailsArea.setText("Error loading ports.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewPortDetails() {
        String portNumber = portDetailsInput.getText();
        if (portNumber == null || portNumber.isBlank()) {
            portDetailsArea.setText("Please enter a port number.");
            return;
        }

        try {
            String details = portManager.getPortDetails(portNumber);
            portDetailsArea.setText(details);
        } catch (Exception e) {
            portDetailsArea.setText("Error retrieving port details.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewDockedShips() {
        String portNumber = dockPortField.getText();
        if (portNumber == null || portNumber.isBlank()) {
            dockedShipsArea.setText("Please enter a port number.");
            return;
        }

        try {
            String ships = portManager.getDockedShips(portNumber);
            dockedShipsArea.setText(ships);
        } catch (Exception e) {
            dockedShipsArea.setText("Error retrieving docked ships.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewAllShips() {
        try {
            ObservableList<ShipTableRow> ships = portManager.getAllShips();
            shipsTable.setItems(ships);
        } catch (Exception e) {
            dockedShipsArea.setText("Error retrieving ships.");
            e.printStackTrace();
        }
    }

    public void setManager(PortManager portManager) {
    }
}
