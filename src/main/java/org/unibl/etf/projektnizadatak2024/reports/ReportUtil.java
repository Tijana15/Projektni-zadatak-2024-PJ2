package org.unibl.etf.projektnizadatak2024.reports;

import org.unibl.etf.projektnizadatak2024.Receipt;
import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;

import java.util.List;
import java.util.Map;

public class ReportUtil {

    public static double calculateTotalIncome(List<Receipt> receipts) {
        return receipts.stream().mapToDouble(Receipt::getTotalPrice).sum();
    }

    public static double calculateTotalDiscount(List<Receipt> receipts) {
        return receipts.stream().mapToDouble(Receipt::getDiscount).sum();
    }

    public static double calculateTotalPromotions(List<Receipt> receipts) {
        return receipts.stream().mapToDouble(Receipt::getPromotion).sum();
    }

    public static double calculateTotalInnerCityRides(List<Receipt> receipts) {
        return receipts.stream()
                .filter(ReportUtil::isInnerCityRide)
                .mapToDouble(Receipt::getTotalPrice)
                .sum();
    }

    public static double calculateTotalOuterCityRides(List<Receipt> receipts) {
        double totalIncome = calculateTotalIncome(receipts);
        return totalIncome - calculateTotalInnerCityRides(receipts);
    }

    public static double calculateTotalRepairCost(List<Receipt> receipts, List<Vehicle> vehicles) {
        double totalRepairCost = 0;
        for (Receipt receipt : receipts) {
            if (receipt.isHadBreakages()) {
                Vehicle vehicle = findVehicleById(receipt.getVehicleID(), receipts, vehicles);
                if (vehicle != null) {
                    double repairCostFactor = getRepairCostFactor(vehicle.getId());
                    totalRepairCost += repairCostFactor * vehicle.getPurchasePrice();
                }
            }
        }
        return totalRepairCost;
    }

    public static Vehicle findVehicleById(String vehicleID, List<Receipt> receipts, List<Vehicle> vehicles) {
        for (Receipt receipt : receipts) {
            if (receipt.getVehicleID().equals(vehicleID)) {
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.getId().equals(vehicleID)) {
                        return vehicle;
                    }
                }
            }
        }
        return null;
    }

    static double getRepairCostFactor(String vehicleID) {
        if (vehicleID.startsWith("A")) return 0.07;
        if (vehicleID.startsWith("B")) return 0.04;
        if (vehicleID.startsWith("T")) return 0.02;
        return 0;
    }

    static boolean isInnerCityRide(Receipt receipt) {
        return receipt.isInnerCityRide();
    }
}
