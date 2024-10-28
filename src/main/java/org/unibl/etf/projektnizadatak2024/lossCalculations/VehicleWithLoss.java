package org.unibl.etf.projektnizadatak2024.lossCalculations;

import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;

import java.io.Serializable;

public class VehicleWithLoss implements Serializable {
    private Vehicle vehicle;
    private double lossAmount;

    public VehicleWithLoss(Vehicle vehicle, double lossAmount) {
        this.vehicle = vehicle;
        this.lossAmount = lossAmount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getLossAmount() {
        return Math.round(lossAmount*100.0)/100.0;
    }

    @Override
    public String toString() {
        return vehicle + "\nIznos gubitka: " + lossAmount;
    }
}
