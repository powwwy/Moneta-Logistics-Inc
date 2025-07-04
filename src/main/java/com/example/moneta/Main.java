package src.main.java.com.example.moneta;

import src.main.java.com.example.moneta.navigation.Selector;
import src.main.java.com.example.moneta.navigation.SelectorFactory;

import javafx.application.Application;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            List<String> roles = Arrays.asList("Country Manager", "Port Authority", "Ship Operator", "Container Manager");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Country Manager", roles);
            dialog.setTitle("Login");
            dialog.setHeaderText("Select Role");
            dialog.setContentText("Choose your role:");

            Optional<String> result = dialog.showAndWait();
            if (result.isEmpty()) {
                System.out.println("No role selected. Exiting.");
                return;
            }

            Selector selector = SelectorFactory.getSelector(result.get());
            selector.loadMenu(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
