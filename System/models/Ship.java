package System.models;

import System.handler.DBUtil;

import java.sql.*;
import java.util.Scanner;

public class Ship {
    private static final Scanner scanner = new Scanner(System.in);
    private static String currentShipNumber = null;

    public static void loginToShip() {
        System.out.print("Enter your Ship Number: ");
        currentShipNumber = scanner.nextLine();

        try (Connection conn = DBUtil.getConnection()) {
            String checkQuery = "SELECT * FROM ships WHERE number = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
                stmt.setString(1, currentShipNumber);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Successfully logged in to ship: " + rs.getString("name"));
                    } else {
                        System.out.println("Ship Number not found.");
                        currentShipNumber = null;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error accessing ship: " + e.getMessage());
            currentShipNumber = null;
        }
    }

    public static void dockAtPort() {
        if (currentShipNumber == null) {
            System.out.println("You need to login to a ship first.");
            return;
        }

        System.out.print("Enter Port Number to dock at: ");
        String portNumber = scanner.nextLine();

        try (Connection conn = DBUtil.getConnection()) {
            // Check if port exists
            String portCheckQuery = "SELECT * FROM ports WHERE portNumber = ?";
            try (PreparedStatement portStmt = conn.prepareStatement(portCheckQuery)) {
                portStmt.setString(1, portNumber);
                try (ResultSet rs = portStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Port Number not found.");
                        return;
                    }
                }
            }

            // Update ship status only (don't save port info directly)
            String updateShip = "UPDATE ships SET status = 'Docked' WHERE number = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateShip)) {
                updateStmt.setString(1, currentShipNumber);
                int rows = updateStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Ship docked at port " + portNumber + ". (Note: Not permanently linked)");
                } else {
                    System.out.println("Failed to dock the ship.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error docking ship: " + e.getMessage());
        }
    }

    public static void departFromPort() {
        if (currentShipNumber == null) {
            System.out.println("You need to login to a ship first.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String checkQuery = "SELECT status FROM ships WHERE number = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, currentShipNumber);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        String status = rs.getString("status");
                        if (!status.equalsIgnoreCase("Docked")) {
                            System.out.println("Ship is not currently docked.");
                            return;
                        }
                    } else {
                        System.out.println("Ship not found.");
                        return;
                    }
                }
            }

            String updateShip = "UPDATE ships SET status = 'Departed' WHERE number = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateShip)) {
                updateStmt.setString(1, currentShipNumber);
                int rows = updateStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Ship has departed.");
                } else {
                    System.out.println("Failed to update ship status.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error departing ship: " + e.getMessage());
        }
    }

    public static void shipMenu() {
        while (true) {
            System.out.println("\n=== Ship Manager Menu ===");
            System.out.println("1. Login to Ship");
            System.out.println("2. Dock at Port");
            System.out.println("3. Depart from Port");
            System.out.println("4. Manage Containers (TODO)");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loginToShip();
                    break;
                case "2":
                    dockAtPort();
                    break;
                case "3":
                    departFromPort();
                    break;
                case "4":
                    System.out.println("Container management coming soon.");
                    break;
                case "5":
                    System.out.println("Exiting Ship Manager.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
