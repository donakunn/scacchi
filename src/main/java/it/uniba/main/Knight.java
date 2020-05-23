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
 * <p>Titolo: Knight</p>
 * <p>Descrizione: La classe Knight implementa la classe astratta {@link Piece} e permette di utilizzare
 * il Cavallo all'interno del gioco.
 * 
 * @author Donato Lucente
 */
class Knight extends Piece {

	/**
	 * E' il costruttore della classe, assegna al pezzo il colore e la relativa stringa Unicode.
	 * @param col: colore del pezzo.
	 */
    Knight(final int col) {
        this.setColor(col);
        if (col == 0) {
        	this.setPieceType("\u265E"); // Cavallo nero
        } else {
        	this.setPieceType("\u2658"); // Cavallo bianco
        }
    }

    /**
     * Verifica se e' possibile effettuare la mossa.
     * 
     * @param x: ascissa della casella di partenza.
     * @param y: ordinata della casella di partenza.
     * @param a: ascissa della casella di arrivo.
     * @param b: ordinata della casella di arrivo.
     * @return true, se e' possibile effettuare la mossa; false, altrimenti.
     */
    private static boolean isMovable(final int x, final int y, final int a, final int b) {
        if ((Math.abs(x - a) == 1 && Math.abs(y - b) == 2)
                || (Math.abs(y - b) == 1 && Math.abs(x - a) == 2)) {
            return true;
        }
        return false;
    }

    /**
     * Effettua tutti i controlli che servono per poter effettuare la mossa o la cattura.
     * 
     * @param move: mossa specificata dell'utente.
     * @return array contenente il Cavallo che effettua la mossa o la cattura convertito a stringa, la mossa effettuata e,
     * se si tratta di una cattura, contiene anche il pezzo catturato convertito a stringa.
     * @throws IllegalMoveException
     */
    static String[] move(final String move) throws IllegalMoveException {
        int count = 0;
        int xC1 = -1;
        int yC1 = -1;
        int xC2 = -1;
        int yC2 = -1;
        //coordinate del cavallo scelto da muovere
        int xTarget = -1;
        int yTarget = -1;
        final int maxCount = 3;
        boolean isCapture = false;
        boolean blackTurn = Game.getBlackTurn();
        int blackTurnColor;

        if (blackTurn) {
            blackTurnColor = 0;
        } else {
            blackTurnColor = 1;
        }

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
            if (Game.getCell(a, b).getPiece().getColor() == blackTurnColor) {
                throw new IllegalMoveException("Mossa illegale; Non puoi spostarti sulla cella di un alleato");


                //o se � una mossa di spostamento con cella di destinazione occupata da avversario
            } else if (Game.getCell(a, b).getPiece().getColor() != blackTurnColor && !isCapture) {
                throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota");
            }

            //o se � una mossa di cattura con cella di destinazione vuota
        } else if (Game.getCell(a, b).getPiece() == null && isCapture) {
            throw new IllegalMoveException("Mossa illegale; La cella di destinazione e' vuota");
        }

        //provo a trovare entrambi i cavalli nella scacchiera
        for (int i = 0; i <= OUTOFBOUND; i++) {
            for (int j = 0; j <= OUTOFBOUND; j++) {
                if (Game.getCell(i, j).getPiece() instanceof Knight
                        && Game.getCell(i, j).getPiece().getColor() == blackTurnColor) {
                    if (xC1 == -1) {
                        xC1 = i;
                        yC1 = j;
                    } else {
                        xC2 = i;
                        yC2 = j;
                    }
                }
            }
        }
        if (xC1 != -1 && yC1 != -1) {
            if (isMovable(xC1, yC1, a, b)) {
                count = count + 1;
            }
        }
        if (xC2 != -1 && yC2 != -1) {
            if (isMovable(xC2, yC2, a, b)) {
                count = count + 2;
            }
        }
        if (count == 0) {
            throw new IllegalMoveException("Nessun cavallo puo' spostarsi in quella cella.");
        }

        if (count == 1) {
            xTarget = xC1;
            yTarget = yC1;
        } else if (count == 2) {
            xTarget = xC2;
            yTarget = yC2;
        } else if (count == maxCount) {
            if (move.length() == PIECEMOVELENGTH) {
                throw new IllegalMoveException(
                        "Mossa ambigua, devi specificare quale dei due cavalli "
                                + "muovere secondo la notazione algebrica.");
            }
            if (move.charAt(CHARPOS1) >= '1' && move.charAt(CHARPOS1) <= '8') {
                if (xC1 == xC2) {
                    throw new IllegalMoveException(
                            "Quando i due cavalli si trovano sulla stessa riga e' necessario specificare la colonna!");
                }
                if (xC1 == (MAXROW - Integer.parseInt(move.substring(CHARPOS1, CHARPOS2)))) {
                    xTarget = xC1;
                    yTarget = yC1;
                } else if (xC2 == (MAXROW - Integer.parseInt(move.substring(CHARPOS1, CHARPOS2)))) {
                    xTarget = xC2;
                    yTarget = yC2;
                } else {
                    throw new IllegalMoveException(
                            "Nessun cavallo appartenente alla riga di disambiguazione specificata.");
                }
            } else if (move.charAt(CHARPOS1) >= 'a' && move.charAt(CHARPOS1) <= 'h') {
                if (yC1 == yC2) {
                    throw new IllegalMoveException(
                            "Quando i due cavalli si trovano sulla stessa colonna e' necessario specificare la riga!");
                }
                if (yC1 == ((int) move.charAt(CHARPOS1) - AINASCII)) {
                    xTarget = xC1;
                    yTarget = yC1;
                } else if (yC2 == ((int) move.charAt(CHARPOS1) - AINASCII)) {
                    xTarget = xC2;
                    yTarget = yC2;
                } else {
                    throw new IllegalMoveException(
                            "Nessun cavallo appartenente alla colonna di disambiguazione specificata.");
                }
            } else {
                throw new IllegalMoveException(
                        "Mossa ambigua, devi specificare quale dei due cavalli"
                                + " muovere secondo la notazione algebrica.");
            }
        }

        return actualMove(isCapture, xTarget, yTarget, a, b);
    }

    /**
     * Permette di effettuare la mossa.
     * 
     * @param isCapture: verifica se si tratta di una mossa o una cattura.
     * @param xC: ascissa di partenza del Cavallo.
     * @param yC: ordinata di partenza del Cavallo.
     * @param x: ascissa di arrivo del Cavallo.
     * @param y: ordinata di arrivo del Cavallo.
     * @return array contenente il Cavallo che effettua la mossa o la cattura convertito a stringa, la mossa effettuata e,
     * se si tratta di una cattura, contiene anche il pezzo catturato convertito a stringa.
     * @throws IllegalMoveException
     */
    private static String[] actualMove(final boolean isCapture, final int xC, final int yC, final int x, final int y)
            throws IllegalMoveException {
        String[] pieces = new String[STRARRDIM];
        Piece target = null;
        if (isCapture) {
            target = Game.getCell(x, y).getPiece();
        }
        Knight k = (Knight) Game.getCell(xC, yC).getPiece();
        Game.getCell(xC, yC).setEmpty();
        Game.getCell(x, y).setPiece(k);
        if (King.isThreatened()) {
            Game.getCell(x, y).setPiece(target);
            Game.getCell(xC, yC).setPiece(k);
            throw new IllegalMoveException("Mossa illegale; il Re � sotto scacco o ci finirebbe dopo questa mossa");
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
            pieces[0] = k.toString();
            pieces[2] = ((char) (y + AINASCII)) + "" + (MAXROW - x);
            return pieces;
        }
    }

}
