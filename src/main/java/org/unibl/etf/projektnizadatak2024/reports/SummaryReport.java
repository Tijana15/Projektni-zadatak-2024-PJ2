package org.unibl.etf.projektnizadatak2024.reports;

import org.unibl.etf.projektnizadatak2024.Receipt;
import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;

import java.util.List;

public class SummaryReport {

    private static double totalIncome;
    private static double totalDiscount;
    private static double totalPromotions;
    private static double totalInnerCityRides;
    private static double totalOuterCityRides;
    private static double totalMaintenanceCost;
    private static double totalRepairCost;
    private static double totalCompanyCosts;
    private static double totalTax;

    public SummaryReport() {}

    public SummaryReport(double totalIncome, double totalDiscount, double totalPromotions, double totalInnerCityRides, double totalOuterCityRides, double totalMaintenanceCost, double totalRepairCost, double totalCompanyCosts, double totalTax) {
        SummaryReport.totalIncome = totalIncome;
        SummaryReport.totalDiscount = totalDiscount;
        SummaryReport.totalPromotions = totalPromotions;
        SummaryReport.totalInnerCityRides = totalInnerCityRides;
        SummaryReport.totalOuterCityRides = totalOuterCityRides;
        SummaryReport.totalMaintenanceCost = totalMaintenanceCost;
        SummaryReport.totalRepairCost = totalRepairCost;
        SummaryReport.totalCompanyCosts = totalCompanyCosts;
        SummaryReport.totalTax = totalTax;
    }

    public static void generateSummaryReport(List<Receipt> receipts, List<Vehicle> vehicles) {
        totalIncome = ReportUtil.calculateTotalIncome(receipts);
        totalDiscount = ReportUtil.calculateTotalDiscount(receipts);
        totalPromotions = ReportUtil.calculateTotalPromotions(receipts);
        totalInnerCityRides = ReportUtil.calculateTotalInnerCityRides(receipts);
        totalOuterCityRides = ReportUtil.calculateTotalOuterCityRides(receipts);
        totalRepairCost = ReportUtil.calculateTotalRepairCost(receipts, vehicles);

        totalMaintenanceCost = totalIncome * 0.2;
        totalCompanyCosts = totalIncome * 0.2;
        totalTax = (totalIncome - totalMaintenanceCost - totalRepairCost - totalCompanyCosts) * 0.1;
    }

    public static SummaryReport getSummaryReport() {
        return new SummaryReport(
                (getTotalIncome() * 100.0) / 100.0,
                (getTotalDiscount() * 100.0) / 100.0,
                (getTotalPromotions() * 100.0) / 100.0,
                (getTotalInnerCityRides() * 100.0) / 100.0,
                (getTotalOuterCityRides() * 100.0) / 100.0,
                (getTotalMaintenanceCost() * 100.0) / 100.0,
                (getTotalRepairCost() * 100.0) / 100.0,
                (getTotalCompanyCosts() * 100.0) / 100.0,
                (getTotalTax() * 100.0) / 100.0
        );
    }

    public static double getTotalIncome() {
        return Math.round(totalIncome * 100.0) / 100.0;
    }

    public static double getTotalDiscount() {
        return Math.round(totalDiscount * 100.0) / 100.0;
    }

    public static double getTotalPromotions() {
        return Math.round(totalPromotions * 100.0) / 100.0;
    }

    public static double getTotalInnerCityRides() {
        return Math.round(totalInnerCityRides * 100.0) / 100.0;
    }


    public static double getTotalOuterCityRides() {
        return Math.round(totalOuterCityRides * 100.0) / 100.0;
    }


    public static double getTotalMaintenanceCost() {
        return Math.round(totalMaintenanceCost * 100.0) / 100.0;
    }


    public static double getTotalRepairCost() {
        return Math.round(totalRepairCost * 100.0) / 100.0;
    }

    public static double getTotalCompanyCosts() {
        return Math.round(totalCompanyCosts * 100.0) / 100.0;
    }


    public static double getTotalTax() {
        return Math.round(totalTax * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "\nSummary Report:" +
                "\nTotal Income: " + getTotalIncome() +
                "\nTotal Discount: " + getTotalDiscount() +
                "\nTotal Promotions: " + getTotalPromotions() +
                "\nTotal Amount for Inner City Rides: " + getTotalInnerCityRides() +
                "\nTotal Amount for Outer City Rides: " + getTotalOuterCityRides() +
                "\nTotal Maintenance Cost: " + getTotalMaintenanceCost() +
                "\nTotal Repair Cost: " + getTotalRepairCost() +
                "\nTotal Company Costs: " + getTotalCompanyCosts() +
                "\nTotal Tax: " + getTotalTax();
    }

}

