package it.uniba.main;

import static it.uniba.main.FinalPar.AINASCII;
import static it.uniba.main.FinalPar.CAPTURELENGTH;
import static it.uniba.main.FinalPar.CHARPOS1;
import static it.uniba.main.FinalPar.CHARPOS2;
import static it.uniba.main.FinalPar.DIGIT0INASCII;
import static it.uniba.main.FinalPar.MAXCOL;
import static it.uniba.main.FinalPar.MAXROW;
import static it.uniba.main.FinalPar.OUTOFBOUND;
import static it.uniba.main.FinalPar.PIECEMOVELENGTH;
import static it.uniba.main.FinalPar.STRARRDIM;

/**
 * <<entity>><br>
 * Bishop class, implementing the abstract class {@link Piece}
 *
 * @author Donato Lucente
 */
class Bishop extends Piece {

    Bishop(final int col) {

        this.color = col;
        if (col == 0) {
            this.pieceType = "\u265D"; // Alfiere nero

        } else  {
            this.pieceType = "\u2657"; // Alfiere bianco

        }
    }


    static String[] move(final String move) throws IllegalMoveException {
        int x = 2; // ascissa
        int y = 1; // ordinata
        final int xValue = 3;
        int xCheck; // sentinella dell'ascissa
        int yCheck; // sentinella dell'ordinata
        boolean blackTurn = Game.getBlackTurn();
        boolean isCapture;
        if (move.length() == PIECEMOVELENGTH) {
            isCapture = false;
        } else if ((move.length() == CAPTURELENGTH) && (move.substring(CHARPOS1, CHARPOS2).equals("x"))) {
            isCapture = true;
            x = xValue;
            y = 2;
        } else {
            throw new IllegalMoveException("Mossa non consentita per l'alfiere");
        }


        y = (int) move.charAt(y) - AINASCII;
        x = MAXROW - (((int) move.charAt(x)) - DIGIT0INASCII);
        if ((x < 0) || (x > OUTOFBOUND) || (y < 0) || (y > OUTOFBOUND)) {
            throw new IllegalMoveException("Mossa illegale; non rispetta i limiti della scacchiera");
        }
        if (Game.getCell(x, y).getPiece() != null) {
            //lancia eccezione se la cella di destinazione � occupata da alleato
            if (Game.getCell(x, y).getPiece().getColor() == (blackTurn ? 0 : 1)) {
                throw new IllegalMoveException("Mossa illegale; Non puoi spostarti sulla cella di un alleato");
            }

            //o se � una mossa di spostamento con cella di destinazione occupata da avversario
            else if (Game.getCell(x, y).getPiece().getColor() != (blackTurn ? 0 : 1) && !isCapture) {
                throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota");
            }

            //o se � una mossa di cattura con cella di destinazione vuota
        } else if (Game.getCell(x, y).getPiece() == null && isCapture) {
            throw new IllegalMoveException("Mossa illegale; La cella di destinazione e' vuota");
        }

        xCheck = x - 1;
        yCheck = y - 1;
        while (xCheck >= 0 && yCheck >= 0) { // controllo diagonale alta sinistra
            if ((Game.getCell(xCheck, yCheck).getPiece() instanceof Bishop)
                    && (Game.getCell(xCheck, yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
                return actualMove(isCapture, x, y, xCheck, yCheck);
            } else if (Game.getCell(xCheck, yCheck).getPiece() != null) {
                break;
            } else {
                xCheck--;
                yCheck--;
            }
        }
        xCheck = x - 1;
        yCheck = y + 1;
        while (xCheck >= 0 && yCheck < MAXCOL) { // controllo diagonale alta destra
            if ((Game.getCell(xCheck, yCheck).getPiece() instanceof Bishop)
                    && (Game.getCell(xCheck, yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
                return actualMove(isCapture, x, y, xCheck, yCheck);
            } else if (Game.getCell(xCheck, yCheck).getPiece() != null) {
                break;
            } else {
                xCheck--;
                yCheck++;
            }
        }
        xCheck = x + 1;
        yCheck = y - 1;
        while (xCheck < MAXROW && yCheck >= 0) { // controllo diagonale bassa sinistra
            if ((Game.getCell(xCheck, yCheck).getPiece() instanceof Bishop)
                    && (Game.getCell(xCheck, yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
                return actualMove(isCapture, x, y, xCheck, yCheck);
            } else if (Game.getCell(xCheck, yCheck).getPiece() != null) {
                break;
            } else {
                xCheck++;
                yCheck--;
            }
        }

        xCheck = x + 1;
        yCheck = y + 1;
        while (xCheck < MAXROW && yCheck < MAXCOL) { // controllo diagonale bassa destra
            if ((Game.getCell(xCheck, yCheck).getPiece() instanceof Bishop)
                    && (Game.getCell(xCheck, yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
                return actualMove(isCapture, x, y, xCheck, yCheck);
            } else if (Game.getCell(xCheck, yCheck).getPiece() != null) {
                break;
            } else {
                xCheck++;
                yCheck++;
            }
        }
        if (isCapture) {
            throw new IllegalMoveException(
                    "Mossa illegale; L'alfiere non puo' effettuare la cattura nella cella di destinazione data");
        } else {
            throw new IllegalMoveException("Mossa illegale; L'alfiere non puo' muoversi qui");
        }
    }

    private static String[] actualMove(final boolean isCapture, final int x, final int y, final int xCheck,
                                       final int yCheck)
            throws IllegalMoveException {
        //isCapture = true -> mossa di cattura
        //isCapture = false -> mossa di spostamento
        String[] pieces = new String[STRARRDIM]; //0 Donna, 2 cella di dest
        Piece target = null;
        if (isCapture) {
            //update: nell'originale viene fatto il cast a piece, qui lo tolgo
            target = Game.getCell(x, y).getPiece();
        }
        Bishop q = (Bishop) Game.getCell(xCheck, yCheck).getPiece();
        Game.getCell(xCheck, yCheck).setEmpty();
        Game.getCell(x, y).setPiece(q);
        if (King.isThreatened()) {
            Game.getCell(x, y).setPiece(target);
            Game.getCell(xCheck, yCheck).setPiece(q);
            throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
        } else {
            if (isCapture) {
                if (target.getColor() == 0) {
                    Game.addBlackCaptured(target.toString());
                } else {
                    Game.addWhiteCaptured(target.toString());
                }
                pieces[1] = target.toString();
            } else {
                pieces[1] = null;
            }
            pieces[0] = q.toString();
            pieces[2] = ((char) (y + AINASCII)) + "" + (MAXROW - x);
            return pieces;
        }
    }
}
