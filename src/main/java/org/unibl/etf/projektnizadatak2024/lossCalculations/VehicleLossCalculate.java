package org.unibl.etf.projektnizadatak2024.lossCalculations;

import org.unibl.etf.projektnizadatak2024.Receipt;
import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;

import java.io.*;
import java.util.List;

import static org.unibl.etf.projektnizadatak2024.ePJ2Company.vehicles;

public class VehicleLossCalculate {

    public static VehicleWithLoss findVehicleWithMaxLoss(List<Receipt> receipts, String vehicleType) {
        VehicleWithLoss maxLossVehicle = null;
        double maxLoss = Double.MIN_VALUE;

        for (Receipt receipt : receipts) {
            if (receipt.getVehicleType().equals(vehicleType) && receipt.isHadBreakages()) {
                double repairCost = calculateRepairCost(receipt);
                if (repairCost > maxLoss) {
                    maxLoss = repairCost;
                    maxLossVehicle = new VehicleWithLoss(findVehicleById(receipt.getVehicleID()), repairCost);
                }
            }
        }
        return maxLossVehicle;
    }


    public static void serializeVehicleWithLoss(VehicleWithLoss vehicleWithLoss, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(vehicleWithLoss);
            System.out.println("Vehicle with max loss " + vehicleWithLoss.getVehicle().getId() + " successfully serialized in " + fileName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static VehicleWithLoss deserializeVehicleWithLoss(String fileName){
        VehicleWithLoss vehicleWithLoss = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            vehicleWithLoss = (VehicleWithLoss) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserializing vehicle with max loss failed.");
        }
        return vehicleWithLoss;
    }


    private static Vehicle findVehicleById(String vehicleId) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                return vehicle;
            }
        }
        return null;
    }

    static private double calculateRepairCost(Receipt receipt) {
        double repairCoefficient = switch (receipt.getVehicleType()) {
            case "automobil" -> 0.07;
            case "bicikl" -> 0.04;
            case "trotinet" -> 0.02;
            default -> 0.0;
        };
        for (Vehicle vehicle : vehicles) {
            if (receipt.getVehicleID().equals(vehicle.getId())) {
                return repairCoefficient * vehicle.getPurchasePrice();
            }
        }
        return 0;
    }
}
