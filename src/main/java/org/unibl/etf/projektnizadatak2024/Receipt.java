package org.unibl.etf.projektnizadatak2024;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Receipt {
    private String vehicleID;
    private LocalDateTime dateTime;
    private String rentalID;
    private int receiptID;
    private  boolean isInnerCityRide;
    private int distance;
    private double durationDrive;
    private boolean hadBreakages;
    private String breakages;
    private double discount;
    private double promotion;
    private double basePrice;
    private double amount;
    private double totalPrice;
    private String vehicleType;

    public Receipt(String rentalID, int receiptID, int distance, double durationDrive, boolean hadBreakages, String breakages, double discount, double promotion, double basePrice, double amount, double totalPrice, boolean isInnerCityRide, LocalDateTime localDateTime, String vehicleType, String vehicleID) {
        this.rentalID = rentalID;
        this.receiptID = receiptID;
        this.distance = distance;
        this.durationDrive = durationDrive;
        this.hadBreakages = hadBreakages;
        this.breakages = breakages;
        this.discount = discount;
        this.promotion = promotion;
        this.basePrice = basePrice;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.isInnerCityRide = isInnerCityRide;
        this.dateTime = localDateTime;
        this.vehicleType = vehicleType;
        this.vehicleID = vehicleID;
    }

    @Override
    public String toString() {
        return "========================" + "\nRECIEPT ID: " + receiptID + "\nRENTAL ID: " + rentalID + "\nDISTANCE: " + distance + "\nDRIVE DURATION: " + durationDrive +
                "\nBREAKAGES: " + breakages + "\nDISCOUNT: " + discount + "\nPROMOTION: " + promotion + "\nBASE PRICE (UNIT_PRICE * DRIVE DURATION): " + basePrice +
                "\nAMOUNT (BASE PRICE * DISTANCE): " + amount + "\n----------------------" + "\nTOTAL PRICE (AMOUNT - DISCOUNT - PROMOTION): " + totalPrice + "\nIS INNER CITY RIDE: " + isInnerCityRide + "\nRENTAL DATE & TIME: " + dateTime + "\nVEHICLE TYPE: " + vehicleType + "\nVEHICLE ID: " + vehicleID + "\n========================";
    }

    synchronized void saveRecieptToFile() {
        synchronized (this) {
            String directory = ConfigLoader.getProperty("RECEIPTS_PATH");
            String filePath = directory + "\\receipt_" + receiptID + ".txt";
            File directoryFile = new File(filePath);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryFile))) {
                writer.write(this.toString());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Unable to save receipt " + receiptID + " to file.");
            }
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isHadBreakages() {
        return hadBreakages;
    }

    public int getReceiptID() {
        return receiptID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public boolean isInnerCityRide() {
        return isInnerCityRide;
    }

    public String getBreakages() { return breakages;}

    public void setBreakages(String breakages) { this.breakages = breakages;}
}
