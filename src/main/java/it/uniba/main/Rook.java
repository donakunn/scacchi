package it.uniba.main;

/**
 * <<entity>><br>
 * Rook class, implementing the abstract class {@link Piece}<br>
 *
 * @author Donato Lucente
 */
class Rook extends Piece {


    Rook(int col) {

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

    static private boolean isMovable(int x, int y, int a, int b) {
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
        if (Game.getCell(i, j).getPiece() == null ||
                Game.getCell(i, j).getPiece().getColor() != (Game.getBlackTurn() ? 0 : 1))
        // Return true
        {
            return true;
        } else {
            return false;
        }
    }

    static String[] move(String move) throws IllegalMoveException {
        int count = 0;
        int xT1 = -1;
        int yT1 = -1;
        int xT2 = -1;
        int yT2 = -1;
        int xTarget = -1;
        int yTarget = -1;
        boolean isCapture = false;
        boolean blackTurn = Game.getBlackTurn();

       int a = 8 - (((int) move.charAt(move.length()-1)) - 48);
		int b = (int) move.charAt(move.length() - 2) - 97;
		if ((a <0) || (a > 7) || (b <0) || (b > 7)) {
			throw new IllegalMoveException("Mossa illegale; non rispetta i limiti della scacchiera");
		}

        if ((move.length() == 4 && move.charAt(1) == 'x') || (move.length() == 5 && move.charAt(2) == 'x')) {
            isCapture = true;
        }

        if (Game.getCell(a, b).getPiece() != null) {
            //lancia eccezione se la cella di destinazione � occupata da alleato
            if (Game.getCell(a, b).getPiece().getColor() == (blackTurn ? 0 : 1)) {
                throw new IllegalMoveException("Mossa illegale; Non puoi spostarti sulla cella di un alleato");
            }

            //o se � una mossa di spostamento con cella di destinazione occupata da avversario
            else if (Game.getCell(a, b).getPiece().getColor() != (blackTurn ? 0 : 1) && !isCapture) {
                throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota");
            }

            //o se � una mossa di cattura con cella di destinazione vuota
        } else if (Game.getCell(a, b).getPiece() == null && isCapture) {
            throw new IllegalMoveException("Mossa illegale; La cella di destinazione e' vuota");
        }

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
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
        } else if (count == 3) {
            if (move.length() == 3) {
                throw new IllegalMoveException(
                        "Mossa ambigua, devi specificare quale delle due torri muovere secondo la notazione algebrica.");
            }
            if (move.charAt(1) >= '1' && move.charAt(1) <= '8') {
                if (xT1 == xT2) {
                    throw new IllegalMoveException(
                            "Quando le due torri si trovano sulla stessa riga e' necessario specificare la colonna!");
                }
                if (xT1 == (8 - Integer.parseInt(move.substring(1, 2)))) {
                    xTarget = xT1;
                    yTarget = yT1;
                } else if (xT2 == (8 - Integer.parseInt(move.substring(1, 2)))) {
                    xTarget = xT2;
                    yTarget = yT2;
                } 
            } else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
                if (yT1 == yT2) {
                    throw new IllegalMoveException(
                            "Quando le due torri si trovano sulla stessa colonna e' necessario specificare la riga!");
                }
                if (yT1 == ((int) move.charAt(1) - 97)) {
                    xTarget = xT1;
                    yTarget = yT1;
                } else if (yT2 == ((int) move.charAt(1) - 97)) {
                    xTarget = xT2;
                    yTarget = yT2;
                } 
            } 
        }

        return actualMove(isCapture, xTarget, yTarget, a, b);
    }

    static private String[] actualMove(boolean isCapture, int xC, int yC, int x, int y) throws IllegalMoveException {
        String[] pieces = new String[3];
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
            r.incrementMoves();
            pieces[0] = r.toString();
            pieces[2] = ((char) (y + 97)) + "" + (8 - x);
            return pieces;
        }
    }
}
