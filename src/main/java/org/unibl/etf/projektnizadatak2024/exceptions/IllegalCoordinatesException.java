package org.unibl.etf.projektnizadatak2024.exceptions;

public class IllegalCoordinatesException extends Exception{
    public IllegalCoordinatesException(){
        super("Illegal coordinates for rental!");
    }
    public IllegalCoordinatesException(String message){
        super(message);
    }
}
