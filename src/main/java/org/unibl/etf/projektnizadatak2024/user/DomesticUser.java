package org.unibl.etf.projektnizadatak2024.user;

public class DomesticUser extends User {
    private String identityCard;

    public DomesticUser(String id, String driverLicence, String identityCard) {
        super(id, driverLicence);
        this.identityCard = identityCard;
    }

    @Override
    public String toString() {
        return "\nDOMESTIC USER" + "\nID: " + super.getID()+ "\nDRIVER LICENCE: " + super.getDriverLicence() + "\nIDENTITY CARD: " + identityCard;
    }

}
