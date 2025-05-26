package System.models;
import System.handler.DBUtil;
import java.sql.*;
import java.util.Scanner;

public class Port{
    private static final Scanner scanner = new Scanner(System.in);

    public static void viewAllPorts() {
        try {
            Connection conn = DBUtil.getConnection();

            String query = "SELECT p.portName, c.country_name AS location " +
                    "FROM ports p JOIN country c ON p.countryID = c.countryID";

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                System.out.println("=== All Ports (Name + Country) ===");
                while (rs.next()) {
                    System.out.printf("Port Name: %s | Country: %s\n",
                            rs.getString("portName"),
                            rs.getString("location"));
                }

            } catch (SQLException e) {
                System.out.println("Error retrieving port list: " + e.getMessage());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewPortDetails() {
        System.out.print("Enter Port Number: ");
        String portNumber = scanner.nextLine();

        try {
            Connection conn = DBUtil.getConnection();

            String query = "SELECT p.portName,p.status,p.maxShips,p.maxContainerCapacity, c.country_name AS location " +
                    "FROM ports p JOIN country c ON p.countryID = c.countryID WHERE p.portNumber = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, portNumber);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("=== Port Details ===");
                    System.out.printf("Name: %s | Location: %s| Maximum Capacity: %s containers| Maximum Ships: %s ships | Status: %s\n",
                            rs.getString("portName"),
                            rs.getString("location"),
                            rs.getString("maxContainerCapacity"),
                            rs.getString("maxShips"),
                            rs.getString("status")
                    );

                } else {
                    System.out.println("Port not found.");
                }

            } catch (SQLException e) {
                System.out.println("Error retrieving port details: " + e.getMessage());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewAllShips() {
        try {
            Connection conn = DBUtil.getConnection();

            String query = "SELECT s.number AS shipNumber, s.name AS shipName, " +
                    "p.portName AS portName, s.status " +
                    "FROM ships s " +
                    "LEFT JOIN ports p ON p.portNumber = p.portName ";

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                System.out.println("=== All Ships and Their Current Location ===");
                while (rs.next()) {
                    String location = rs.getString("status").equalsIgnoreCase("At Sea")
                            ? "At Sea"
                            : rs.getString("portName");
                    System.out.printf("Ship Number: %s | Ship Name: %s | Status: %s\n",
                            rs.getString("shipNumber"),
                            rs.getString("shipName"),
                            rs.getString("status"));
                }

            } catch (SQLException e) {
                System.out.println("Error retrieving ship list: " + e.getMessage());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void viewShipsAtPort() {
        System.out.print("Enter Port Number to view docked ships: ");
        String portNumber = scanner.nextLine();

        try {
            Connection conn = DBUtil.getConnection();

            String query = "SELECT s.number, s.name, s.arrivalDate, s.departureDate, s.capacity, s.status " +
                    "FROM ships s " +
                    "JOIN ports p ON s.countryID = p.countryID " + // assuming ship has countryID to match port's
                    "WHERE p.portNumber = ? AND s.status = 'Docked'";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, portNumber);
                ResultSet rs = stmt.executeQuery();

                System.out.println("=== Docked Ships at Port ===");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("Ship Number: %s | Name: %s | Arrival: %s | Departure: %s | Capacity: %s | Status: %s\n",
                            rs.getString("number"),
                            rs.getString("name"),
                            rs.getString("arrivalDate"),
                            rs.getString("departureDate"),
                            rs.getString("capacity"),
                            rs.getString("status"));
                }

                if (!found) {
                    System.out.println("No docked ships at this port.");
                }

            } catch (SQLException e) {
                System.out.println("Error retrieving docked ships: " + e.getMessage());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}