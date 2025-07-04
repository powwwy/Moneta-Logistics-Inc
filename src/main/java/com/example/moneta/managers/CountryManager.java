package src.main.java.com.example.moneta.managers;

import src.main.java.com.example.moneta.models.Ship;
import src.main.java.com.example.moneta.models.Port;
import src.main.java.com.example.moneta.handler.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryManager {

    public List<Ship> getShips(String countryID) {
        List<Ship> ships = new ArrayList<>();
        String query = "SELECT name, number, capacity, status FROM ships WHERE countryID=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, countryID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ship ship = new Ship();
                ship.setName(rs.getString("name"));
                ship.setNumber(rs.getString("number"));
                ship.setCapacity(rs.getInt("capacity"));
                ship.setStatus(rs.getString("status"));
                ships.add(ship);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ships;
    }

    public List<Port> getPorts(String countryID) {
        List<Port> ports = new ArrayList<>();
        String query = "SELECT portName, portNumber, maxShips, maxContainerCapacity FROM Ports WHERE countryID=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, countryID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Port port = new Port();
                port.setName(rs.getString("portName"));
                port.setNumber(rs.getString("portNumber"));
                port.setMaxShips(rs.getInt("maxShips"));
                port.setMaxContainers(rs.getInt("maxContainerCapacity"));
                ports.add(port);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ports;
    }

    public void addPort(String countryID, String name, String number, int maxShips, int maxContainers) {
        String insert = "INSERT INTO ports (countryID, portName, portNumber, maxShips, maxContainerCapacity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, countryID);
            stmt.setString(2, name);
            stmt.setString(3, number);
            stmt.setInt(4, maxShips);
            stmt.setInt(5, maxContainers);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addShip(String countryID, String name, String number,String status, int capacity) {
        String insert = "INSERT INTO ships (countryID, name, number, status, capacity) VALUES (?, ?, ? ,?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, countryID);
            stmt.setString(2, name);
            stmt.setString(3, number);
            stmt.setString(4, status);
            stmt.setInt(5, capacity);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(String countryID) {
        String query = "SELECT accountBalance FROM Country WHERE countryID=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, countryID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("accountBalance");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
