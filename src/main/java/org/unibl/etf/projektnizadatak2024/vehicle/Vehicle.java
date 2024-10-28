package org.unibl.etf.projektnizadatak2024.vehicle;

import org.unibl.etf.projektnizadatak2024.interfaces.Breakable;
import org.unibl.etf.projektnizadatak2024.interfaces.Chargeable;
import org.unibl.etf.projektnizadatak2024.interfaces.Dischargeable;

import java.io.Serializable;
import java.util.Random;

public abstract class Vehicle implements Breakable, Chargeable, Dischargeable, Serializable {
    String id;
    double purchasePrice;
    String manufacturer;
    String model;
    int batteryPercentage;

    public Vehicle() {}

    public Vehicle(String id, double purchasePrice, String manufacturer, String model,int batteryPercentage) {
        this.id = id;
        this.purchasePrice = purchasePrice;
        this.manufacturer = manufacturer;
        this.model = model;
        int perc=0;
        do {
            perc = new Random().nextInt(100) + 1;
        }while (perc<0);
        this.batteryPercentage = perc;


    }

    public String toString() {
        return "\n-------" + "\nVehicle ID: " + id + "\nPurchase price: " + purchasePrice + "\nManufacturer: " + manufacturer + "\nModel: " + model + "\nBattery percentage: " + batteryPercentage + "\n------";
    }

    @Override
    public void charge() {
        if (getBatteryPercentage() < 100) {
            int diff = 100 - getBatteryPercentage();
            setBatteryPercentage(getBatteryPercentage() + new Random().nextInt(diff));
            //System.out.println("Battery recharged to: " + getBatteryPercentage());
        } else {
            System.out.println("Battery is already charged to maximum!");
        }
    }

    @Override
    public void discharge() {
        setBatteryPercentage(getBatteryPercentage() - (new Random().nextInt(4)+1));
        //System.out.println("Battery discharged to: " + getBatteryPercentage());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
