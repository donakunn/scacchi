package it.uniba.main;

import java.util.ArrayList;

import static it.uniba.main.FinalPar.MAXCOL;
import static it.uniba.main.FinalPar.MAXROW;

public final class PrintMessage {
    private PrintMessage() {
    }

    static void printBoard(final String[][] board) {
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

    static void printMoves(final ArrayList<String> moves) {
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

    static void printCaptures(final ArrayList<String> capturedBlacks, final ArrayList<String> capturedWhites) {
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

    static void printAMove(final String[] pieceAndCell) {
        System.out.println(pieceAndCell[0] + " spostato su " + pieceAndCell[2]);
    }

    static void printShortCastling() {
        System.out.println("Arrocco corto eseguito");
    }

    static void printLongCastling() {
        System.out.println("Arrocco lungo eseguito");
    }

    static void printACapture(final String[] piecesAndCell) {
        System.out.println(piecesAndCell[1] + " e' stato catturato da " + piecesAndCell[0] + " su " + piecesAndCell[2]);
    }
}
