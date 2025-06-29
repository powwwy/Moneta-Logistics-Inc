package System.models;

public class Container {
    private int containerID;
    private String description;
    private double weight;
    private String status;
    private String shipNumber;

    // Constructor
    public Container(int containerID, String description, double weight, String status, String shipNumber) {
        this.containerID = containerID;
        this.description = description;
        this.weight = weight;
        this.status = status;
        this.shipNumber = shipNumber;
    }

    // Getters and Setters
    public int getContainerID() {
        return containerID;
    }

    public void setContainerID(int containerID) {
        this.containerID = containerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShipNumber() {
        return shipNumber;
    }

    public void setShipNumber(String shipNumber) {
        this.shipNumber = shipNumber;
    }

    // Optional: display container info
    @Override
    public String toString() {
        return "Container{" +
                "ID=" + containerID +
                ", Description='" + description + '\'' +
                ", Weight=" + weight +
                ", Status='" + status + '\'' +
                ", Ship Number='" + shipNumber + '\'' +
                '}';
    }
}
