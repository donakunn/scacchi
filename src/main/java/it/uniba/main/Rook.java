package it.uniba.main;

import static it.uniba.main.FinalPar.AINASCII;
import static it.uniba.main.FinalPar.AMBCAPTLENGTH;
import static it.uniba.main.FinalPar.CAPTURELENGTH;
import static it.uniba.main.FinalPar.CHARPOS1;
import static it.uniba.main.FinalPar.CHARPOS2;
import static it.uniba.main.FinalPar.DIGIT0INASCII;
import static it.uniba.main.FinalPar.MAXROW;
import static it.uniba.main.FinalPar.OUTOFBOUND;
import static it.uniba.main.FinalPar.PIECEMOVELENGTH;
import static it.uniba.main.FinalPar.STRARRDIM;

/**
 * <<entity>><br>
 * Rook class, implementing the abstract class {@link Piece}<br>
 *
 * @author Donato Lucente
 */
class Rook extends Piece {


    Rook(final int col) {

        this.color = col;
        if (col == 0) {
            this.pieceType = "\u265C"; // Torre nera
            nMoves = 0;

        } else {

            this.pieceType = "\u2656"; // Torre bianca
            nMoves = 0;

        }
    }


    void incrementMoves() {
        nMoves++;
    }

    int getNumberOfMoves() {
        return this.nMoves;
    }

    private static boolean isMovable(final int x, final int y, final int a, final int b) {
        int i = a;
        int j = b;

        if (x == a && y == b) {
            return false;
        }

        if (x == a) { // controllo orizzontale
            int dx = (y < b) ? 1 : -1;

            for (j = y + dx; j != b; j += dx) {
                if (Game.getCell(x, j).getPiece() != null) {
                    break;
                }
            }
        } else if (y == b) { // in verticale
            int dy = (x < a) ? 1 : -1;

            for (i = x + dy; i != a; i += dy) {
                if (Game.getCell(i, y).getPiece() != null) {
                    break;
                }
            }
        } else { // Non valido
            return false;
        }
        if (Game.getCell(i, j).getPiece() == null
                || Game.getCell(i, j).getPiece().getColor() != (Game.getBlackTurn() ? 0 : 1)) {
            return true;
        }
        return false;
    }

    static String[] move(final String move) throws IllegalMoveException {
        int count = 0;
        int xT1 = -1;
        int yT1 = -1;
        int xT2 = -1;
        int yT2 = -1;
        int xTarget = -1;
        int yTarget = -1;
        final int maxCount = 3;
        boolean isCapture = false;
        boolean blackTurn = Game.getBlackTurn();

        int a = MAXROW - (((int) move.charAt(move.length() - 1)) - DIGIT0INASCII);
        int b = (int) move.charAt(move.length() - 2) - AINASCII;
        if ((a < 0) || (a > OUTOFBOUND) || (b < 0) || (b > OUTOFBOUND)) {
            throw new IllegalMoveException("Mossa illegale; non rispetta i limiti della scacchiera");
        }

        if ((move.length() == CAPTURELENGTH && move.charAt(CHARPOS1) == 'x')
                || (move.length() == AMBCAPTLENGTH && move.charAt(CHARPOS2) == 'x')) {
            isCapture = true;
        }

        if (Game.getCell(a, b).getPiece() != null) {
            //lancia eccezione se la cella di destinazione � occupata da alleato
            if (Game.getCell(a, b).getPiece().getColor() == (blackTurn ? 0 : 1)) {
                throw new IllegalMoveException("Mossa illegale; Non puoi spostarti sulla cella di un alleato");


                //o se � una mossa di spostamento con cella di destinazione occupata da avversario
            } else if (Game.getCell(a, b).getPiece().getColor() != (blackTurn ? 0 : 1) && !isCapture) {
                throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota");
            }

            //o se � una mossa di cattura con cella di destinazione vuota
        } else if (Game.getCell(a, b).getPiece() == null && isCapture) {
            throw new IllegalMoveException("Mossa illegale; La cella di destinazione e' vuota");
        }

        for (int i = 0; i <= OUTOFBOUND; i++) {
            for (int j = 0; j <= OUTOFBOUND; j++) {
                if (Game.getCell(i, j).getPiece() instanceof Rook
                        && Game.getCell(i, j).getPiece().getColor() == (blackTurn ? 0 : 1)) {
                    if (xT1 == -1) {
                        xT1 = i;
                        yT1 = j;
                    } else {
                        xT2 = i;
                        yT2 = j;
                    }
                }
            }
        }
        if (xT1 != -1 && yT1 != -1) {
            if (isMovable(xT1, yT1, a, b)) {
                count = count + 1;
            }
        }
        if (xT2 != -1 && yT2 != -1) {
            if (isMovable(xT2, yT2, a, b)) {
                count = count + 2;
            }
        }

        if (count == 0) {
            throw new IllegalMoveException("Nessuna torre puo' spostarsi in quella cella.");
        }

        if (count == 1) {
            xTarget = xT1;
            yTarget = yT1;
        } else if (count == 2) {
            xTarget = xT2;
            yTarget = yT2;
        } else if (count == maxCount) {
            if (move.length() == PIECEMOVELENGTH) {
                throw new IllegalMoveException(
                        "Mossa ambigua, devi specificare quale delle due torri "
                                + "muovere secondo la notazione algebrica.");
            }
            if (move.charAt(CHARPOS1) >= '1' && move.charAt(CHARPOS1) <= '8') {
                if (xT1 == xT2) {
                    throw new IllegalMoveException(
                            "Quando le due torri si trovano sulla stessa riga "
                                    + "e' necessario specificare la colonna!");
                }
                if (xT1 == (MAXROW - Integer.parseInt(move.substring(CHARPOS1, CHARPOS2)))) {
                    xTarget = xT1;
                    yTarget = yT1;
                } else if (xT2 == (MAXROW - Integer.parseInt(move.substring(CHARPOS1, CHARPOS2)))) {
                    xTarget = xT2;
                    yTarget = yT2;
                } else {
                    throw new IllegalMoveException(
                            "Nessuna torre appartenente alla riga di disambiguazione specificata.");
                }
            } else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
                if (yT1 == yT2) {
                    throw new IllegalMoveException(
                            "Quando le due torri si trovano sulla stessa "
                                    + "colonna e' necessario specificare la riga!");
                }
                if (yT1 == ((int) move.charAt(CHARPOS1) - AINASCII)) {
                    xTarget = xT1;
                    yTarget = yT1;
                } else if (yT2 == ((int) move.charAt(1) - AINASCII)) {
                    xTarget = xT2;
                    yTarget = yT2;
                } else {
                    throw new IllegalMoveException(
                            "Nessuna torre appartenente alla colonna"
                                    + " di disambiguazione specificata.");
                }
            } else {
                throw new IllegalMoveException(
                        "Mossa ambigua, devi specificare quale delle due torri "
                                + "muovere secondo la notazione algebrica.");
            }
        }

        return actualMove(isCapture, xTarget, yTarget, a, b);
    }

    private static String[] actualMove(final boolean isCapture, final int xC, final int yC, final int x, final int y)
            throws IllegalMoveException {
        String[] pieces = new String[STRARRDIM];
        Piece target = null;
        if (isCapture) {
            target = Game.getCell(x, y).getPiece();
        }
        Rook r = (Rook) Game.getCell(xC, yC).getPiece();
        Game.getCell(xC, yC).setEmpty();
        Game.getCell(x, y).setPiece(r);
        if (King.isThreatened()) {
            Game.getCell(x, y).setPiece(target);
            Game.getCell(xC, yC).setPiece(r);
            throw new IllegalMoveException("Mossa illegale; il Re � sotto scacco "
                    + "o ci finirebbe dopo questa mossa");
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
            r.incrementMoves();
            pieces[0] = r.toString();
            pieces[2] = ((char) (y + AINASCII)) + "" + (MAXROW - x);
            return pieces;
        }
    }
}
