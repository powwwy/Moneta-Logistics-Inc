//system/managers/country manage
package System.managers;

import System.models.*;
import java.util.Scanner;

public class CountryManager extends User{
    public CountryManager() {
        super();
    }
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Logged in as " + Country.currentCountryName + " ===");
        System.out.println("1. Initiate Trade");
        System.out.println("2. View Ships");
        System.out.println("3. View Ports");
        System.out.println("4. View Account Balance");
        System.out.println("5. Update Ship");
        System.out.println("6. Update Port");
        System.out.println("7. Update Balance");
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "2" -> Country.viewShips();
            case "3" -> Country.viewPorts();
            case "4" -> Country.viewAccountBalance();
            case "5" -> Country.updateShip();
            case "6" -> Country.updatePort();
            case "7" -> Country.updateBalance();
            case "8" -> Country.logout();
            default -> System.out.println("Invalid option, try again.");
        }
    }
}

