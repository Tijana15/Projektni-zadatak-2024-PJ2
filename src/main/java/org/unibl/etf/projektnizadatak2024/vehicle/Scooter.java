package org.unibl.etf.projektnizadatak2024.vehicle;

public class Scooter extends Vehicle {
    private double maxSpeed;

    public Scooter(String id, double purchasePrice, String manufacturer, String model, int batteryPercentage, double maxSpeed) {
        super(id, purchasePrice, manufacturer, model, batteryPercentage);
        this.maxSpeed = maxSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

}
