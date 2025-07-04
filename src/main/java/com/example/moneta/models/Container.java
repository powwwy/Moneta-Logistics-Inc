package com.example.moneta.models;

public class Container {
    private int containerID;
    private double weight;
    private String description;

    // Constructor:
    public Container(int containerID, String description, double weight) {
        this.containerID = containerID;
        this.description = description;
        this.weight = weight;
    }

    // Getters
    public int getContainerID() { return containerID; }
    public String getDescription() { return description; }
    public double getWeight() { return weight; }
}




