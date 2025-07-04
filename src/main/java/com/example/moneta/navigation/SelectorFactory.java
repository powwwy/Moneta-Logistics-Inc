package src.main.java.com.example.moneta.navigation;

public class SelectorFactory {

    public static Selector getSelector(String role) {
        switch ( role ) {
            case "Container Manager":
                return new ContainerNavigator();
            case "Ship Operator":
                return new ShipNavigator();
            case "Port Authority":
                return new PortNavigator();
            case "Country Manager":
                return new CountryLoginNavigator();
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
