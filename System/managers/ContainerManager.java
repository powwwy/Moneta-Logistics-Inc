package System.managers;

import System.models.Container;
import java.util.Scanner;

public class ContainerManager {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        boolean running = true;

        while (running) {
            String choice = getUserChoice();

            switch (choice) {
                case "1":
                    Container.loginToShip();
                    break;
                case "2":
                    Container.addContainer();
                    break;
                case "3":
                    Container.viewContainers();
                    break;
                case "4":
                    Container.unloadContainer();
                    break;
                case "5":
                    System.out.println("Exiting Container Manager.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private String getUserChoice() {
        System.out.println("\n=== Container Manager Menu ===");
        System.out.println("1. Login to Ship");
        System.out.println("2. Add New Container");
        System.out.println("3. View All Containers on Ship");
        System.out.println("4. Unload a Container");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
        return scanner.nextLine();
    }
}
