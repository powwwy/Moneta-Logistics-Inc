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
        System.out.println("1. View Ships");
        System.out.println("2. View Ports");
        System.out.println("3. View Account Balance");
        System.out.println("4. Update Ship");
        System.out.println("5. Update Port");
        System.out.println("6. Update Balance");
        System.out.println("7. Logout");
        System.out.print("Choose an option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> Country.viewShips();
            case "2" -> Country.viewPorts();
            case "3" -> Country.viewAccountBalance();
            case "4" -> Country.updateShip();
            case "5" -> Country.updatePort();
            case "6" -> Country.updateBalance();
            case "7" -> Country.logout();
            default -> System.out.println("Invalid option, try again.");
        }
    }
}

