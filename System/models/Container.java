package System.models;
public class Container {
    private int id;
    private String containerCode;
    private String transitCode;
    private String typeName;
    private String locationType;
    private int locationId;

    public Container(int id, String containerCode, String transitCode, String typeName, String locationType, int locationId) {
        this.id = id;
        this.containerCode = containerCode;
        this.transitCode = transitCode;
        this.typeName = typeName;
        this.locationType = locationType;
        this.locationId = locationId;
    }

    // Getters
    public int getId() { return id; }
    public String getContainerCode() { return containerCode; }
    public String getTransitCode() { return transitCode; }
    public String getTypeName() { return typeName; }
    public String getLocationType() { return locationType; }
    public int getLocationId() { return locationId; }

    @Override
    public String toString() {
        return String.format("Container %s (%s) at %s #%d", containerCode, typeName, locationType, locationId);
    }
}