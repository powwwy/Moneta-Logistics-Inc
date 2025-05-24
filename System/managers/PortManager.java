// system/managers/PortManager.java
package System.managers;

import System.models.Port;

import java.io.*;
import java.util.*;

public class PortManager {
    private static final String FILE_PATH = "data/ports.txt";

    public static List<Port> loadPorts() {
        List<Port> ports = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0]);
                    int countryId = Integer.parseInt(parts[1]);
                    String portNumber = parts[2];
                    int maxContainer = Integer.parseInt(parts[3]);
                    int maxShips = Integer.parseInt(parts[4]);
                    String status = parts[5];

                    Port port = new Port(id, countryId, portNumber, maxContainer, maxShips, status);
                    ports.add(port);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading ports: " + e.getMessage());
        }
        return ports;
}
}

