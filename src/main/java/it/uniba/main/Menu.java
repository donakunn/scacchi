package it.uniba.main;

import java.util.ArrayList;

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
public class Menu {
    private Game game = new Game();

    public String help() {
        return "Lista di comandi utilizzabili:\n" + "help\n" + "play\n"
                + "quit\n" +
                "Lista di comandi utilizzabili solo se in partita:\n" +
                "board\n"
                + "captures\n"
                + "moves\n"
                +
                "Per effettuare una mossa e' necessario specificarla in notazione algebrica; \nPer la cattura en passant si puo' specificare 'e.p.' o 'ep' alla fine della mossa in notazione algebrica";
    }

    public String[][] board() {
        String[][] board = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

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

    public String[] getMove(String input)
            throws IllegalArgumentException, IndexOutOfBoundsException, IllegalMoveException {
        char chosenPiece = input.charAt(0);
        String[] pieces = new String[3];
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


    public ArrayList<String> Blackcaptured() {
        return game.getBlacks();
    }

    public ArrayList<String> Whitecaptured() {
        return game.getWhites();
    }

}
