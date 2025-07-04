package src.main.java.com.example.moneta.controllers;

import src.main.java.com.example.moneta.models.Container;
import src.main.java.com.example.moneta.managers.ContainerManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ContainerMenuController {

    @FXML
    private TextField shipLoginField, containerDescriptionField, containerWeightField;
    @FXML
    private Button loginButton, addButton, unloadButton;
    @FXML
    private TableView<Container> containersTable;
    @FXML
    private TableColumn<Container, Integer> containerIDColumn;
    @FXML
    private TableColumn<Container, String> containerDescriptionColumn;
    @FXML
    private TableColumn<Container, Double> containerWeightColumn;
    @FXML
    private Label statusLabel;

    private final ContainerManager containerManager = new ContainerManager();
    private String currentShipID;
    private Object manager;

    @FXML
    private void initialize() {
        containerIDColumn.setCellValueFactory(new PropertyValueFactory<>("containerID"));
        containerDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        containerWeightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
    }

    @FXML
    private void handleLoginToShip() {
        String shipNumber = shipLoginField.getText();

        try {
            if (containerManager.shipExists(shipNumber)) {
                currentShipID = shipNumber;
                statusLabel.setText("Logged into ship successfully.");
                handleViewContainers();
            } else {
                statusLabel.setText("Ship not found.");
            }
        } catch (SQLException e) {
            statusLabel.setText("Error logging into ship.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddContainer() {
        if (currentShipID == null) {
            statusLabel.setText("Login to a ship first.");
            return;
        }

        String description = containerDescriptionField.getText();
        String weightText = containerWeightField.getText();

        if (description.isEmpty() || weightText.isEmpty()) {
            statusLabel.setText("All fields required.");
            return;
        }

        try {
            double weight = Double.parseDouble(weightText);
            containerManager.addContainer(description, weight, currentShipID);
            statusLabel.setText("Container added.");
            handleViewContainers();
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid weight format.");
        } catch (SQLException e) {
            statusLabel.setText("Error adding container.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewContainers() {
        if (currentShipID == null) return;

        try {
            containersTable.setItems(containerManager.getContainersByShip(currentShipID));
        } catch (SQLException e) {
            statusLabel.setText("Error loading containers.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUnloadContainer() {
        Container selected = containersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Select a container to unload.");
            return;
        }

        try {
            containerManager.removeContainer(selected.getContainerID(), currentShipID);
            statusLabel.setText("Container unloaded.");
            handleViewContainers();
        } catch (SQLException e) {
            statusLabel.setText("Error unloading container.");
            e.printStackTrace();
        }
    }

    public Object setManager(ContainerManager containerManager) {
        return manager;
    }
}