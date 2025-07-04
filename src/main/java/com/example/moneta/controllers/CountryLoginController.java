package src.main.java.com.example.moneta.controllers;

import src.main.java.com.example.moneta.managers.CountryLoginManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CountryLoginController {

    @FXML
    private TextField countryIdField;

    @FXML
    private Label errorLabel;

    private CountryLoginManager countryManager;

    @FXML
    private void handleLogin(ActionEvent event) {
        errorLabel.setText(""); // Clear previous error
        String managerID = countryIdField.getText().trim().toUpperCase();

        if (managerID.isEmpty()) {
            errorLabel.setText("Please enter Manager ID.");
            return;
        }

        try {
            String countryID = countryManager.getCountryIDByManagerID(managerID);

            if (countryID != null) {
                // Load the Country Menu FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/moneta/country_menu.fxml"));
                Parent root = loader.load();

                // Pass country ID to controller
                CountryMenuController controller = loader.getController();
                controller.setCountryID(countryID); // Make sure this method exists

                // Switch scene
                Stage stage = new Stage();
                stage.setTitle("Country Dashboard");
                stage.setScene(new Scene(root));
                stage.show();

                // Optional: Close login window
                ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();

            } else {
                errorLabel.setText("Invalid Manager ID.");
            }

        } catch (Exception e) {
            errorLabel.setText("Login failed.");
            e.printStackTrace();
        }
    }

    public void setManager(CountryLoginManager countryLoginManager) {
        this.countryManager = countryLoginManager;
    }
}
