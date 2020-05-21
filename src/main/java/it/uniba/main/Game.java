package it.uniba.main;

import java.util.ArrayList;

/**
 * <<entity>><br>
 * Game is the main entity of the application. It contains the chessboard, turn of player currently
 * playing, white and black pieces captured, moves done. It also contains the main methods to
 * activate a move or a capture event for each piece.
 *
 * @author Megi Gjata
 * @author Mario Giordano
 * @author Donato Lucente
 * @author Patrick Clark
 * @author Filippo Iacobellis
 */
class Game {
    private static boolean blackTurn = false;
    private static Cell board[][] = new Cell[8][8];
    private static ArrayList<String> movesDone = new ArrayList<String>();
    private static ArrayList<String> BlacksCaptured = new ArrayList<String>();
    private static ArrayList<String> WhitesCaptured = new ArrayList<String>();

    void newGame() {
        setBlackTurn();
        movesDone.clear();
        BlacksCaptured.clear();
        WhitesCaptured.clear();
        for (int j = 0; j < 8; j++) {
            // initialize pawns a2-h2 (white side)
            board[1][j] = new Cell(new Pawn(0));

            // initialize pawns a7-h7 (black side)
            board[6][j] = new Cell(new Pawn(1));
        }

        // initialize pieces a1-h1 (white side)
        board[0][0] = new Cell(new Rook(0));
        board[0][1] = new Cell(new Knight(0));
        board[0][2] = new Cell(new Bishop(0));
        board[0][3] = new Cell(new Queen(0));
        board[0][4] = new Cell(new King(0));
        board[0][5] = new Cell(new Bishop(0));
        board[0][6] = new Cell(new Knight(0));
        board[0][7] = new Cell(new Rook(0));

        // initialize empty cells
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Cell(null);
            }
        }

        // initialize pieces a8-h8 (black side)
        board[7][0] = new Cell(new Rook(1));
        board[7][1] = new Cell(new Knight(1));
        board[7][2] = new Cell(new Bishop(1));
        board[7][3] = new Cell(new Queen(1));
        board[7][4] = new Cell(new King(1));
        board[7][5] = new Cell(new Bishop(1));
        board[7][6] = new Cell(new Knight(1));
        board[7][7] = new Cell(new Rook(1));
    }

    String[] movePawn(String input) throws IllegalMoveException {
        if (input.length() == 2) {
            return Pawn.move(input);
        } else if (input.length() == 4) {
            if (input.substring(1, 2).equals("x")) {
                return Pawn.capture(input);
            }
            throw new IllegalMoveException("Mossa non valida");
        } else if (input.length() == 8) {
            if ((input.substring(1, 2).toLowerCase().equals("x"))
                    && (input.substring(4, 8).toLowerCase().equals("e.p."))) {
                return Pawn.captureEnPassant(input);
            } else {
                throw new IllegalMoveException("Mossa non valida");
            }
        } else if (input.length() == 6) {
            if ((input.substring(1, 2).toLowerCase().equals("x"))
                    && (input.substring(4, 6).toLowerCase().equals("ep"))) {
                return Pawn.captureEnPassant(input);
            } else {
                throw new IllegalMoveException("Mossa non valida");
            }
        } else {
            throw new IllegalMoveException(
                    "Mossa illegale o comando inesistente; Riprova utilizzando un comando consentito o inserisci una mossa legale");
        }
    }

    String[] moveKing(String move) throws IllegalMoveException {
        return King.move(move);
    }

    String[] moveQueen(String move) throws IllegalMoveException {
        return Queen.move(move);
    }

    String[] moveBishop(String move) throws IllegalMoveException {
        return Bishop.move(move);
    }

    String[] moveKnight(String move) throws IllegalMoveException {
        return Knight.move(move);
    }

    String[] moveRook(String move) throws IllegalMoveException {
        return Rook.move(move);
    }

    String[] tryCastling(String move) throws IllegalMoveException {
        if (move.equals("0-0") || move.equals("O-O")) {
            return King.castling(false);
        } else if (move.equals("0-0-0") || move.equals("O-O-O")) {
            return King.castling(true);
        } else {
            throw new IllegalMoveException(
                    "Errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; 0-0-0 oppure O-O-O per arrocco lungo");
        }
    }

    static boolean getBlackTurn() {
        return blackTurn;
    }

    void changeTurn() {
        setNotBlackTurn();
    }

    private static void setBlackTurn() {
        blackTurn = false;
    }

    private static void setNotBlackTurn() {
        blackTurn = !blackTurn;
    }

    static Cell getCell(int x, int y) {
        return board[x][y];
    }

    void addMove(String move) {
        movesDone.add(move);
    }

    ArrayList<String> getMoves() {
        return movesDone;
    }

    ArrayList<String> getBlacks() {
        return BlacksCaptured;
    }

    ArrayList<String> getWhites() {
        return WhitesCaptured;
    }

    static void addWhiteCaptured(String captured) {
        WhitesCaptured.add(captured.toString());
    }

    static void addBlackCaptured(String captured) {
        BlacksCaptured.add(captured.toString());
    }
}
