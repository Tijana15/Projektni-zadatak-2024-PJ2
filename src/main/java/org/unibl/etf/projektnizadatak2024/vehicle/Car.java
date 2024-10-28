package org.unibl.etf.projektnizadatak2024.vehicle;

import org.unibl.etf.projektnizadatak2024.interfaces.MultiPassenger;

import java.util.Date;

public class Car extends Vehicle implements MultiPassenger {
    private Date purchaseDate;
    private String description;

    public Car(String id, double cijenaNabavke, String manufacturer, String model, int batteryPercentage, Date purchaseDate, String description) {
        super(id, cijenaNabavke, manufacturer, model, batteryPercentage);
        this.purchaseDate = purchaseDate;
        this.description = description;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public String getDescription() {
        return description;
    }
}
