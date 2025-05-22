package System.managers;

import System.models.Container;
import java.io.*;
import java.util.*;

public class ContainerManager {
    private static final String FILE_PATH = "data/containers.txt";

    public static List<Container> loadContainers() {
        List<Container> containers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0]);
                    String containerCode = parts[1];
                    String transitCode = parts[2];
                    String typeName = parts[3];
                    String locationType = parts[4];
                    int locationId = Integer.parseInt(parts[5]);

                    Container container = new Container(id, containerCode, transitCode, typeName, locationType, locationId);
                    containers.add(container);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading containers: " + e.getMessage());
        }
        return containers;
    }
}
