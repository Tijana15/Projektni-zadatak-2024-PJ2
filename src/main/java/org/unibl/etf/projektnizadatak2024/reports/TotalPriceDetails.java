package org.unibl.etf.projektnizadatak2024.reports;

public class TotalPriceDetails {
    private final double totalPrice;
    private final double discount;
    private final double promotion;
    private final double basePrice;
    private final double amount;

    public TotalPriceDetails(double totalPrice, double discount, double promotion, double basePrice, double amount) {
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.promotion = promotion;
        this.basePrice = basePrice;
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getPromotion() {
        return promotion;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getAmount() {
        return amount;
    }
}
