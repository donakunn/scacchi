package it.uniba.main;
import java.util.ArrayList;
import static it.uniba.main.FinalPar.ALTEPLENGTH;
import static it.uniba.main.FinalPar.CAPTEPLENGTH;
import static it.uniba.main.FinalPar.CAPTURELENGTH;
import static it.uniba.main.FinalPar.CHARPOS1;
import static it.uniba.main.FinalPar.CHARPOS2;
import static it.uniba.main.FinalPar.CHARPOS4;
import static it.uniba.main.FinalPar.CHARPOS6;
import static it.uniba.main.FinalPar.CHARPOS8;
import static it.uniba.main.FinalPar.MAXCOL;
import static it.uniba.main.FinalPar.MAXROW;
import static it.uniba.main.FinalPar.MOVELENGTH;
import static it.uniba.main.FinalPar.POS0;
import static it.uniba.main.FinalPar.POS1;
import static it.uniba.main.FinalPar.POS2;
import static it.uniba.main.FinalPar.POS3;
import static it.uniba.main.FinalPar.POS4;
import static it.uniba.main.FinalPar.POS5;
import static it.uniba.main.FinalPar.POS6;
import static it.uniba.main.FinalPar.POS7;


/**
 * <<control>><br>
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
    private static Cell[][] board = new Cell[MAXROW][MAXCOL];
    private static ArrayList<String> movesDone = new ArrayList<String>();
    private static ArrayList<String> blacksCaptured = new ArrayList<String>();
    private static ArrayList<String> whitesCaptured = new ArrayList<String>();

    void newGame() {
        final int firstEmptyRow = 2;
        final int lastEmptyRow = 5;
        setWhiteTurn();
        movesDone.clear();
        blacksCaptured.clear();
        whitesCaptured.clear();
        for (int j = 0; j < MAXCOL; j++) {
            // initialize pawns a2-h2 (white side)
            board[POS1][j] = new Cell(new Pawn(0));

            // initialize pawns a7-h7 (black side)
            board[POS6][j] = new Cell(new Pawn(1));
        }

        // initialize pieces a1-h1 (white side)
        board[POS0][POS0] = new Cell(new Rook(0));
        board[POS0][POS1] = new Cell(new Knight(0));
        board[POS0][POS2] = new Cell(new Bishop(0));
        board[POS0][POS3] = new Cell(new Queen(0));
        board[POS0][POS4] = new Cell(new King(0));
        board[POS0][POS5] = new Cell(new Bishop(0));
        board[POS0][POS6] = new Cell(new Knight(0));
        board[POS0][POS7] = new Cell(new Rook(0));

        // initialize empty cells
        for (int i = firstEmptyRow; i <= lastEmptyRow; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                board[i][j] = new Cell(null);
            }
        }

        // initialize pieces a8-h8 (black side)
        board[POS7][POS0] = new Cell(new Rook(1));
        board[POS7][POS1] = new Cell(new Knight(1));
        board[POS7][POS2] = new Cell(new Bishop(1));
        board[POS7][POS3] = new Cell(new Queen(1));
        board[POS7][POS4] = new Cell(new King(1));
        board[POS7][POS5] = new Cell(new Bishop(1));
        board[POS7][POS6] = new Cell(new Knight(1));
        board[POS7][POS7] = new Cell(new Rook(1));
    }

    String[] movePawn(final String input) throws IllegalMoveException {
        if (input.length() == MOVELENGTH) {
            return Pawn.move(input);
        } else if (input.length() == CAPTURELENGTH) {
            if (input.substring(CHARPOS1, CHARPOS2).equals("x")) {
                return Pawn.capture(input);
            }
            throw new IllegalMoveException("Mossa non valida");
        } else if (input.length() == ALTEPLENGTH) {
            if ((input.substring(CHARPOS1, CHARPOS2).toLowerCase().equals("x"))
                    && (input.substring(CHARPOS4, CHARPOS8).toLowerCase().equals("e.p."))) {
                return Pawn.captureEnPassant(input);
            } else {
                throw new IllegalMoveException("Mossa non valida");
            }
        } else if (input.length() == CAPTEPLENGTH) {
            if ((input.substring(CHARPOS1, CHARPOS2).toLowerCase().equals("x"))
                    && (input.substring(CHARPOS4, CHARPOS6).toLowerCase().equals("ep"))) {
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

    private static void setWhiteTurn() {
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
