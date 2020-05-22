package it.uniba.main;

import static it.uniba.main.FinalPar.AINASCII;
import static it.uniba.main.FinalPar.CHARPOS0;
import static it.uniba.main.FinalPar.CHARPOS1;
import static it.uniba.main.FinalPar.CHARPOS2;
import static it.uniba.main.FinalPar.CHARPOS3;
import static it.uniba.main.FinalPar.CHARPOS4;
import static it.uniba.main.FinalPar.DIGIT0INASCII;
import static it.uniba.main.FinalPar.MAXROW;
import static it.uniba.main.FinalPar.OUTOFBOUND;
import static it.uniba.main.FinalPar.POS3;
import static it.uniba.main.FinalPar.POS4;
import static it.uniba.main.FinalPar.POS6;
import static it.uniba.main.FinalPar.STRARRDIM;

/**
 * <<entity>><br>
 * <p>Titolo: Pawn</p>
 * <p>Descrizione: La classe Pawn, implementa la classe astratta {@link Piece} e permette di usare il Pedone
 * all'interno del gioco.</p>
 *
 * @author Donato Lucente
 */
class Pawn extends Piece {


	/**
	 * E' il costruttore della classe, assegna al pezzo il colore e la relativa sringa Unicode.
	 * 
	 * @param col: colore del pezzo.
	 */
    Pawn(final int col) { // costruttore classe Pedone
        nMoves = 0;

        this.color = col;
        if (col == 0) {
            this.pieceType = "\u265F"; // pedone nero

        } else {
            this.pieceType = "\u2659"; // pedone bianco

        }
    }

    /**
     * Incrementa il contatore delle mosse del Pedone.
     */
    private void incrementMoves() {
        this.nMoves++;
    }

    /**
     * Controlla che il Pedone sia catturabile en passant.
     *
     * @param x: ascissa del Pedone.
     * @return true, se il Pedone e' catturabile en passant; false, altrimenti.
     */
    private Boolean enPassantCatturable(final int x) { // restituisce true se il pedone
        // ha effettuato una sola mossa con salto di 2,
        // false altrimenti
        if ((getColor() == 1) && (nMoves == 1) && (x == POS4)) {
            return true;
        } else if ((getColor() == 0) && (nMoves == 1) && (x == POS3)) {
            return true;
        }
        return false;
    }

    /**
     * Permette di muovere un Pedone all'interno della scacchiera.
     *
     * @param move: mossa specificata dall'utente.
     * @return array che contiene il Pedone che e' stato mosso convertito a stringa e la cella di destinazione.
     * @throws IllegalMoveException
     */
    static String[] move(final String move) throws IllegalMoveException {
        int x; // ascissa
        int y; // ordinata
        int xCheck; //ordinata su cui fare il check del pezzo
        boolean blackTurn = Game.getBlackTurn();


        y = (int) (move.charAt(CHARPOS0)) - AINASCII; // lettura x e y casella di destinazione
        x = MAXROW - (((int) move.charAt(CHARPOS1)) - DIGIT0INASCII);
        if ((x < 0) || (x > OUTOFBOUND) || (y < 0) || (y > OUTOFBOUND)) {
            throw new IllegalMoveException("Mossa illegale; non rispetta i limiti della scacchiera");
        }
        Pawn p;
        String[] pieceAndCell = new String[STRARRDIM]; //0 pezzo che viene mosso, //2 cella di destinazione
        if (Game.getCell(x, y).getPiece() != null) {
            throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota.");
        }
        if ((x > 0)
                && (x < MAXROW)
                && (Game.getCell(x - 1, y).getPiece() instanceof Pawn)
                && (Game.getCell(x - 1, y).getPiece().getColor() == 0)
                // check se casella in x-1 c'e' pedone con colore 1
                && blackTurn) {
            xCheck = x - 1;
        } else if ((x > 1)
                && (x < MAXROW)
                && (Game.getCell(x - 2, y).getPiece() instanceof Pawn)
                && (Game.getCell(x - 2, y).getPiece().getColor() == 0)
                // check se casella in x-2 c'e' pedone con colore 1
                && (blackTurn)
                && (Game.getCell(x - 2, y).getPiece().getMoves() == 0)) { // se le condizioni sono
            // rispettate fa la mossa
            xCheck = x - 2;
        } else if ((x >= 0)
                && (x < OUTOFBOUND)
                && (Game.getCell(x + 1, y).getPiece() instanceof Pawn)
                && (Game.getCell(x + 1, y).getPiece().getColor() == 1)
                // check se casella in x+1 c'e' pedone con colore 0
                && !blackTurn) { // se le condizioni sono rispettate fa la mossa
            xCheck = x + 1;
        } else if ((x >= 0)
                && (x < POS6)
                && (Game.getCell(x + 2, y).getPiece() instanceof Pawn)
                && (Game.getCell(x + 2, y).getPiece().getColor() == 1)
                // check se casella in x+2 c'e' pedone con colore 1
                && (!blackTurn)
                && (Game.getCell(x + 2, y).getPiece().getMoves() == 0)) { // se le condizioni sono
            // rispettate fa la mossa
            xCheck = x + 2;
        } else {
            throw new IllegalMoveException("Mossa illegale; Nessun pedone puo' spostarsi qui");
        }
        //abbiamo il pawn target, facciamo i controlli su di lui
        p = (Pawn) Game.getCell(xCheck, y).getPiece();
        Game.getCell(xCheck, y).setEmpty();
        Game.getCell(x, y).setPiece(p);
        if (King.isThreatened()) {
            Game.getCell(xCheck, y).setPiece(p);
            Game.getCell(x, y).setEmpty();
            throw new IllegalMoveException("Mossa illegale; il Re è sotto scacco o ci finirebbe dopo questa mossa");
        }
        p.incrementMoves();
        pieceAndCell[0] = p.toString();
        pieceAndCell[2] = move;
        return pieceAndCell;
    }

    /**
     * Consente ad un Pedone di catturare, semplicemente o en passant, un pezzo all'interno della scacchiera.
     *
     * @param move: mossa specificta dall'utente.
     * @return array che contiene il Pedone che effettua la cattura convertito a stringa, il pezzo catturato convertito a stringa e
     * la cella di destinazione.
     * @throws IllegalMoveException
     */

    static String[] capture(final String move) throws IllegalMoveException {
        int x; // ascissa
        int y; // ordinata
        int z; // colonna del pezzo di provenienza
        int xCheck, yCheck; //coordinate target check
        boolean blackTurn = Game.getBlackTurn();

        Piece p, caught;
        String[] pieces = new String[STRARRDIM]; // 0 pezzo che cattura, 1 pezzo catturato, 2 cella di destinazione


        y = (int) (move.charAt(CHARPOS2)) - AINASCII;
        x = MAXROW - (((int) move.charAt(CHARPOS3)) - DIGIT0INASCII);
        if ((x < 0) || (x > OUTOFBOUND) || (y < 0) || (y > OUTOFBOUND)) {
            throw new IllegalMoveException("Mossa illegale; non rispetta i limiti della scacchiera");
        }
        z = (int) (move.charAt(CHARPOS0)) - AINASCII;


        if (Game.getCell(x, y).getPiece()
                == null) { // se cella di destinazione e' vuota prova a fare cattura en passant

            pieces = captureEnPassant(move);
            return pieces;

        }
        if (Math.abs(z - y) >= 2 || (z - y) == 0) {
            throw new IllegalMoveException(
                    "Mossa illegale; Nessuna possibile cattura "
                            + "da parte di un pedone a partire dalla colonna indicata");
        }
        if (blackTurn) {
            xCheck = x - 1;
            if (z == y - 1) {
                yCheck = y - 1;
            } else {
                yCheck = y + 1;
            }
        } else {
            xCheck = x + 1;
            if (z == y - 1) {
                yCheck = y - 1;
            } else {
                yCheck = y + 1;
            }
        }
        if (Game.getCell(x, y) == null) {
            throw new IllegalMoveException("Mossa illegale. La cella e' vuota.");
        }
        if (!(Game.getCell(xCheck, yCheck).getPiece() instanceof Pawn)) {
            throw new IllegalMoveException(
                    "Mossa illegale; Nessun pedone puo' catturare "
                            + "dalla colonna di partenza indicata.");
        }
        p = (Pawn) Game.getCell(xCheck, yCheck).getPiece();
        if (Game.getCell(x, y).getPiece().getColor() != p.getColor()) {
            caught = Game.getCell(x, y).getPiece();
            Game.getCell(x, y).setPiece(p);
            Game.getCell(xCheck, yCheck).setEmpty();
            if (King.isThreatened()) {
                Game.getCell(x, y).setPiece(caught);
                Game.getCell(xCheck, yCheck).setPiece(p);
                throw new IllegalMoveException("Mossa illegale; il Re è sotto scacco o ci finirebbe dopo questa mossa");
            } else {
                if (caught.getColor() == 0) {
                    Game.addBlackCaptured(caught.toString());
                } else {
                    Game.addWhiteCaptured(caught.toString());
                }
                pieces[0] = p.toString();
                pieces[1] = caught.toString();
                pieces[2] = move.substring(CHARPOS2, CHARPOS4);
                return pieces;
            }
        } else {
            throw new IllegalMoveException(
                    "Mossa illegale; Impossibile catturare pezzo dello stesso colore.");
        }
    }

    /**

     * Consente ad un Pedone di catturare solo en passant all'interno della scacchiera.
     *
     * @param move: mossa specificata dall'utente.
     * @return array che contiene il Pedone che effettua la cattura convertito a stringa, il pezzo catturato convertito a stringa e
     * la cella di destinazione.
     * @throws IllegalMoveException
     */
    static String[] captureEnPassant(final String move) throws IllegalMoveException {

        int x; // ascissa
        int y; // ordinata
        int z; // colonna del pezzo di provenienza
        int xCheck, yCheck; //coordinate target check
        boolean blackTurn = Game.getBlackTurn();
        Piece p;
        String[] pieces = new String[STRARRDIM]; // 0 pezzo catturato, 1 pezzo che cattura //2 cella di destinazione
        y = (int) (move.charAt(CHARPOS2)) - AINASCII;
        x = MAXROW - (((int) move.charAt(CHARPOS3)) - DIGIT0INASCII);
        if ((x < 0) || (x > OUTOFBOUND) || (y < 0) || (y > OUTOFBOUND)) {
            throw new IllegalMoveException("Mossa illegale; non rispetta i limiti della scacchiera");
        }
        z = (int) (move.charAt(CHARPOS0)) - AINASCII;
        if (Math.abs(z - y) >= 2 || (z - y) == 0) {
            throw new IllegalMoveException(
                    "Mossa illegale; Nessuna possibile cattura "
                            + "da parte di un pedone a partire dalla colonna indicata");
        }
        if (blackTurn) {
            xCheck = x - 1;
            if (z == y - 1) {
                yCheck = y - 1;
            } else {
                yCheck = y + 1;
            }
        } else {
            xCheck = x + 1;
            if (z == y - 1) {
                yCheck = y - 1;
            } else {
                yCheck = y + 1;
            }
        }
        if (!(Game.getCell(xCheck, yCheck).getPiece()
                instanceof Pawn)) {
            throw new IllegalMoveException(
                    "Mossa illegale; Nessun pedone puo' effettuare cattura e.p."
                            + " a partire dalla colonna inserita");
        }
        if (Game.getCell(xCheck, y).getPiece() instanceof Pawn) {
            p = (Pawn) Game.getCell(xCheck, yCheck).getPiece();
            Pawn caught = (Pawn) Game.getCell(xCheck, y).getPiece();
            if ((Game.getCell(x, y).getPiece() == null) && (caught.enPassantCatturable(xCheck))) {
                Game.getCell(x, y).setPiece(p);
                Game.getCell(xCheck, yCheck).setEmpty();
                Game.getCell(xCheck, y).setEmpty();
                if (King.isThreatened()) {
                    Game.getCell(x, y).setEmpty();
                    Game.getCell(xCheck, yCheck).setPiece(p);
                    Game.getCell(xCheck, y).setPiece(caught);
                    throw new IllegalMoveException("Mossa illegale; il Re e' sotto scacco "
                            + "o ci finirebbe dopo questa mossa");
                } else {
                    if (caught.getColor() == 0) {
                        Game.addBlackCaptured(caught.toString());
                    } else {
                        Game.addWhiteCaptured(caught.toString());
                    }
                    pieces[0] = p.toString();
                    pieces[1] = caught.toString();
                    pieces[2] = move.substring(CHARPOS2, CHARPOS4) + " e.p.";
                    return pieces;
                }
            } else {
                throw new IllegalMoveException(
                        "Mossa illegale; Pezzo non catturabile con e.p. o cella di destinazione occupata");
            }
        } else {
            throw new IllegalMoveException(
                    "Mossa illegale; Nessun pedone catturabile e.p. alla posizione indicata.");
        }
    }
}
