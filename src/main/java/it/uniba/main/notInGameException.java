package it.uniba.main;

public class notInGameException extends Exception { 
    public notInGameException() {
        super("[#1] You have to be in an active game to use that command.");
    }
}