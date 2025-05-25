package System.managers;
import System.models.Ship;
import java.util.Scanner;

public class ShipManager extends User{
    private static final Scanner scanner = new Scanner(System.in);

    public ShipManager() {
        super();
    }

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
                    Ship.loginToShip();
                    break;
                case "2":
                    Ship.dockAtPort();
                    break;
                case "3":
                    Ship.departFromPort();
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