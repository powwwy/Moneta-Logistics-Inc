package src.main.java.com.example.moneta.managers;

import src.main.java.com.example.moneta.models.Container;
import src.main.java.com.example.moneta.handler.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ContainerManager {

    public boolean shipExists(String shipNumber) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT number FROM ships WHERE number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, shipNumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void addContainer(String description, double weight, String shipNumber) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO containers (description, weight, shipNumber) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, description);
            stmt.setDouble(2, weight);
            stmt.setString(3, shipNumber);
            stmt.executeUpdate();
        }
    }

    public ObservableList<Container> getContainersByShip(String shipNumber) throws SQLException {
        ObservableList<Container> containers = FXCollections.observableArrayList();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT containerID, description, weight FROM containers WHERE shipNumber = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, shipNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                containers.add(new Container(
                        rs.getInt("containerID"),
                        rs.getString("description"),
                        rs.getDouble("weight")
                ));
            }
        }

        return containers;
    }

    public void removeContainer(int containerID, String shipNumber) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "DELETE FROM containers WHERE containerID = ? AND shipNumber = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, containerID);
            stmt.setString(2, shipNumber);
            stmt.executeUpdate();
        }
    }
}
