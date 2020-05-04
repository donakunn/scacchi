package it.uniba.main;

/**
* <<no-ECB>><br>
* Exception class for illegal move cases.
* 
* @author Donato Lucente
* 
*/
public class IllegalMoveException extends Exception {

    public IllegalMoveException(String s) {
        super(s);
    }

}