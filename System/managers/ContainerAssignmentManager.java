// System/managers/ContainerAssignmentManager.java
package System.managers;

import java.io.*;
import java.util.*;

public class ContainerAssignmentManager {
    private static final String SHIP_FILE = "data/ship_containers.txt";
    private static final String PORT_FILE = "data/port_containers.txt";

    public static Map<Integer, List<Integer>> loadShipAssignments() {
        return loadAssignments(SHIP_FILE);
    }

    public static Map<Integer, List<Integer>> loadPortAssignments() {
        return loadAssignments(PORT_FILE);
    }

    private static Map<Integer, List<Integer>> loadAssignments(String filePath) {
        Map<Integer, List<Integer>> assignments = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    int keyId = Integer.parseInt(parts[0]);
                    int containerId = Integer.parseInt(parts[1]);

                    assignments.putIfAbsent(keyId, new ArrayList<>());
                    assignments.get(keyId).add(containerId);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading container assignments: " + e.getMessage());
        }
        return assignments;
    }
}
