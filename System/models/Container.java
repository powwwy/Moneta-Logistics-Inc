package System.models;

import System.handler.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Container {
    static final Scanner scanner = new Scanner(System.in);
    private static String currentShipNumber = null;

    public static void loginToShip() {
        System.out.print("Enter your Ship Number to manage containers: ");
        currentShipNumber = scanner.nextLine();

        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM ships WHERE number = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, currentShipNumber);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Logged in to ship: " + rs.getString("name"));
                    } else {
                        System.out.println("Ship not found.");
                        currentShipNumber = null;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            currentShipNumber = null;
        }
    }

    public static void addContainer() {
        if (currentShipNumber == null) {
            System.out.println("Login to a ship first.");
            return;
        }

        System.out.print("Enter container description: ");
        String description = scanner.nextLine();
        System.out.print("Enter container weight (kg): ");
        double weight = Double.parseDouble(scanner.nextLine());

        try (Connection conn = DBUtil.getConnection()) {
            String insert = "INSERT INTO containers (description, weight, status, shipNumber) VALUES (?, ?, 'Loaded', ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1, description);
                stmt.setDouble(2, weight);
                stmt.setString(3, currentShipNumber);
                stmt.executeUpdate();
                System.out.println("Container added and loaded onto ship.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding container: " + e.getMessage());
        }
    }

    public static void viewContainers() {
        if (currentShipNumber == null) {
            System.out.println("Login to a ship first.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM containers WHERE shipNumber = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, currentShipNumber);
                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("\nContainers on ship " + currentShipNumber + ":");
                    while (rs.next()) {
                        System.out.println("- ID: " + rs.getInt("containerID")
                                + ", Description: " + rs.getString("description")
                                + ", Weight: " + rs.getDouble("weight")
                                + "kg, Status: " + rs.getString("status"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing containers: " + e.getMessage());
        }
    }

    public static void unloadContainer() {
        if (currentShipNumber == null) {
            System.out.println("Login to a ship first.");
            return;
        }

        System.out.print("Enter container ID to unload: ");
        String containerID = scanner.nextLine();

        try (Connection conn = DBUtil.getConnection()) {
            String update = "UPDATE containers SET status = 'Unloaded', shipNumber = NULL WHERE containerID = ? AND shipNumber = ?";
            try (PreparedStatement stmt = conn.prepareStatement(update)) {
                stmt.setString(1, containerID);
                stmt.setString(2, currentShipNumber);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Container unloaded successfully.");
                } else {
                    System.out.println("Container not found on current ship.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error unloading container: " + e.getMessage());
        }
    }
}
