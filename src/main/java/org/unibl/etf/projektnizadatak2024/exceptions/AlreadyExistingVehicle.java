package org.unibl.etf.projektnizadatak2024.exceptions;

public class AlreadyExistingVehicle extends Exception {
    public AlreadyExistingVehicle() {
        super("Vehicle with same id already exists");
    }

    public AlreadyExistingVehicle(String message) {
        super(message);
    }

}
