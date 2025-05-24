// system/managers/CountryManager.java
package System.managers;

import System.models.Country;

import java.io.*;
import java.util.*;

public class CountryManager {
    private static final String FILE_PATH = "data/countries.txt";

    public static List<Country> loadCountries() {
        List<Country> countries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 7) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String contact = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    String manager = parts[4];
                    double importTariff = Double.parseDouble(parts[5]);
                    double exportTariff = Double.parseDouble(parts[6]);

                    Country country = new Country(id, name, contact, balance, manager, importTariff, exportTariff);
                    countries.add(country);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading countries: " + e.getMessage());
        }
        return countries;
    }

}
