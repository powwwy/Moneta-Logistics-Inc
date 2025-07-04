package src.main.java.com.example.moneta.navigation;

import src.main.java.com.example.moneta.controllers.CountryLoginController;
import src.main.java.com.example.moneta.managers.CountryLoginManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class CountryLoginNavigator implements Selector {

    @Override
    public void loadMenu(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("/com/example/moneta/login.fxml");
        System.out.println("FXML URL: " + fxmlLocation);  // Should not be null
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        Parent root = loader.load();

        // Get the controller and inject the ContainerManager
        CountryLoginController controller = loader.getController();
        controller.setManager(new CountryLoginManager());

        stage.setTitle("Country Manager Menu");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
