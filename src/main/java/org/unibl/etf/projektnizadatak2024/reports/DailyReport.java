package org.unibl.etf.projektnizadatak2024.reports;

import org.unibl.etf.projektnizadatak2024.Receipt;
import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyReport {

    public static Map<LocalDate, DailySummary> generateDailyReports(List<Receipt> receipts, List<Vehicle> vehicles) {
        Map<LocalDate, DailySummary> dailyReports = new HashMap<>();

        for (Receipt receipt : receipts) {
            LocalDate date = receipt.getDateTime().toLocalDate();
            DailySummary dailySummary = dailyReports.getOrDefault(date, new DailySummary(date));

            dailySummary.totalIncome += receipt.getTotalPrice();
            dailySummary.totalDiscount += receipt.getDiscount();
            dailySummary.totalPromotions += receipt.getPromotion();

            if (ReportUtil.isInnerCityRide(receipt)) {
                dailySummary.totalInnerCityRides += receipt.getTotalPrice();
            } else {
                dailySummary.totalOuterCityRides += receipt.getTotalPrice();
            }

            if (receipt.isHadBreakages()) {
                Vehicle vehicle = ReportUtil.findVehicleById(receipt.getVehicleID(), receipts, vehicles);
                if (vehicle != null) {
                    double repairCostFactor = ReportUtil.getRepairCostFactor(vehicle.getId());
                    dailySummary.totalRepairCost += repairCostFactor * vehicle.getPurchasePrice();
                }
            }
            dailySummary.totalMaintenenceCost = dailySummary.totalIncome * 0.2;
            dailyReports.put(date, dailySummary);
        }
        return dailyReports;
    }

    public static void printDailyReports(Map<LocalDate, DailySummary> dailyReports) {
        for (Map.Entry<LocalDate, DailySummary> entry : dailyReports.entrySet()) {
            LocalDate date = entry.getKey();
            DailySummary report = entry.getValue();
            System.out.println("-----------------------------------------");
            System.out.println("Report for date: " + date);
            System.out.println("Total income: " + report.getTotalIncome());
            System.out.println("Total discount: " + report.getTotalDiscount());
            System.out.println("Total promotions: " + report.getTotalPromotions());
            System.out.println("Total amount for inner city rides: " + report.getTotalInnerCityRides());
            System.out.println("Total amount for outer city rides: " + report.getTotalOuterCityRides());
            System.out.println("Total maintenance cost: " + report.getTotalMaintenenceCost());
            System.out.println("Total repair cost: " + report.getTotalRepairCost());
            System.out.println("-----------------------------------------");
        }
    }

    public static class DailySummary {
        LocalDate date;
        double totalIncome = 0;
        double totalDiscount = 0;
        double totalPromotions = 0;
        double totalInnerCityRides = 0;
        double totalOuterCityRides = 0;
        double totalRepairCost = 0;
        double totalMaintenenceCost = 0;

        public DailySummary(LocalDate date) {
            this.date = date;
        }

        public LocalDate getDate() {
            return date;
        }

        public double getTotalIncome() {
            return Math.round(totalIncome * 100.0) / 100.0;
        }

        public double getTotalDiscount() {
            return Math.round(totalDiscount * 100.0) / 100.0;
        }

        public double getTotalPromotions() {
            return Math.round(totalPromotions * 100.0) / 100.0;
        }

        public double getTotalInnerCityRides() {
            return Math.round(totalInnerCityRides * 100.0) / 100.0;
        }

        public double getTotalOuterCityRides() {
            return Math.round(totalOuterCityRides * 100.0) / 100.0;
        }

        public double getTotalRepairCost() {
            return Math.round(totalRepairCost * 100.0) / 100.0;
        }

        public double getTotalMaintenenceCost() {
            return Math.round(totalMaintenenceCost * 100.0) / 100.0;
        }
    }

}
