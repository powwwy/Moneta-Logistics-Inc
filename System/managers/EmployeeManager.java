// System/managers/EmployeeManager.java
package System.managers;


import System.models.Employee;

import java.io.*;
import java.util.*;

public class EmployeeManager {
    private static final String FILE_PATH = "data/employees.txt";

    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 7) {
                    int id = Integer.parseInt(parts[0]);
                    String ssn = parts[1];
                    String fname = parts[2];
                    String lname = parts[3];
                    String role = parts[4];
                    String status = parts[5];
                    int portId = Integer.parseInt(parts[6]);
                    Employee emp = new Employee(id, ssn, fname, lname, role, status, portId);
                    employees.add(emp);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading employees: " + e.getMessage());
        }
        return employees;
    }
}