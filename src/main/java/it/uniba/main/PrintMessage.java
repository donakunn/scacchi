package it.uniba.main;

import java.util.ArrayList;

import static it.uniba.main.FinalPar.MAXCOL;
import static it.uniba.main.FinalPar.MAXROW;

/**
 * <<boundary>><br>
 * <p><I>Titolo</I>: PrintMessage</p>
 * <p><I>Descrizione</I>: La classe PrintMessage contiene tutti i messaggi e la scacchiera che vengono stampati a video durante 
 * l'esecuzione del programma.</p>
 * 
 * @author Donato Lucente 
 */

public final class PrintMessage {
	/**
	 * E' il costruttore della classe.
	 */
    private PrintMessage() {
    }
    
    /**
     * Stampa a video la scacchiera.
     * @param board matrice di stringhe.
     */
    public static void printBoard(final String[][] board) {
        System.out.println("    a    b    c    d    e    f    g    h");
        for (int i = 0; i < MAXROW; i++) {
            System.out.print(MAXROW - i + "  ");
            for (int j = 0; j < MAXCOL; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.print(MAXCOL - i + "  ");
            System.out.println("\n");
        }
        System.out.println("    a    b    c    d    e    f    g    h");
    }
    
    /**
     * Stampa a video un messaggio.
     * @param message messaggio da mostrare.
     */
    public static void printMessage(String message) {
    	System.out.println(message);
    }
    
    /**
     * Stampa a video il turno del giocatore.
     * @param turn turno di chi sta giocando.
     */
    public static void printTurn(String turn) {
    	System.out.println("Inserire comando (turno dei " + turn + ")");
    }
    
    /**
     * Stampa a video un messaggio d'errore.
     * @param e messaggio di errore da mostrare.
     */
    public static void printError(String e) {
    	System.err.println(e);
    }

    /**
     * Stampa a video le mosse effettuate.
     * @param moves arraylist che contiene le mosse effettuate.
     */
    public static void printMoves(final ArrayList<String> moves) {
        if (moves.size() == 0) {
            System.out.println("Non sono ancora state effettuate mosse");
        } else {
            int j; // secondo indice
            int k = 1; // numero mossa
            for (int i = 0; i < moves.size(); i++) {
                j = i + 1;
                if (j < moves.size()) {

                    System.out.println(k + ". " + moves.get(i) + " " + moves.get(j));
                    i++;
                    k++;

                } else if (i == moves.size() - 1) {
                    System.out.println(k + ". " + moves.get(i));
                }
            }
        }
    }

    /**
     * Stampa a video le catture effettuate.
     * @param capturedBlacks pezzi neri catturati.
     * @param capturedWhites pezzi bianchi catturati.
     */
    public static void printCaptures(final ArrayList<String> capturedBlacks, final ArrayList<String> capturedWhites) {
        if (capturedBlacks.size() == 0) {
            System.out.println("Nessun pezzo nero catturato");
        } else {
            System.out.println("Pezzi neri catturati: " + capturedBlacks);
        }
        if (capturedWhites.size() == 0) {
            System.out.println("Nessun pezzo bianco catturato");
        } else {
            System.out.println("Pezzi bianchi catturati: " + capturedWhites);
        }
    }

    /**
     * Stampa a video la mossa di quel determinato turno.
     * @param pieceAndCell array contenente il pezzo che effettua la mossa e la sua casella di destinazione.
     */
    public static void printAMove(final String[] pieceAndCell) {
        System.out.println(pieceAndCell[0] + " spostato su " + pieceAndCell[2]);
    }

    /**
     * Stampa a video il messaggio dell'arrocco corto.
     */
    public static void printShortCastling() {
        System.out.println("Arrocco corto eseguito");
    }

    /**
     * Stampa a video il messaggio dell'arrocco lungo.
     */
    public static void printLongCastling() {
        System.out.println("Arrocco lungo eseguito");
    }

    /**
     * Stampa a video la cattura di quel determinato turno.
     * @param piecesAndCell array contenente il pezzo catturato convertito a stringa, il pezzo che effettua la cattura convertito
     * a stringa e la casella di destinazione.
     */
    public static void printACapture(final String[] piecesAndCell) {
        System.out.println(piecesAndCell[1] + " e' stato catturato da " + piecesAndCell[0] + " su " + piecesAndCell[2]);
    }
}
