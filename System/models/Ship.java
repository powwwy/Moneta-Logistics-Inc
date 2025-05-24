// System/models/Ship.java
package System.models;

public class Ship {
    private int id;
    private int countryId;
    private String number;
    private String name;
    private String arrivalDate;
    private String departureDate;
    private String status;
    private String operatingSegment;

    public Ship(int id, int countryId, String number, String name, String arrivalDate,
                String departureDate, String status, String operatingSegment) {
        this.id = id;
        this.countryId = countryId;
        this.number = number;
        this.name = name;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.status = status;
        this.operatingSegment = operatingSegment;
    }
    // Getters
    public int getId() { return id; }
    public int getCountryId() { return countryId; }
    public String getNumber() { return number; }
    public String getName() { return name; }
    public String getArrivalDate() { return arrivalDate; }
    public String getDepartureDate() { return departureDate; }
    public String getStatus() { return status; }
    public String getOperatingSegment() { return operatingSegment; }

    @Override
    public String toString() {
        return String.format("Ship %s (%s) - Status: %s", number, name, status);
}
}