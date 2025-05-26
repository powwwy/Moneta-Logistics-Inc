package System.models;

import System.handler.DBUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static System.models.Container.scanner;

public class Country{
    private static int currentCountryID = -1;
    public static String currentCountryName = "";

    public Country(int countryID, String countryName, String contactNumber, double accountBalance, int managerID, String managerName) {}

    private static void showLoginRegisterMenu(Scanner scanner) {

        System.out.println("\n=== Country Manager Menu ===");
        System.out.println("1. Register New Country");
        System.out.println("2. Login as Existing Manager");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> registerNewCountry(scanner);
            case "2" -> loginExistingManager(scanner);
            case "3" -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid option, try again.");
        }
    }

    private static void registerNewCountry(Scanner scanner) {
        try (Connection conn = DBUtil.getConnection()) {
            System.out.print("Enter country name: ");
            String name = scanner.nextLine();

            System.out.print("Enter country contact number: ");
            String contactNumber = scanner.nextLine();

            double accountBalance = 0.0;

            // Generate a random Manager ID
            Random rand = new Random();
            String managerID = "CMGR-" + (10000 + rand.nextInt(90000));
            System.out.println("Generated Manager ID: " + managerID);

            System.out.print("Enter country manager name: ");
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
                        System.out.println("New country registered. Your manager ID is: " + managerID);
                    }
                } else {
                    System.out.println("Failed to register country.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error registering country: " + e.getMessage());
        }
    }

    private static void loginExistingManager(Scanner scanner) {
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
            System.err.println("Error logging in: " + e.getMessage());
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
                System.out.println("Account information not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving account balance: " + e.getMessage());
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

    public static List<Country> loadCountries() {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT * FROM Country";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Country country = new Country(
                        rs.getInt("countryID"),
                        rs.getString("country_name"),
                        rs.getString("contactNumber"),
                        rs.getDouble("accountBalance"),
                        rs.getInt("managerID"),
                        rs.getString("managerName"));
                countries.add(country);
            }
        } catch (SQLException e) {
            System.err.println("Error loading countries: " + e.getMessage());
        }
        return countries;
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
                System.out.print("Do you want to update the name? (y/n): ");
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

                System.out.println("Ship updated successfully.");
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
                System.out.print("Do you want to update the name? (y/n): ");
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

                System.out.println("Port updated successfully.");
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

                System.out.println("Balance updated successfully.");
            } else {
                System.out.println("Country not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
