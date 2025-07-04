package src.main.java.com.example.moneta.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;
import src.main.java.com.example.moneta.models.Ship;
import src.main.java.com.example.moneta.models.Port;
import src.main.java.com.example.moneta.managers.CountryManager;

public class CountryMenuController {

    // Ship Table Components
    @FXML private TableView<Ship> shipsTable;
    @FXML private TableColumn<Ship, String> shipNameColumn;
    @FXML private TableColumn<Ship, String> shipNumberColumn;
    @FXML private TableColumn<Ship, Integer> shipCapacityColumn;
    @FXML private TableColumn<Ship, String> shipStatusColumn;

    // Port Table Components
    @FXML private TableView<Port> portsTable;
    @FXML private TableColumn<Port, String> portNameColumn;
    @FXML private TableColumn<Port, String> portNumberColumn;
    @FXML private TableColumn<Port, Integer> portMaxShipsColumn;
    @FXML private TableColumn<Port, Integer> portMaxContainersColumn;

    // Form Fields
    @FXML private TextField createShipNameField;
    @FXML private TextField createShipNumberField;
    @FXML private TextField createShipCapacityField;
    @FXML private TextField createPortNameField;
    @FXML private TextField createPortNumberField;
    @FXML private TextField createPortMaxShipsField;
    @FXML private TextField createPortMaxContainersField;

    // Labels
    @FXML private Label balanceLabel;
    @FXML private Label statusLabel;
    @FXML private Label welcomeLabel;

    private String countryID;
    private CountryManager countryManager;

    public CountryMenuController() {
        // Default constructor needed for FXML loading
    }

    @FXML
    public void initialize() {
        // Ship table setup
        shipNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        shipNumberColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNumber()));
        shipCapacityColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCapacity()).asObject());
        shipStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        // Port table setup
        portNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        portNumberColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNumber()));
        portMaxShipsColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaxShips()).asObject());
        portMaxContainersColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaxContainers()).asObject());

        // Initialize manager
        countryManager = new CountryManager();
    }

    @FXML
    private void handleViewShips() {
        ObservableList<Ship> shipList = FXCollections.observableArrayList(
                countryManager.getShips(countryID)
        );
        shipsTable.setItems(shipList);
        updateStatus("Ships loaded successfully");
    }

    @FXML
    private void handleViewPorts() {
        ObservableList<Port> portList = FXCollections.observableArrayList(
                countryManager.getPorts(countryID)
        );
        portsTable.setItems(portList);
        updateStatus("Ports loaded successfully");
    }

    @FXML
    private void handleAddPort() {
        try {
            String name = createPortNameField.getText();
            String number = createPortNumberField.getText();
            int maxShips = Integer.parseInt(createPortMaxShipsField.getText());
            int maxContainers = Integer.parseInt(createPortMaxContainersField.getText());

            if (name.isEmpty() || number.isEmpty()) {
                showAlert("Error", "Please fill all fields");
                return;
            }

            if (maxShips <= 0 || maxContainers <= 0) {
                showAlert("Error", "Values must be positive numbers");
                return;
            }

            countryManager.addPort(countryID, name, number, maxShips, maxContainers);
            clearPortForm();
            handleViewPorts();
            updateStatus("Port added successfully");
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers for max ships/containers");
        }
    }

    @FXML
    private void handleAddShip() {
        try {
            String name = createShipNameField.getText();
            String number = createShipNumberField.getText();
            int capacity = Integer.parseInt(createShipCapacityField.getText());

            if (name.isEmpty() || number.isEmpty()) {
                showAlert("Error", "Please fill all fields");
                return;
            }

            if (capacity <= 0) {
                showAlert("Error", "Capacity must be a positive number");
                return;
            }

            countryManager.addShip(countryID, name, number, "Idle", capacity);
            clearShipForm();
            handleViewShips();
            updateStatus("Ship added successfully");
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for capacity");
        }
    }

    @FXML
    private void handleViewBalance() {
        double balance = countryManager.getBalance(countryID);
        balanceLabel.setText("Balance: $" + balance);
        updateStatus("Balance updated");
    }

    private void clearPortForm() {
        createPortNameField.clear();
        createPortNumberField.clear();
        createPortMaxShipsField.clear();
        createPortMaxContainersField.clear();
    }

    private void clearShipForm() {
        createShipNameField.clear();
        createShipNumberField.clear();
        createShipCapacityField.clear();
    }

    private void updateStatus(String message) {
        statusLabel.setText("Status: " + message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
        welcomeLabel.setText("Welcome, Manager of " + countryID);
        handleViewBalance(); // Load initial balance
    }

    public void setManager(CountryManager manager) {
        this.countryManager = manager;
    }
}