package com.example.moneta.models;

// Ship.java
public class Ship {

    private String name;
    private String number;
    private int capacity;
    private String status;


    public String getName() {
        return name;
    }

        public String getNumber() {
            return number;
        }

        public int getCapacity() {
            return capacity;
        }

        public String getStatus() {
            return status;
        }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }
}
