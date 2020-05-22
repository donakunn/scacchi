package it.uniba.main;

import java.util.ArrayList;

import static it.uniba.main.FinalPar.MAXCOL;
import static it.uniba.main.FinalPar.MAXROW;

/**
 * <<control>><br>
 * Menu class, containing all methods of the command list.
 *
 * @author Megi Gjata
 * @author Mario Giordano
 * @author Donato Lucente
 * @author Patrick Clark
 * @author Filippo Iacobellis
 */
public final class Menu {
    private Game game = new Game();

    public String help() {
        return "Lista di comandi utilizzabili:\n"
                + "help\n"
                + "play\n"
                + "quit\n"
                + "Lista di comandi utilizzabili solo se in partita:\n"
                + "board\n"
                + "captures\n"
                + "moves\n"
                + "Per effettuare una mossa e' necessario specificarla in notazione algebrica; \n"
                + "Per la cattura en passant si puo' specificare 'e.p.' "
                + "o 'ep' alla fine della mossa in notazione algebrica";
    }

    public String[][] board() {
        String[][] board = new String[MAXROW][MAXCOL];
        for (int i = 0; i < MAXROW; i++) {
            for (int j = 0; j < MAXCOL; j++) {

                board[i][j] = Game.getCell(i, j).toString();
            }

        }

        return board;
    }

    public ArrayList<String> moves() {
        return game.getMoves();

    }

    public void play() {
        game.newGame();
    }

    public String[] getMove(final String input)
            throws IllegalArgumentException, IndexOutOfBoundsException, IllegalMoveException {
        char chosenPiece = input.charAt(0);
        String[] pieces;
        switch (chosenPiece) {
            case 'T': //da sistemare
                pieces = game.moveRook(input);
                break;

            case 'C': //da sistemare
                pieces = game.moveKnight(input);
                break;

            case 'A':
                pieces = game.moveBishop(input);
                break;

            case 'D':
                pieces = game.moveQueen(input);
                break;

            case 'R':
                pieces = game.moveKing(input);
                break;

            case '0':
            case 'O':
                pieces = game.tryCastling(input);
                break;

            default:
                pieces = game.movePawn(input);
                break;
        }

        game.addMove(input);
        game.changeTurn();
        return pieces;
    }

    public Boolean getBlackTurn() {
        return Game.getBlackTurn();
    }


    public ArrayList<String> blackCaptured() {
        return game.getBlacks();
    }

    public ArrayList<String> whiteCaptured() {
        return game.getWhites();
    }

}
