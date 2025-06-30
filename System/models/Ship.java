package System.models;

import System.handler.DBUtil;
import java.sql.*;
import java.util.Scanner;

// --- Entity class (Single Responsibility: Represents Ship data) ---
class ShipData {
    private String number;
    private String name;
    private String status;

    public ShipData(String number, String name, String status) {
        this.number = number;
        this.name = name;
        this.status = status;
    }

    public String getNumber() { return number; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

// --- Interface Segregation & Dependency Inversion ---
interface ShipRepository {
    ShipData findByNumber(String number);
    boolean updateStatus(String number, String status);
}

interface PortRepository {
    boolean exists(String portNumber);
}

// --- Repositories (Single Responsibility) ---
class JdbcShipRepository implements ShipRepository {
    @Override
    public ShipData findByNumber(String number) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM ships WHERE number = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, number);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new ShipData(
                        rs.getString("number"),
                        rs.getString("name"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding ship: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateStatus(String number, String status) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE ships SET status = ? WHERE number = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setString(2, number);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
            return false;
        }
    }
}

class JdbcPortRepository implements PortRepository {
    @Override
    public boolean exists(String portNumber) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT 1 FROM ports WHERE portNumber = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, portNumber);
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking port: " + e.getMessage());
            return false;
        }
    }
}

// --- Business Logic (Single Responsibility + Open/Closed) ---
class ShipService {
    private final ShipRepository shipRepo;
    private final PortRepository portRepo;
    private ShipData currentShip;

    public ShipService(ShipRepository shipRepo, PortRepository portRepo) {
        this.shipRepo = shipRepo;
        this.portRepo = portRepo;
    }

    public void loginToShip(String shipNumber) {
        currentShip = shipRepo.findByNumber(shipNumber);
        if (currentShip != null) {
            System.out.println("Successfully logged in to ship: " + currentShip.getName());
        } else {
            System.out.println("Ship Number not found.");
        }
    }

    public void dockAtPort(String portNumber) {
        if (!isLoggedIn()) return;

        if (!portRepo.exists(portNumber)) {
            System.out.println("Port Number not found.");
            return;
        }

        if (shipRepo.updateStatus(currentShip.getNumber(), "Docked")) {
            currentShip.setStatus("Docked");
            System.out.println("Ship docked at port " + portNumber + ".");
        } else {
            System.out.println("Failed to dock the ship.");
        }
    }

    public void departFromPort() {
        if (!isLoggedIn()) return;

        if (!"Docked".equalsIgnoreCase(currentShip.getStatus())) {
            System.out.println("Ship is not currently docked.");
            return;
        }

        if (shipRepo.updateStatus(currentShip.getNumber(), "Departed")) {
            currentShip.setStatus("Departed");
            System.out.println("Ship has departed.");
        } else {
            System.out.println("Failed to update ship status.");
        }
    }

    private boolean isLoggedIn() {
        if (currentShip == null) {
            System.out.println("You need to login to a ship first.");
            return false;
        }
        return true;
    }
}

// --- User Interface (Handles user input only) ---
public class Ship {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipService shipService =
        new ShipService(new JdbcShipRepository(), new JdbcPortRepository());

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
                    System.out.print("Enter your Ship Number: ");
                    shipService.loginToShip(scanner.nextLine());
                    break;
                case "2":
                    System.out.print("Enter Port Number to dock at: ");
                    shipService.dockAtPort(scanner.nextLine());
                    break;
                case "3":
                    shipService.departFromPort();
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

    public static void main(String[] args) {
        shipMenu();
    }
}
