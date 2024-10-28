package org.unibl.etf.projektnizadatak2024.exceptions;

public class ParsingNullElements extends Exception{
    public ParsingNullElements() {
        super("One of the important elements while parsing is null");
    }
    public ParsingNullElements(String message){
        super(message);
    }
}
