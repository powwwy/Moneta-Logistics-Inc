package src.main.java.com.example.moneta.helper;

import javafx.beans.property.SimpleStringProperty;

public class PortTableRow {
    private final SimpleStringProperty portName;
    private final SimpleStringProperty country;
    private final SimpleStringProperty portNumber;

    public PortTableRow(String portName, String country, String portNumber) {
        this.portName = new SimpleStringProperty(portName);
        this.country = new SimpleStringProperty(country);
        this.portNumber = new SimpleStringProperty(portNumber);
    }

    // Getters
    public String getPortName() { return portName.get(); }
    public String getCountry() { return country.get(); }
    public String getPortNumber() { return portNumber.get(); }

    // Property Getters (for TableView bindings)
    public SimpleStringProperty portNameProperty() { return portName; }
    public SimpleStringProperty countryProperty() { return country; }
    public SimpleStringProperty portNumberProperty() { return portNumber; }

    // Optional Setters
    public void setPortName(String name) { this.portName.set(name); }
    public void setCountry(String country) { this.country.set(country); }
    public void setPortNumber(String number) { this.portNumber.set(number); }
}
