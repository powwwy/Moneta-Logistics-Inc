package System.managers;

import System.models.*;
import java.util.Scanner;

public class PortManager extends User{
    private static final Scanner scanner = new Scanner(System.in);

    public PortManager() {
        super();
    }

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n=== Port Authority Menu ===");
            System.out.println("1. View All Ports (Name + Country)");
            System.out.println("2. View Port Details");
            System.out.println("3. View Docked Ships at a Port");
            System.out.println("4. View All Ships");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Port.viewAllPorts();
                    break;
                case "2":
                    Port.viewPortDetails();
                    break;
                case "3":
                    Port.viewShipsAtPort();
                    break;
                case "4":
                    Port.viewAllShips();
                    break;
                case "5":
                    System.out.println("Exiting Port Authority Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
