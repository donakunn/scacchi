package it.uniba.main;

/**
 * <<no-ECB>><br>
 * Exception class for illegal move cases.
 * 
 * @author Donato Lucente
 * 
 */
public class IllegalMoveException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IllegalMoveException(String s) {
        super(s);
    }

}