package System.managers;

import java.util.Scanner;

public class UserManager {
    public void selectUserRole() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Select Role ===");
            System.out.println("1. Port Authority");
            System.out.println("2. Ship Operator");
            System.out.println("3. Country Manager");
            System.out.println("4. Container Manager");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            User user = null;

            switch (choice) {
                case "1":
                    user = new PortManager();
                    break;
                case "2":
                    user = new ShipManager();
                    break;
                case "3":
                    user = new CountryManager();
                    break;
                case "4":
                    user = new ContainerManager();
                    break;
                case "5":
                    System.out.println("Exiting system.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
                    continue;
            }

            user.showMenu();
        }
    }
}
