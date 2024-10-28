package org.unibl.etf.projektnizadatak2024;

import org.unibl.etf.projektnizadatak2024.reports.TotalPriceDetails;
import org.unibl.etf.projektnizadatak2024.user.User;
import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The Rental class represents the rental of a vehicle. It extends the {@link Thread} class
 * to simulate vehicle movement using threads.
 */

public class Rental extends Thread {

    public static List<Receipt> receiptList = new ArrayList<>();
    public static final Object lock = new Object();
    static volatile int i = 0;

    private final String rentalID = "REN";
    private LocalDateTime rentalDateTime;
    private String userID;
    private String vehicleId;
    private volatile int xStartCoordinate;
    private volatile int yStartCoordinate;
    private volatile int xEndCoordinate;
    private volatile int yEndCoordinate;
    private double duration;
    private boolean hadBreakage;
    private boolean promotion;
    private boolean isTenth;
    private String vehicleType;

    public Rental(LocalDateTime rentalDateTime, User user, String vehicleId, int xStartCoordinate, int yStartCoordinate, int xEndCoordinate, int yEndCoordinate, double duration, boolean hadBreakage, boolean promotion) {
        this.rentalDateTime = rentalDateTime;
        this.userID = user.getID();
        this.vehicleId = vehicleId;
        this.xStartCoordinate = xStartCoordinate;
        this.yStartCoordinate = yStartCoordinate;
        this.xEndCoordinate = xEndCoordinate;
        this.yEndCoordinate = yEndCoordinate;
        this.duration = duration;
        this.hadBreakage = hadBreakage;
        this.promotion = promotion;
        vehicleType = getVehicleType();
    }

    @Override
    public void run() {
        Vehicle vehicle = Simulation.vehicleMap.get(vehicleId);
        vehicle.charge();
        System.out.println("Rental starting: " + rentalDateTime + " for type: " + getVehicleType());
        double durationOnField = Math.round(duration / (Math.abs(xEndCoordinate - xStartCoordinate) + Math.abs(yEndCoordinate - yStartCoordinate)) * 100.0) / 100.0;
        System.out.println("Duration of waiting on field: " + durationOnField + " for " + vehicleId);

        int xTrenutno = xStartCoordinate;
        int yTrenutno = yStartCoordinate;
        Simulation.showVehicleOnGridPane(this, xTrenutno, yTrenutno);

        int xRazlika = calculateDiff(xStartCoordinate, xEndCoordinate);
        if (xRazlika != 0) {
            do {
                int xOld = xTrenutno;
                if (ifDifferenceIsNegative(xRazlika)) {

                    xTrenutno = xTrenutno - 1;
                    vehicle.discharge();
                    xRazlika++;
                } else {

                    xTrenutno = xTrenutno + 1;
                    vehicle.discharge();
                    xRazlika--;
                }

                try {
                    synchronized (Simulation.gridPane1) {
                        Simulation.moveVehicleOnGridPane(this, xOld, yTrenutno, xTrenutno, yTrenutno);
                    }
                } catch (Exception e) {
                    System.out.println("Prekinuto kod rentala sa vozilom " + vehicleId + " u " + rentalDateTime);
                }
                try {
                    Thread.sleep((long) (durationOnField * 1000));

                } catch (InterruptedException e) {
                    System.out.println("Interrupted in run method of Rental Thread");
                }
            } while (xRazlika != 0);
        }
        int yRazlika = calculateDiff(yStartCoordinate, yEndCoordinate);
        if (yRazlika != 0) {
            do {
                int yOld = yTrenutno;
                if (ifDifferenceIsNegative(yRazlika)) {
                    yTrenutno = yTrenutno - 1;
                    vehicle.discharge();
                    yRazlika++;

                } else {
                    yTrenutno = yTrenutno + 1;
                    vehicle.discharge();
                    yRazlika--;
                }
                synchronized (Simulation.gridPane1) {
                    Simulation.moveVehicleOnGridPane(this, xTrenutno, yOld, xTrenutno, yTrenutno);
                }
                try {
                    Thread.sleep((long) (durationOnField * 1000));

                } catch (InterruptedException e) {
                    System.out.println("Interrupted in run method of Rental Thread");
                }
            } while (yRazlika != 0);
        }

        synchronized (Simulation.gridPane1) {
            Simulation.removeVehicleFromGridPane(xEndCoordinate, yEndCoordinate);
        }
        synchronized (lock) {
            System.out.println("Generating reciept for vehicle: " + vehicleId);
            TotalPriceDetails totalPriceDetails = this.calculateTotalPrice();
            Receipt receipt = new Receipt(rentalID + ++i, //rentalID
                    i, // idRacun
                    calculateDistance(),
                    duration,
                    hadBreakage, (hadBreakage) ? "breakage " + i : "none",
                    totalPriceDetails.getDiscount(),
                    totalPriceDetails.getPromotion(), totalPriceDetails.getBasePrice(), totalPriceDetails.getAmount(), totalPriceDetails.getTotalPrice(), isNarrowCityRide(), rentalDateTime, getVehicleType(), vehicleId);

            receipt.saveRecieptToFile();
            receiptList.add(receipt);
        }

    }

    /**
     * Calculates the difference between two coordinate values.
     *
     * @param a1 the start coordinate
     * @param a2 the end coordinate
     * @return the difference between the start and end coordinates
     */
    private synchronized int calculateDiff(int a1, int a2) {
        return a2 - a1;
    }

    /**
     * Determines whether the difference between two coordinates is negative.
     *
     * @param diff the difference between the coordinates
     * @return true if the difference is negative, false otherwise
     */
    private synchronized boolean ifDifferenceIsNegative(int diff) {
        return diff < 0;
    }

    /**
     * Calculates the total price of the rental, taking into account the vehicle type,
     * distance traveled, discounts, and promotions. Returns an object containing
     * the calculated total price, base price, discount, promotion, and amount.
     *
     * @return a TotalPriceDetails object containing the pricing details
     */
    private synchronized TotalPriceDetails calculateTotalPrice() {
        synchronized (this) {
            double BASE_PRICE = 0;
            double TOTAL = 0;
            double discount = 0;
            double promotion = 0;
            double AMOUNT = 0;
            double DISCOUNT=0;
            double PROMOTION=0;

            if (this.isHadBreakage()) {
                return new TotalPriceDetails(0, discount, promotion, BASE_PRICE, AMOUNT);
            }

            double carUnitPrice = Double.parseDouble(ConfigLoader.getProperty("CAR_UNIT_PRICE"));
            double bikeUnitPrice = Double.parseDouble(ConfigLoader.getProperty("BIKE_UNIT_PRICE"));
            double scooterUnitPrice = Double.parseDouble(ConfigLoader.getProperty("SCOOTER_UNIT_PRICE"));
            discount = Double.parseDouble(ConfigLoader.getProperty("DISCOUNT"));
            promotion = Double.parseDouble(ConfigLoader.getProperty("DISCOUNT_PROM"));
            double distanceNarrow = Double.parseDouble(ConfigLoader.getProperty("DISTANCE_NARROW"));
            double distanceWide = Double.parseDouble(ConfigLoader.getProperty("DISTANCE_WIDE"));

            String vehicleType = getVehicleType();
            BASE_PRICE = switch (vehicleType) {
                case "automobil" -> carUnitPrice * this.getDuration();
                case "bicikl" -> bikeUnitPrice * this.getDuration();
                case "trotinet" -> scooterUnitPrice * this.getDuration();
                default -> BASE_PRICE;
            };

            double DISTANCE = isNarrowCityRide() ? distanceNarrow : distanceWide;
            AMOUNT = BASE_PRICE * DISTANCE;

            if (this.isTenth() && this.isPromotion()) {
                PROMOTION=promotion*AMOUNT;
                DISCOUNT=discount*AMOUNT;
                TOTAL = AMOUNT - DISCOUNT-PROMOTION;
            } else if (this.isTenth() && !this.isPromotion()) {
                DISCOUNT=discount*AMOUNT;
                TOTAL = AMOUNT - DISCOUNT;
            } else if (this.isPromotion() && !this.isTenth()) {
                PROMOTION=promotion*AMOUNT;
                TOTAL = AMOUNT -PROMOTION;
            } else if(!this.isTenth() && !this.isPromotion()){
                TOTAL = AMOUNT;
            }
            return new TotalPriceDetails(TOTAL, DISCOUNT, PROMOTION, BASE_PRICE, AMOUNT);
        }
    }

    /**
     * Determines whether the rental is within the narrow city area.
     * Based on the start and end coordinates of the vehicle.
     *
     * @return true if the rental is within the narrow city area, false otherwise
     */
    private boolean isNarrowCityRide() {
        return (this.getxEndCoordinate() > 4 && this.getyEndCoordinate() > 4 && this.getxEndCoordinate()<15 && this.getyEndCoordinate()<15
        && this.getxStartCoordinate()>4 && this.getyStartCoordinate()>4 && this.getxStartCoordinate()<15 && this.getyStartCoordinate()<15);
    }

    String getVehicleType() {
        //Pretpostavka: svaki id vozila krece sa tipom vozila (A automobil...)
        char typePrefix = vehicleId.charAt(0);
        return switch (typePrefix) {
            case 'A' -> "automobil";
            case 'B' -> "bicikl";
            case 'T' -> "trotinet";
            default -> "nepoznat";
        };
    }

    private int calculateDistance() {
        return (Math.abs(xEndCoordinate - xStartCoordinate) + Math.abs(yEndCoordinate - yStartCoordinate));
    }

    public String toString() {
        return "-----------\nRental\nDate & time: " + rentalDateTime + "\nUserID: " + userID + "\nVehicle ID: " + vehicleId + "\nDuration: " + duration + "\nBreakage: " + hadBreakage + "\nPromotions: " + promotion;
    }

    public LocalDateTime getRentalDateTime() {
        return rentalDateTime;
    }


    public int getxStartCoordinate() {
        return xStartCoordinate;
    }

    public int getyStartCoordinate() {
        return yStartCoordinate;
    }

    public int getxEndCoordinate() {
        return xEndCoordinate;
    }


    public int getyEndCoordinate() {
        return yEndCoordinate;
    }


    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }


    public boolean isHadBreakage() {
        return hadBreakage;
    }


    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public boolean isTenth() {
        return isTenth;
    }

    public void setTenth(boolean tenth) {
        this.isTenth = tenth;
    }

    public static int getI() {
        return i;
    }

    public String getRentalID() {
        return rentalID;
    }

    public String getUserID() {
        return userID;
    }

    public String getVehicleId() {
        return vehicleId;
    }
}
