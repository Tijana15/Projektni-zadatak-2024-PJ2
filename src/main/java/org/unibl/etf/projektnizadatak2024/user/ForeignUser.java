package org.unibl.etf.projektnizadatak2024.user;

public class ForeignUser extends User {
    private String passport;

    public ForeignUser(String name, String drivingLicence, String passport) {
        super(name, drivingLicence);
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "\nFOREIGN USER" + "\nID: " + super.getID() + "\nDRIVER LICENCE: " + getDriverLicence() + "\nPASSPORT: " + passport;
    }

}
