package com.traffic.model;

import jakarta.persistence.*;

@Entity
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleId;
    private double speed;
    private String zone;
    private int fine;

    public Violation() {}

    public Violation(String vehicleId, double speed, String zone, int fine) {
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.zone = zone;
        this.fine = fine;
    }

    // getters + setters
    public Long getId() { return id; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public int getFine() { return fine; }
    public void setFine(int fine) { this.fine = fine; }
}