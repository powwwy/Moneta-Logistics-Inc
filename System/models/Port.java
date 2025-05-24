// system/models/Port.java
package System.models;

public class Port {
    private int id;
    private int countryId;
    private String portNumber;
    private int maxContainerCapacity;
    private int maxNoOfShips;
    private String status;

    public Port(int id, int countryId, String portNumber, int maxContainerCapacity, int maxNoOfShips, String status) {
        this.id = id;
        this.countryId = countryId;
        this.portNumber = portNumber;
        this.maxContainerCapacity = maxContainerCapacity;
        this.maxNoOfShips = maxNoOfShips;
        this.status = status;
    }
    // Getters
    public int getId() { return id; }
    public int getCountryId() { return countryId; }
    public String getPortNumber() { return portNumber; }
    public int getMaxContainerCapacity() { return maxContainerCapacity; }
    public int getMaxNoOfShips() { return maxNoOfShips; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Port #%s)Country %d) - Status: %s", portNumber, countryId, status);
}
}