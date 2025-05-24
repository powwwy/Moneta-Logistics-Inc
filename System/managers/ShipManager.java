// System/managers/ShipManager.java
package System.managers;

import System.models.Ship;

import java.io.*;
import java.util.*;

public class ShipManager {
    private static final String FILE_PATH = "data/ships.txt";

    public static List<Ship> loadShips() {
        List<Ship> ships = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 8) {
                    int id = Integer.parseInt(parts[0]);
                    int countryId = Integer.parseInt(parts[1]);
                    String number = parts[2];
                    String name = parts[3];
                    String arrivalDate = parts[4];
                    String departureDate = parts[5];
                    String status = parts[6];
                    String operatingSegment = parts[7];

                    Ship ship = new Ship(id, countryId, number, name, arrivalDate,
                            departureDate, status, operatingSegment);
                    ships.add(ship);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading ships: " + e.getMessage());
        }
        return ships;
}
}