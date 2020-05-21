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
    static final int ROWDIM = 8;
    static final int COLDIM = 8;
    private static boolean blackTurn = false;
    private static Cell[][] board = new Cell[ROWDIM][COLDIM];
    private static ArrayList<String> movesDone = new ArrayList<String>();
    private static ArrayList<String> blacksCaptured = new ArrayList<String>();
    private static ArrayList<String> whitesCaptured = new ArrayList<String>();

    void newGame() {
        final int pos0 = 0;
        final int pos1 = 1;
        final int pos2 = 2;
        final int pos3 = 3;
        final int pos4 = 4;
        final int pos5 = 5;
        final int pos6 = 6;
        final int pos7 = 7;
        setBlackTurn();
        movesDone.clear();
        blacksCaptured.clear();
        whitesCaptured.clear();
        for (int j = 0; j < ROWDIM; j++) {
            // initialize pawns a2-h2 (white side)
            board[pos1][j] = new Cell(new Pawn(0));

            // initialize pawns a7-h7 (black side)
            board[pos6][j] = new Cell(new Pawn(1));
        }

        // initialize pieces a1-h1 (white side)
        board[pos0][pos0] = new Cell(new Rook(0));
        board[pos0][pos1] = new Cell(new Knight(0));
        board[pos0][pos2] = new Cell(new Bishop(0));
        board[pos0][pos3] = new Cell(new Queen(0));
        board[pos0][pos4] = new Cell(new King(0));
        board[pos0][pos5] = new Cell(new Bishop(0));
        board[pos0][pos6] = new Cell(new Knight(0));
        board[pos0][pos7] = new Cell(new Rook(0));

        // initialize empty cells
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < ROWDIM; j++) {
                board[i][j] = new Cell(null);
            }
        }

        // initialize pieces a8-h8 (black side)
        board[pos7][pos0] = new Cell(new Rook(1));
        board[pos7][pos1] = new Cell(new Knight(1));
        board[pos7][pos2] = new Cell(new Bishop(1));
        board[pos7][pos3] = new Cell(new Queen(1));
        board[pos7][pos4] = new Cell(new King(1));
        board[pos7][pos5] = new Cell(new Bishop(1));
        board[pos7][pos6] = new Cell(new Knight(1));
        board[pos7][pos7] = new Cell(new Rook(1));
    }

    String[] movePawn(final String input) throws IllegalMoveException {
        final int moveLength = 2;
        final int captureLength = 4;
        final int ambiguityEpLength = 8;
        final int captEpLength = 6;

        if (input.length() == moveLength) {
            return Pawn.move(input);
        } else if (input.length() == captureLength) {
            if (input.substring(1, 2).equals("x")) {
                return Pawn.capture(input);
            }
            throw new IllegalMoveException("Mossa non valida");
        } else if (input.length() == ambiguityEpLength) {
            if ((input.substring(1, 2).toLowerCase().equals("x"))
                    && (input.substring(4, 8).toLowerCase().equals("e.p."))) {
                return Pawn.captureEnPassant(input);
            } else {
                throw new IllegalMoveException("Mossa non valida");
            }
        } else if (input.length() == captEpLength) {
            if ((input.substring(1, 2).toLowerCase().equals("x"))
                    && (input.substring(4, 6).toLowerCase().equals("ep"))) {
                return Pawn.captureEnPassant(input);
            } else {
                throw new IllegalMoveException("Mossa non valida");
            }
        } else {
            throw new IllegalMoveException(
                    "Mossa illegale o comando inesistente; Riprova utilizzando "
                            + "un comando consentito o inserisci una mossa legale");
        }
    }

    String[] moveKing(final String move) throws IllegalMoveException {
        return King.move(move);
    }

    String[] moveQueen(final String move) throws IllegalMoveException {
        return Queen.move(move);
    }

    String[] moveBishop(final String move) throws IllegalMoveException {
        return Bishop.move(move);
    }

    String[] moveKnight(final String move) throws IllegalMoveException {
        return Knight.move(move);
    }

    String[] moveRook(final String move) throws IllegalMoveException {
        return Rook.move(move);
    }

    String[] tryCastling(final String move) throws IllegalMoveException {
        if (move.equals("0-0") || move.equals("O-O")) {
            return King.castling(false);
        } else if (move.equals("0-0-0") || move.equals("O-O-O")) {
            return King.castling(true);
        } else {
            throw new IllegalMoveException(
                    "Errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; "
                            + "0-0-0 oppure O-O-O per arrocco lungo");
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

    static Cell getCell(final int x, final int y) {
        return board[x][y];
    }

    void addMove(final String move) {
        movesDone.add(move);
    }

    ArrayList<String> getMoves() {
        return movesDone;
    }

    ArrayList<String> getBlacks() {
        return blacksCaptured;
    }

    ArrayList<String> getWhites() {
        return whitesCaptured;
    }

    static void addWhiteCaptured(final String captured) {
        whitesCaptured.add(captured.toString());
    }

    static void addBlackCaptured(final String captured) {
        blacksCaptured.add(captured.toString());
    }
}
