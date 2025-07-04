package src.main.java.com.example.moneta.helper;

import javafx.beans.property.SimpleStringProperty;

public class ShipTableRow {
    private final SimpleStringProperty shipName;
    private final SimpleStringProperty location;
    private final SimpleStringProperty status;

    public ShipTableRow(String shipName, String location, String status) {
        this.shipName = new SimpleStringProperty(shipName);
        this.location = new SimpleStringProperty(location);
        this.status = new SimpleStringProperty(status);
    }

    // Getters
    public String getShipName() { return shipName.get(); }
    public String getLocation() { return location.get(); }
    public String getStatus() { return status.get(); }

    // Property getters
    public SimpleStringProperty shipNameProperty() { return shipName; }
    public SimpleStringProperty locationProperty() { return location; }
    public SimpleStringProperty statusProperty() { return status; }

    // Optional setters
    public void setShipName(String name) { this.shipName.set(name); }
    public void setLocation(String location) { this.location.set(location); }
    public void setStatus(String status) { this.status.set(status); }
}
