package src.main.java.com.example.moneta.managers;

import src.main.java.com.example.moneta.handler.DBUtil;
import src.main.java.com.example.moneta.helper.PortTableRow;
import src.main.java.com.example.moneta.helper.ShipTableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PortManager {

    public ObservableList<PortTableRow> getAllPorts() throws SQLException {
        String query = """
            SELECT p.portName, p.portNumber, c.country_name AS location
            FROM ports p
            JOIN country c ON p.countryID = c.countryID
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            ObservableList<PortTableRow> ports = FXCollections.observableArrayList();
            while (rs.next()) {
                ports.add(new PortTableRow(
                        rs.getString("portNumber"),
                        rs.getString("portName"),
                        rs.getString("location")
                ));
            }
            return ports;
        }
    }

    public String getPortDetails(String portNumber) throws SQLException {
        String query = """
            SELECT p.portName, p.status, p.maxShips, p.maxContainerCapacity, c.country_name AS location
            FROM ports p JOIN country c ON p.countryID = c.countryID
            WHERE p.portNumber = ?
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, portNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return String.format("""
                    Name: %s
                    Location: %s
                    Max Ships: %s
                    Max Container Capacity: %s
                    Status: %s
                    """,
                        rs.getString("portName"),
                        rs.getString("location"),
                        rs.getString("maxShips"),
                        rs.getString("maxContainerCapacity"),
                        rs.getString("status")
                );
            } else {
                return "Port not found.";
            }
        }
    }

    public String getDockedShips(String portNumber) throws SQLException {
        String query = """
            SELECT s.number, s.name, s.arrivalDate, s.departureDate, s.capacity, s.status
            FROM ships s
            JOIN ports p ON s.countryID = p.countryID
            WHERE p.portNumber = ? AND s.status = 'Docked'
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, portNumber);
            ResultSet rs = stmt.executeQuery();

            StringBuilder builder = new StringBuilder("=== Docked Ships ===\n");
            boolean found = false;
            while (rs.next()) {
                found = true;
                builder.append(String.format(
                        "Ship Number: %s | Name: %s | Arrival: %s | Departure: %s | Capacity: %s | Status: %s\n",
                        rs.getString("number"),
                        rs.getString("name"),
                        rs.getString("arrivalDate"),
                        rs.getString("departureDate"),
                        rs.getString("capacity"),
                        rs.getString("status")
                ));
            }

            if (!found) builder.append("No docked ships at this port.");
            return builder.toString();
        }
    }

    public ObservableList<ShipTableRow> getAllShips() throws SQLException {
        String query = """
            SELECT s.name AS shipName, p.portName AS portName, s.status
            FROM ships s
            LEFT JOIN ports p ON s.countryID = p.countryID
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            ObservableList<ShipTableRow> ships = FXCollections.observableArrayList();

            while (rs.next()) {
                String name = rs.getString("shipName");
                String status = rs.getString("status");
                String location = status.equalsIgnoreCase("At Sea") ? "At Sea" : rs.getString("portName");
                ships.add(new ShipTableRow(name, location, status));
            }

            return ships;
        }
    }
}
