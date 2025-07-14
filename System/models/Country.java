package System.models;

import System.handler.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static System.models.Container.scanner;

public class Country {
    private static int currentCountryID = -1;
    public static String currentCountryName = "";

    public Country(int countryID, String countryName, String contactNumber, double accountBalance, int managerID, String managerName) {}

    public static void registerNewCountry(Scanner scanner) {
        try (Connection conn = DBUtil.getConnection()) {
            System.out.print("Enter country name: ");
            String name = scanner.nextLine();

            System.out.print("Enter contact number: ");
            String contactNumber = scanner.nextLine();

            double accountBalance = 0.0;

            Random rand = new Random();
            String managerID = "CMGR-" + (10000 + rand.nextInt(90000));
            System.out.println("Generated Manager ID: " + managerID);

            System.out.print("Enter manager's name: ");
            String managerName = scanner.nextLine();

            String sql = "INSERT INTO Country (country_name, contactNumber, accountBalance, managerID, managerName) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setString(2, contactNumber);
                pstmt.setDouble(3, accountBalance);
                pstmt.setString(4, managerID);
                pstmt.setString(5, managerName);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        currentCountryID = rs.getInt(1);
                        currentCountryName = name;
                        System.out.println("Successfully registered. Your manager ID is: " + managerID);
                    }
                } else {
                    System.out.println("Registration failed.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
        }
    }

    public static void loginExistingManager(Scanner scanner) {
        try (Connection conn = DBUtil.getConnection()) {
            System.out.print("Enter your manager ID: ");
            String managerID = scanner.nextLine();

            String sql = "SELECT countryID, country_name FROM Country WHERE managerID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, managerID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    currentCountryID = rs.getInt("countryID");
                    currentCountryName = rs.getString("country_name");
                    System.out.println("Login successful. Welcome, " + currentCountryName + " Manager!");
                } else {
                    System.out.println("Manager ID not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
    }

    public static void viewShips() {
        if (!isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }

        String sql = "SELECT * FROM Ships WHERE countryID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, currentCountryID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n=== Ships for " + currentCountryName + " ===");
            while (rs.next()) {
                System.out.printf("Ship ID: %d | Name: %s | Number: %s | Status: %s | Arrival: %s | Departure: %s | Capacity: %d%n",
                        rs.getInt("shipID"),
                        rs.getString("name"),
                        rs.getString("number"),
                        rs.getString("status"),
                        rs.getString("arrivalDate"),
                        rs.getString("departureDate"),
                        rs.getInt("capacity"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching ships: " + e.getMessage());
        }
    }

    public static void viewPorts() {
        if (!isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM Ports WHERE countryID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, currentCountryID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nPorts for Country ID " + currentCountryID + ":");
            while (rs.next()) {
                System.out.println("Port ID: " + rs.getInt("portID") +
                        ", Name: " + rs.getString("portName") +
                        ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching ports: " + e.getMessage());
        }
    }

    public static void viewAccountBalance() {
        if (!isLoggedIn()) {
            System.out.println("Please login first.");
            return;
        }
        String sql = "SELECT accountBalance FROM Country WHERE countryID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, currentCountryID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("accountBalance");
                System.out.printf("Account Balance for %s: $%.2f%n", currentCountryName, balance);
            } else {
                System.out.println("Account info not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving balance: " + e.getMessage());
        }
    }

    public static void updateShip() {
        System.out.print("Enter Ship ID to update: ");
        String shipID = scanner.nextLine();

        try (Connection conn = DBUtil.getConnection()) {
            String selectQuery = "SELECT * FROM ships WHERE shipID = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, shipID);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String currentName = rs.getString("name");
                System.out.println("Current name: " + currentName);
                System.out.print("Update name? (y/n): ");
                String updateName = scanner.nextLine();
                String newName = currentName;
                if (updateName.equalsIgnoreCase("y")) {
                    System.out.print("Enter new name: ");
                    newName = scanner.nextLine();
                }

                System.out.print("Enter new arrival date (YYYY-MM-DD): ");
                String arrivalDate = scanner.nextLine();
                System.out.print("Enter new departure date (YYYY-MM-DD): ");
                String departureDate = scanner.nextLine();
                System.out.print("Enter new capacity: ");
                int capacity = Integer.parseInt(scanner.nextLine());

                String status = LocalDate.now().isBefore(LocalDate.parse(arrivalDate)) ? "Incoming" :
                        LocalDate.now().isAfter(LocalDate.parse(departureDate)) ? "Departed" : "Docked";

                String updateQuery = "UPDATE ships SET name=?, arrivalDate=?, departureDate=?, capacity=?, status=? WHERE shipID=?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, newName);
                updateStmt.setString(2, arrivalDate);
                updateStmt.setString(3, departureDate);
                updateStmt.setInt(4, capacity);
                updateStmt.setString(5, status);
                updateStmt.setString(6, shipID);
                updateStmt.executeUpdate();

                System.out.println("Ship updated.");
            } else {
                System.out.println("Ship not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePort() {
        System.out.print("Enter Port Number to update: ");
        String portNumber = scanner.nextLine();

        try (Connection conn = DBUtil.getConnection()) {
            String selectQuery = "SELECT * FROM ports WHERE portNumber = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, portNumber);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String currentName = rs.getString("portName");
                System.out.println("Current name: " + currentName);
                System.out.print("Update name? (y/n): ");
                String updateName = scanner.nextLine();
                String newName = currentName;
                if (updateName.equalsIgnoreCase("y")) {
                    System.out.print("Enter new name: ");
                    newName = scanner.nextLine();
                }

                String updateQuery = "UPDATE ports SET portName=? WHERE portNumber=?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, newName);
                updateStmt.setString(2, portNumber);
                updateStmt.executeUpdate();

                System.out.println("Port updated.");
            } else {
                System.out.println("Port not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBalance() {
        System.out.print("Enter Manager ID: ");
        String managerID = scanner.nextLine();

        try (Connection conn = DBUtil.getConnection()) {
            String selectQuery = "SELECT * FROM Country WHERE managerID = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, managerID);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("accountBalance");
                System.out.println("Current balance: " + currentBalance);
                System.out.print("Enter new balance: ");
                double newBalance = Double.parseDouble(scanner.nextLine());

                String updateQuery = "UPDATE country SET accountBalance = ? WHERE managerID = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setDouble(1, newBalance);
                updateStmt.setString(2, managerID);
                updateStmt.executeUpdate();

                System.out.println("Balance updated.");
            } else {
                System.out.println("Country not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logout() {
        currentCountryID = -1;
        currentCountryName = "";
        System.out.println("Logged out successfully.");
    }

    public static boolean isLoggedIn() {
        return currentCountryID != -1;
    }

    public static int getCurrentCountryID() {
        return currentCountryID;
    }
}
