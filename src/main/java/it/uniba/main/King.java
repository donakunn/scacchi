package it.uniba.main;

import static it.uniba.main.FinalPar.AINASCII;
import static it.uniba.main.FinalPar.CAPTURELENGTH;
import static it.uniba.main.FinalPar.CASTLARRDIM;
import static it.uniba.main.FinalPar.CHARPOS1;
import static it.uniba.main.FinalPar.DIGIT0INASCII;
import static it.uniba.main.FinalPar.MAXCOL;
import static it.uniba.main.FinalPar.MAXROW;
import static it.uniba.main.FinalPar.OUTOFBOUND;
import static it.uniba.main.FinalPar.POS3;
import static it.uniba.main.FinalPar.POS4;
import static it.uniba.main.FinalPar.STARTBKINGX;
import static it.uniba.main.FinalPar.STARTBKINGY;
import static it.uniba.main.FinalPar.STARTWKINGX;
import static it.uniba.main.FinalPar.STARTWKINGY;
import static it.uniba.main.FinalPar.STRARRDIM;

/**
 * <<entity>><br>
 * <p>Titolo: King</p>
 * <p>Descrizione: La classe King implementa la classe astratta {@link Piece} e permette di utilizzare il Re
 * all'interno del gioco.</p>
 *
 * @author Donato Lucente
 * @author Filippo Iacobellis
 */
class King extends Piece {
    private static int[] coordBlackKing = new int[2]; // coordinate re nero, [0]=x [1]=y
    private static int[] coordWhiteKing = new int[2]; // coordinate re bianco, [0]=x [1]=y

    /**
     * E' il costruttore della classe, assegna al pezzo il colore e la relativa stringa Unicode. Inoltre, alla creazione 
     * dell'oggetto, inizializza le coordinate del Re.
     *
     * @param col: colore del pezzo.
     */

    King(final int col) {

        this.setColor(col);
        if (col == 0) {
        	this.setPieceType("\u265A"); // Re nero
            coordBlackKing = new int[] {STARTBKINGX, STARTBKINGY};
        } else {
        	this.setPieceType("\u2654"); // Re bianco
            coordWhiteKing = new int[] {STARTWKINGX, STARTWKINGY};
        }
    }

    static void setCoordBlackKing(final int[] newCoord) {
        coordBlackKing = newCoord;
    }


    /**
     * Chiama il metodo isThreatened sulla posizione del Re utilizzato dal giocatore che sta giocando in quel turno.
     *
     * @return true, se il Re del giocatore che sta giocando in quel turno e' sotto scacco; false, altrimenti.
     */

    static boolean isThreatened() {
        int x, y;
        if (Game.getBlackTurn()) {
            x = coordBlackKing[0];
            y = coordBlackKing[1];
        } else {
            x = coordWhiteKing[0];
            y = coordWhiteKing[1];
        }
        return isThreatened(x, y);
    }


    /**
     * Verifica che il Re, in posizione x-y, non sia sotto scacco.
     *
     * @param x: ascissa del Re.
     * @param y: ordinata del Re.
     * @return true, se il Re del giocatore che sta giocando in quel turno e' sotto scacco; false, altrimenti.
     */

    static boolean isThreatened(final int x, final int y) {
        return checkPawnThreat(x, y)
                || checkBishopThreat(x, y)
                || checkRookThreat(x, y)
                || checkKnightThreat(x, y);
    }

    /**
     * Controlla che il Re non sia minacciato dal Pedone.
     * 
     * @param x: ascissa da controllare.
     * @param y: ordinata da controllare.
     * @return true, se il Re del giocatore che sta giocando in quel turno e' minacciato; false altrimenti.
     */
   private static boolean checkPawnThreat(final int x, final int y) {
        boolean blackTurn = Game.getBlackTurn();
        Piece checkPiece;
        if (blackTurn) {
            if (x < OUTOFBOUND) {

                checkPiece = Game.getCell(x + 1, y - 1).getPiece();
                if (y > 0
                        && checkPiece instanceof Pawn
                        && checkPiece.getColor() == 1) {
                    return true;
                }
                checkPiece = Game.getCell(x + 1, y + 1).getPiece();
                if (y < OUTOFBOUND
                        && checkPiece instanceof Pawn
                        && checkPiece.getColor() == 1) {
                    return true;
                }
            }
        }
        if (!blackTurn) {
            if (x > 0) {
                checkPiece = Game.getCell(x - 1, y - 1).getPiece();
                if (y > 0
                        && checkPiece instanceof Pawn
                        && checkPiece.getColor() == 0) {
                    return true;
                }
                checkPiece = Game.getCell(x - 1, y + 1).getPiece();
                if (y < OUTOFBOUND
                        && checkPiece instanceof Pawn
                        && checkPiece.getColor() == 0) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Controlla che il Re non sia minacciato da un Alfiere e dalla Regina diagonalmente.
     * 
     * @param x: ascissa da controllare.
     * @param y: ordinata da controllare.
     * @return true, se il Re e' minacciato; false, altrimenti. 
     */
    private static boolean checkBishopThreat(final int x, final int y) {
        boolean blackTurn = Game.getBlackTurn();
        int blackTurnColor;
        Piece checkPiece;
        int i, j;

        if (blackTurn) {
            blackTurnColor = 0;
        } else {
            blackTurnColor = 1;
        }
        // up right
        i = 1;
        j = 1;
        while (x - i >= 0 && y + j <= OUTOFBOUND) {
            checkPiece = Game.getCell(x - i, y + j).getPiece();
            if (checkPiece == null) {
                i++;
                j++;
                continue;
            }
            if (checkPiece.getColor() == blackTurnColor
                    || checkPiece instanceof Pawn) {
                break;
            }

            if (checkPiece instanceof Bishop
                    || checkPiece instanceof Queen) {
                return true;
            }
            i++;
            j++;
        }
        // down right
        i = 1;
        j = 1;
        while (x + i <= OUTOFBOUND && y + j <= OUTOFBOUND) {
            checkPiece = Game.getCell(x + i, y + j).getPiece();
            if (checkPiece == null) {
                i++;
                j++;
                continue;
            }
            if (checkPiece.getColor() == blackTurnColor
                    || checkPiece instanceof Pawn) {
                break;
            }
            if (checkPiece instanceof Bishop
                    || checkPiece instanceof Queen) {
                return true;
            }
            i++;
            j++;
        }
        // down left
        i = 1;
        j = 1;
        while (x + i <= OUTOFBOUND && y - j >= 0) {
            checkPiece = Game.getCell(x + i, y - j).getPiece();
            if (checkPiece == null) {
                i++;
                j++;
                continue;
            }
            if (checkPiece.getColor() == blackTurnColor
                    || checkPiece instanceof Pawn) {
                break;
            }
            if (checkPiece instanceof Bishop
                    || checkPiece instanceof Queen) {
                return true;
            }
            i++;
            j++;
        }
        // up left
        i = 1;
        j = 1;
        while (x - i >= 0 && y - j >= 0) {
            checkPiece = Game.getCell(x - i, y - j).getPiece();
            if (checkPiece == null) {
                i++;
                j++;
                continue;
            }
            if (checkPiece.getColor() == blackTurnColor
                    || checkPiece instanceof Pawn) {
                break;
            }
            if (checkPiece instanceof Bishop
                    || checkPiece instanceof Queen) {
                return true;
            }
            i++;
            j++;
        }
        return false;
    }
    
    /**
     * Controlla che il Re non sia minacciato da una Torre e dalla Regina orizzontalmente e verticalmente.
     * 
     * @param x: ascissa da controllare.
     * @param y: ordinata da controllare.
     * @return true, se il Re e' minacciato; false, altrimenti.
     */

    private static boolean checkRookThreat(final int x, final int y) {
        boolean blackTurn = Game.getBlackTurn();
        Piece checkPiece;
        int i, j;
        int blackTurnColor;

        if (blackTurn) {
            blackTurnColor = 0;
        } else {
            blackTurnColor = 1;
        }
        // right
        j = 1;
        while (y + j <= OUTOFBOUND) {
            checkPiece = Game.getCell(x, y + j).getPiece();
            if (checkPiece == null) {
                j++;
                continue;
            }
            if (checkPiece.getColor() == blackTurnColor
                    || checkPiece instanceof Pawn) {
                break;
            }
            if (checkPiece instanceof Rook || checkPiece instanceof Queen) {
                return true;
            }
            j++;
        }
        // down
        i = 1;
        while (x + i <= OUTOFBOUND) {
            checkPiece = Game.getCell(x + i, y).getPiece();
            if (checkPiece == null) {
                i++;
                continue;
            }
            if (checkPiece.getColor() == blackTurnColor
                    || checkPiece instanceof Pawn) {
                break;
            }
            if (checkPiece instanceof Rook || checkPiece instanceof Queen) {
                return true;
            }
            i++;
        }
        // left
        j = 1;
        while (y - j >= 0) {
            checkPiece = Game.getCell(x, y - j).getPiece();
            if (checkPiece == null) {
                j++;
                continue;
            }
            if (checkPiece.getColor() == blackTurnColor
                    || checkPiece instanceof Pawn) {
                break;
            }
            if (checkPiece instanceof Rook || checkPiece instanceof Queen) {
                return true;
            }
            j++;
        }
        // up
        i = 1;
        while (x - i >= 0) {
            checkPiece = Game.getCell(x - i, y).getPiece();
            if (checkPiece == null) {
                i++;
                continue;
            }
            if (checkPiece.getColor() == blackTurnColor
                    || checkPiece instanceof Pawn) {
                break;
            }
            if (checkPiece instanceof Rook || checkPiece instanceof Queen) {
                return true;
            }
            i++;
        }
        return false;
    }  
  
    /**
     * Controlla che il Re non sia minacciato da un Cavallo.
     * 
     * @param x: ascissa da controllare.
     * @param y: ordinata da controllare.
     * @return true, se il Re e' minacciato; false altrimenti.
     */

    private static boolean checkKnightThreat(final int x, final int y) {
        boolean blackTurn = Game.getBlackTurn();
        int blackTurnColor;

        if (blackTurn) {
            blackTurnColor = 0;
        } else {
            blackTurnColor = 1;
        }
        // knight
        if (y + 2 <= OUTOFBOUND) {
            if (x - 1 >= 0
                    && Game.getCell(x - 1, y + 2).getPiece() instanceof Knight
                    && Game.getCell(x - 1, y + 2).getPiece().getColor() != blackTurnColor) {
                return true;
            }
            if (x + 1 <= OUTOFBOUND
                    && Game.getCell(x + 1, y + 2).getPiece() instanceof Knight
                    && Game.getCell(x + 1, y + 2).getPiece().getColor() != blackTurnColor) {
                return true;
            }
        }
        if (y + 1 <= OUTOFBOUND) {
            if (x - 2 >= 0
                    && Game.getCell(x - 2, y + 1).getPiece() instanceof Knight
                    && Game.getCell(x - 2, y + 1).getPiece().getColor() != blackTurnColor) {
                return true;
            }
            if (x + 2 <= OUTOFBOUND
                    && Game.getCell(x + 2, y + 1).getPiece() instanceof Knight
                    && Game.getCell(x + 2, y + 1).getPiece().getColor() != blackTurnColor) {
                return true;
            }
        }
        if (y - 1 >= 0) {
            if (x - 2 >= 0
                    && Game.getCell(x - 2, y - 1).getPiece() instanceof Knight
                    && Game.getCell(x - 2, y - 1).getPiece().getColor() != blackTurnColor) {
                return true;
            }
            if (x + 2 <= OUTOFBOUND
                    && Game.getCell(x + 2, y - 1).getPiece() instanceof Knight
                    && Game.getCell(x + 2, y - 1).getPiece().getColor() != blackTurnColor) {
                return true;
            }
        }
        if (y - 2 >= 0) {
            if (x - 1 >= 0
                    && Game.getCell(x - 1, y - 2).getPiece() instanceof Knight
                    && Game.getCell(x - 1, y - 2).getPiece().getColor() != blackTurnColor) {
                return true;
            }
            if (x + 1 <= OUTOFBOUND
                    && Game.getCell(x + 1, y - 2).getPiece() instanceof Knight
                    && Game.getCell(x + 1, y - 2).getPiece().getColor() != blackTurnColor) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permette di muovere il Re all'interno della scacchiera e comprende anche la possibilita' di effettuare una cattura.
     *
     * @param move: mossa specificata dall'utente.
     * @return array che contiene il Re che effettua la cattura, il pezzo catturato e la casella di destinazione.
     * @throws IllegalMoveException
     */

    static String[] move(final String move) throws IllegalMoveException {
        int x = 2;
        int y = 1;
        final int xInCapture = 3;
        boolean blackTurn = Game.getBlackTurn();
        int blackTurnColor;
        // pezzi da ritornare a fine esecuzione
        String[] printOut = new String[STRARRDIM];

        if (blackTurn) {
            blackTurnColor = 0;
        } else {
            blackTurnColor = 1;
        }

        if (move.length() == CAPTURELENGTH) {
            x = xInCapture;
            y = 2;
        }
        //cella di destinazione da ritornare, parti da colonna specificata e tagli fino alla fine della stringa
        printOut[2] = move.substring(y);
        y = (int) move.charAt(y) - AINASCII;

        x = MAXROW - (((int) move.charAt(x)) - DIGIT0INASCII);
        if ((x < 0) || (x > OUTOFBOUND) || (y < 0) || (y > OUTOFBOUND)) {
            throw new IllegalMoveException("Mossa illegale; non rispetta i limiti della scacchiera");
        }


        if (Game.getCell(x, y).getPiece() != null
                && Game.getCell(x, y).getPiece().getColor() == blackTurnColor) {
            throw new IllegalMoveException("Non puoi spostarti sulla cella di un alleato.");
        }
        int xK = -1;
        int yK = -1;

        for (int i = 0; i < MAXROW; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                if (Game.getCell(i, j).getPiece() instanceof King
                        && Game.getCell(i, j).getPiece().getColor() == blackTurnColor) {
                    xK = i;
                    yK = j;
                    break;
                }
            }
        }
        if (Math.abs(x - xK) > 1 || Math.abs(y - yK) > 1) {
            throw new IllegalMoveException("Il Re non puo' muoversi in quella cella");
        }
        if (isThreatened(x, y)) {
            throw new IllegalMoveException("Mossa illegale, metterebbe il Re sotto scacco");
        }

        // null valore standard perche' non sappiamo se e' una cattura o meno
        printOut[0] = Game.getCell(xK, yK).getPiece().toString();
        if (Game.getCell(x, y).getPiece() == null) {
            if (move.charAt(CHARPOS1) == 'x') {
                throw new IllegalMoveException(
                        "Mossa illegale, non c'e' nessun pezzo da catturare nella cella di arrivo");
            }
        } else {
            if (move.charAt(CHARPOS1) != 'x') {
                throw new IllegalMoveException(
                        "Mossa illegale, devi specificare la cattura come da notazione algebrica");
            }
            if (blackTurn) {
                Game.addBlackCaptured(Game.getCell(x, y).getPiece().toString());
            } else {
                Game.addWhiteCaptured(Game.getCell(x, y).getPiece().toString());
            }
            printOut[1] = Game.getCell(x, y).getPiece().toString();
        }
        Game.getCell(x, y).setPiece(Game.getCell(xK, yK).getPiece());
        ((King) Game.getCell(x, y).getPiece()).incrementMoves();
        Game.getCell(xK, yK).setEmpty();
        // imposta le nuove coordinate del king
        if (blackTurn) {
            coordBlackKing[0] = x;
            coordBlackKing[1] = y;
        } else {
            coordWhiteKing[0] = x;
            coordWhiteKing[1] = y;
        }
        return printOut;
    }

    /**
     * Determina se e' possibile effettuare l'arrocco o meno.
     *
     * @param isLong: indica se l'arrocco e' lungo o corto.
     * @return array contenente la stringa che determina il tipo di arrocco.
     * @throws IllegalMoveException
     */

    static String[] castling(final boolean isLong) throws IllegalMoveException {
        //isLong = true -> si tratta di longCastling

        String[] result = new String[CASTLARRDIM];

        //coordinate del re da muovere
        int xK, yK;

        //ordinata della torre da muovere, l'ascissa e' uguale
        int yR;

        //moltiplicatore, serve per raggiungere le celle in mezzo all'arrocco
        //per arrocco lungo bisogna spostarsi a sinistra e viceversa
        int indexMultiplier;

        if (isLong) {
            indexMultiplier = -1;
        } else {
            indexMultiplier = +1;
        }

        boolean blackTurn = Game.getBlackTurn();
        if (blackTurn) {
            xK = coordBlackKing[0];
            yK = coordBlackKing[1];
        } else {
            xK = coordWhiteKing[0];
            yK = coordWhiteKing[1];
        }

        if (isLong) {
            //arrocco lungo, torre sempre in posizione yK-4
            yR = yK - POS4;
        } else {
            //arrocco corto, torre sempre in posizione yK+3
            yR = yK + POS3;
        }

        if (!(Game.getCell(xK, yK).getPiece() instanceof King) || !(Game.getCell(xK, yR).getPiece() instanceof Rook)) {
            throw new IllegalMoveException(
                    "Mossa illegale; Impossibile effettuare arrocco lungo, "
                            + "re o torre non sono nella posizione iniziale");
        }
        // controllo che re e torre siano nella posizione corretta
        King k = (King) Game.getCell(xK, yK).getPiece();
        Rook r = (Rook) Game.getCell(xK, yR).getPiece();
        // controllo che non siano stati ancora mossi
        if ((k.getnMoves() != 0)
                || (r.getnMoves() != 0)) {
            throw new IllegalMoveException(
                    "Mossa illegale; Il re o la torre "
                            + "sono gia' stati mossi in precedenza");
        }

        if ((Game.getCell(xK, yK + (indexMultiplier)).getPiece() != null)
                || (Game.getCell(xK, yK + (indexMultiplier * 2)).getPiece() != null)
                || ((Game.getCell(xK, yK - POS3).getPiece() != null) && isLong)) {
            throw new IllegalMoveException("Mossa illegale; Il percorso non e' libero");
        }
        if ((King.isThreatened(xK, yK))
                // controllo che il re non e', e non finisce sotto scacco durante la mossa
                || (King.isThreatened(xK, yK + (indexMultiplier)))
                || (King.isThreatened(xK, yK + (indexMultiplier * 2)))
        ) {
            throw new IllegalMoveException(
                    "Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
        }
        // controllo se il percorso e' libero
        k.incrementMoves();
        r.incrementMoves();
        Game.getCell(xK, yK + (indexMultiplier * 2)).setPiece(k);
        Game.getCell(xK, yK + (indexMultiplier)).setPiece(r);
        Game.getCell(xK, yK).setEmpty();
        Game.getCell(xK, yR).setEmpty();
        if (isLong) {
            result[0] = "0-0-0";
        } else {
            result[0] = "0-0";
        }
        return result;
    }

}

