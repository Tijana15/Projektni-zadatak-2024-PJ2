package org.unibl.etf.projektnizadatak2024.exceptions;

public class MissingQuotationMarksException extends Exception{
    public MissingQuotationMarksException(){
        super("Missing quotation marks while parsing coordinates");
    }
    public MissingQuotationMarksException(String message){
        super(message);
    }
}
