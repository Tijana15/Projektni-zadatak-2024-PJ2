package org.unibl.etf.projektnizadatak2024.vehicle;

public class Bike extends Vehicle {
    private double range;

    public Bike(String id, double cijenaNabavke, String manufacturer, String model, int batteryPercentage, double range) {
        super(id, cijenaNabavke, manufacturer, model, batteryPercentage);
        this.range = range;
    }

    public double getRange() {
        return range;
    }
}
