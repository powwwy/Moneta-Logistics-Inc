package src.main.java.com.example.moneta.managers;

import java.sql.*;
import src.main.java.com.example.moneta.handler.DBUtil;

public class ShipManager {

    private String currentShipNumber;

    public String login(String shipNumber) {
        if (shipNumber == null || shipNumber.isBlank()) {
            return "Please enter a valid ship number.";
        }

        try (PreparedStatement stmt = DBUtil.getConnection().prepareStatement(
                "SELECT number FROM ships WHERE number = ?")) {
            stmt.setString(1, shipNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                currentShipNumber = shipNumber;
                return "Logged in as Ship #" + shipNumber;
            } else {
                return "Ship not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error during login.";
        }
    }

    public String dock(String portID) {
        if (currentShipNumber == null) {
            return "Please login to a ship first.";
        }

        if (portID == null || portID.isBlank()) {
            return "Please enter a valid port ID.";
        }

        try (PreparedStatement stmt = DBUtil.getConnection().prepareStatement(
                "UPDATE ships SET arrivalDate = CURRENT_DATE, status = 'Docked' WHERE number = ?")) {
            stmt.setString(1, currentShipNumber);
            int updated = stmt.executeUpdate();
            return updated > 0 ?
                    "Ship #" + currentShipNumber + " docked at Port #" + portID :
                    "Failed to dock ship.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error during docking.";
        }
    }

    public String depart() {
        if (currentShipNumber == null) {
            return "Please login to a ship first.";
        }

        try (PreparedStatement stmt = DBUtil.getConnection().prepareStatement(
                "UPDATE ships SET departureDate = CURRENT_DATE, status = 'Departed' WHERE number = ?")) {
            stmt.setString(1, currentShipNumber);
            int updated = stmt.executeUpdate();
            return updated > 0 ?
                    "Ship #" + currentShipNumber + " has departed." :
                    "Failed to update ship status.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error during departure.";
        }
    }
}
