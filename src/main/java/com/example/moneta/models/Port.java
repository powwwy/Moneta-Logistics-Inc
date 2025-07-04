package com.example.moneta.models;
import javafx.beans.property.SimpleStringProperty;

// Port.java
public class Port {
    private String name;
    private String number;
    private int capacity;
    private int maxShips;
    private int maxContainers;

    public String getName() { return name; }
    public String getNumber() { return number; }
    public int getCapacity() { return capacity; }
    public int getMaxShips() { return maxShips; }
    public int getMaxContainers() { return maxContainers; }

    public void setName(String name) { this.name = name; }
    public void setNumber(String number) { this.number = number; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setMaxShips(int maxShips) { this.maxShips = maxShips; }
    public void setMaxContainers(int maxContainers) { this.maxContainers = maxContainers; }
}
