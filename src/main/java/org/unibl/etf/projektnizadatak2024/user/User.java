package org.unibl.etf.projektnizadatak2024.user;

public class User {
    private String ID;
    private String driverLicence;

    public User(String ID) {
        this.ID = ID;
    }

    public User(String ID, String driverLicence) {
        this.ID = ID;
        this.driverLicence = driverLicence;
    }


    public String getID() {
        return ID;
    }

    public String getDriverLicence() {
        return driverLicence;
    }
    @Override
    public String toString() {
        return "\nUSER" +"\nID: "+ID+"\nDRIVER LICENCE: "+driverLicence;
    }

}
