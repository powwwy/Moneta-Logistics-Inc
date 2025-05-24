// System/models/Employee.java
package System.models;

public class Employee {
    private int id;
    private String ssn;
    private String fname;
    private String lname;
    private String role;
    private String status;
    private int portId;

    public Employee(int id, String ssn, String fname, String lname, String role, String status, int portId) {
        this.id = id;
        this.ssn = ssn;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.status = status;
        this.portId = portId;
    }

 // Getters
 public int getId() { return id; }
 public String getSsn() { return ssn; }
 public String getFname() { return fname; }
 public String getLname() { return lname; }
 public String getRole() { return role; }
 public String getStatus() { return status; }
 public int getPortId() { return portId; }

 @Override
 public String toString() {
     return String.format("%s %s (%s) - %s at Port #%d", fname, lname, ssn, role, portId);
}
}