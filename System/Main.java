// system/Main.java
package System;

import System.managers.*;
import System.models.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
         List<Container> container = ContainerManager.loadContainers();
        System.out.println("\nContainers:");
        for (Container k : container) {
            System.out.println(k);
        }
        List<Trade> trade = TradeManager.loadTrades();
        System.out.println("\nTrades:");
        for (Trade t: trade) {
            System.out.println(t);
        }
        System.out.println("\nShip Container Assignments:");
        Map<Integer, List<Integer>> shipAssignments = ContainerAssignmentManager.loadShipAssignments();
        for (var entry : shipAssignments.entrySet()) {
            System.out.println("Ship " + entry.getKey() + " → Containers " + entry.getValue());
        }
        System.out.println("\nPort Container Assignments:");
        Map<Integer, List<Integer>> portAssignments = ContainerAssignmentManager.loadPortAssignments();
        for (var entry : portAssignments.entrySet()) {
            System.out.println("Port " + entry.getKey() + " → Containers " + entry.getValue());
        }
        List<Employee> Employee = EmployeeManager.loadEmployees();
        System.out.println("\nEmployees:");
        for (Employee p: Employee) {
            System.out.println(p); }
}
}
