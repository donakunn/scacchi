package it.uniba.main;

public class IllegalMoveException extends Exception {
    
    public IllegalMoveException() {
        System.err.println("Illegal move, please try again. ");
    }
    
}