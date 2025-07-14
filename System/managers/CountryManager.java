package System.managers;

import System.models.Country;
import java.util.Scanner;

public class CountryManager extends User {
    public CountryManager() {
        super();
    }

    @Override
    public void showMenu() {
        start(); // ✅ Ensure showMenu() runs the correct flow
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Country Management ===");
            System.out.println("1. Register a New Country");
            System.out.println("2. Login as Manager");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> Country.registerNewCountry(scanner);
                case "2" -> {
                    Country.loginExistingManager(scanner);
                    if (Country.isLoggedIn()) {
                        showCountryMenu(scanner);
                    }
                }
                case "3" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showCountryMenu(Scanner scanner) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=== Logged in as " + Country.currentCountryName + " ===");
            System.out.println("1. View Ships");
            System.out.println("2. View Ports");
            System.out.println("3. View Account Balance");
            System.out.println("4. Update Ship");
            System.out.println("5. Update Port");
            System.out.println("6. Update Balance");
            System.out.println("7. Log-out");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> Country.viewShips();
                case "2" -> Country.viewPorts();
                case "3" -> Country.viewAccountBalance();
                case "4" -> Country.updateShip();
                case "5" -> Country.updatePort();
                case "6" -> Country.updateBalance();
                case "7" -> {
                    Country.logout();
                    loggedIn = false;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }
}
