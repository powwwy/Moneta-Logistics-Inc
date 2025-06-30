package System.managers;

import java.util.Scanner;

// === Abstracted Ship Operations ===
interface ShipService {
    void execute();
}

class ShipLogin implements ShipService {
    public void execute() {
        System.out.println("[LOGIN] Ship login logic goes here.");
        // Ship.loginToShip(); // Place real logic here
    }
}

class ShipDock implements ShipService {
    public void execute() {
        System.out.println("[DOCK] Ship dock logic goes here.");
        // Ship.dockAtPort(); // Place real logic here
    }
}

class ShipDepart implements ShipService {
    public void execute() {
        System.out.println("[DEPART] Ship depart logic goes here.");
        // Ship.departFromPort(); // Place real logic here
    }
}

// === Base User class (simulating your system) ===
abstract class User {
    public abstract void showMenu();
}

// === ShipManager using SOLID ===
public class ShipManager extends User {
    private static final Scanner scanner = new Scanner(System.in);

    private final ShipService loginService = new ShipLogin();
    private final ShipService dockService = new ShipDock();
    private final ShipService departService = new ShipDepart();

    public ShipManager() {
        super();
    }

    @Override
    public void showMenu() {
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
                    loginService.execute();  // Open/Closed principle
                    break;
                case "2":
                    dockService.execute();
                    break;
                case "3":
                    departService.execute();
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
