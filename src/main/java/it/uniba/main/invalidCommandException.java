package it.uniba.main;

public class invalidCommandException extends Exception { 
    public invalidCommandException() {
        super("[#2] That is not a valid command nor move.");
    }
}