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
 * <p><I>Titolo</I>: Game</p>
 * <p><I>Descrizione</I>: la classe Game contiene la scacchiera, gestisce i turni dei giocatori, tiene conto
 * di tutte le mosse effettuate e di tutte le catture effettuate. 
 *
 * @author Megi Gjata
 * @author Mario Giordano
 * @author Donato Lucente
 * @author Patrick Clark
 * @author Filippo Iacobellis
 */
class Game {
	/**
	 * Imposta il turno nero a false.
	 */
    private static boolean blackTurn = false;
    /**
     * Crea una matrice di celle, ovvero la scacchiera.
     */
    private static Cell[][] board = new Cell[MAXROW][MAXCOL];
    /**
     * Arraylist contenente tutte le mosse effetuate.
     */
    private static ArrayList<String> movesDone = new ArrayList<String>();
    /**
     * Arraylist contenente tutti i pezzi neri catturati.
     */
    private static ArrayList<String> blacksCaptured = new ArrayList<String>();
    /**
     * Arraylist contenente tutti i pezzi bianchi catturati.
     */
    private static ArrayList<String> whitesCaptured = new ArrayList<String>();

    /**
     * Inizia una nuova partita assegnando il turno al giocatore bianco, svuota gli arraylist contenenti mosse e catture e 
     * inizializza la scacchiera.
     */
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

    /**
     * Gestisce mosse, catture semplici, catture en passant ed eccezioni per il Pedone.
     * @param input mossa specificata dall'utente.
     * @return array contenente il Pedone che e' stato mosso convertito a stringa e la cella di destinazione se si tratta di una mossa.
     * Array contenente il Pedone che effettua la cattura convertito a stringa, il pezzo catturato convertito a stringa e la cella di 
     * destinazione se si tratta di una cattura.
     * @throws IllegalMoveException messaggio di eccezione.
     */
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

    /**
     * Gestisce mosse, catture semplici, catture en passant ed eccezioni per il Re.
     * @param move mossa specificata dall'utente.
     * @return array contenente il Re che effettua la mossa o la cattura convertito a stringa, la mossa effettuata e, 
     * se si tratta di una cattura, contiene anche il pezzo catturato convertito a stringa.
     * @throws IllegalMoveException messaggio di eccezione.
     */
    String[] moveKing(final String move) throws IllegalMoveException {
        return King.move(move);
    }

    /**
     * Gestisce mosse, catture semplici, catture en passant ed eccezioni per la Regina.
     * @param move mossa specificata dall'utente.
     * @return array contenente la Regina che effettua la mossa o la cattura convertita a stringa, la mossa effettuata e,
     * se si tratta di una cattura, contiene anche il pezzo catturato convertito a stringa.
     * @throws IllegalMoveException messaggio di eccezione.
     */
    String[] moveQueen(final String move) throws IllegalMoveException {
        return Queen.move(move);
    }

    /**
     * Gestisce mosse, catture semplici, catture en passant ed eccezioni per l'Alfiere.
     * @param move mossa specificata dall'utente.
     * @return array contenente l'Alfiere che effettua la mossa o la cattura convertito a stringa, la mossa effettuata e,
     * se si tratta di una cattura, contiene anche il pezzo catturato convertito a stringa.
     * @throws IllegalMoveException messaggio di eccezione.
     */
    String[] moveBishop(final String move) throws IllegalMoveException {
        return Bishop.move(move);
    }

    /**
     * Gestisce mosse, catture sepmlici, catture en passant ed eccezioni per il Cavallo.
     * @param move mossa specificata dall'utente.
     * @return array contenente il Cavallo che effettua la mossa o la cattura convertito a stringa, la mossa effettuata e,
     * se si tratta di una cattura, contiene anche il pezzo catturato convertito a stringa.
     * @throws IllegalMoveException messaggio di eccezione.
     */
    String[] moveKnight(final String move) throws IllegalMoveException {
        return Knight.move(move);
    }

    /**
     * Gestisce mosse, catture semplici, catture en passant ed eccezioni per la Torre.
     * @param move mossa specificata dall'utente.
     * @return array contenente la Torre che effettua la mossa o la cattura convertita a stringa, la mossa effettuata e,
     * se si tratta di una cattura, contiene anche il pezzo catturato convertito a stringa.
     * @throws IllegalMoveException messaggio di eccezione.
     */
    String[] moveRook(final String move) throws IllegalMoveException {
        return Rook.move(move);
    }

    /**
     * Verifica se puo' essere effettuato l'arrocco tramite la stringa inserita dall'utente.
     * @param move mossa specificata dall'utente.
     * @return  array contenente la stringa che determina il tipo di arrocco.
     * @throws IllegalMoveException messaggio di eccezione.
     */
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

    /**
     * 
     * @return true se e' il turno dei neri; false, altrimenti.
     */
    static boolean getBlackTurn() {
        return blackTurn;
    }

    /**
     * Cambia il turno all'interno della partita.
     */
    void changeTurn() {
        setNotBlackTurn();
    }

    /**
     * Imposta il turno del bianco, assegnando false al turno dei neri.
     */
    private static void setWhiteTurn() {
        blackTurn = false;
    }

    /**
     * Cambia il turno del nero.
     */
    private static void setNotBlackTurn() {
        blackTurn = !blackTurn;
    }

    /**
     * 
     * @param x ascissa.
     * @param y ordinata.
     * @return cella con ascissa x e ordinata y.
     */
    static Cell getCell(final int x, final int y) {
        return board[x][y];
    }

    /**
     * Aggiunge le mosse effettuate all'arraylist contenente tutte le mosse effettuate nella partita.
     * @param move mossa specificata dall'utente.
     */
    void addMove(final String move) {
        movesDone.add(move);
    }

    /**
     * 
     * @return mosse effettuate durante la partita.
     */
    ArrayList<String> getMoves() {
        return movesDone;
    }

    /**
     * 
     * @return pezzi neri catturati durante la partita.
     */
    ArrayList<String> getBlacks() {
        return blacksCaptured;
    }

    /**
     * 
     * @return pezzi bianchi catturati durante la partita.
     */
    ArrayList<String> getWhites() {
        return whitesCaptured;
    }

    /**
     * Aggiunge il pezzo bianco catturato all'arraylist contenente tutti i pezzi bianchi catturati.
     * @param captured pezzo catturato.
     */
    static void addWhiteCaptured(final String captured) {
        whitesCaptured.add(captured.toString());
    }

    /**
     * Aggiunge il pezzo nero catturato all'arraylist contenente tutti i pezzi neri catturati.
     * @param captured pezzo catturato.
     */
    static void addBlackCaptured(final String captured) {
        blacksCaptured.add(captured.toString());
    }
}
