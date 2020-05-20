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

  String[] moveAPawn(String input) throws IllegalMoveException {
	  if (input.length() == 2) { 
		  return Pawn.move(input);
	  } else if (input.length() == 4) {
		  if (input.substring(1, 2).equals("x")) {
			  return Pawn.capture(input);
		  } throw new IllegalMoveException("Mossa non valida");
	  } else if (input.length() == 8) {
		  if ((input.substring(1, 2).toLowerCase().equals("x"))
				  && (input.substring(4, 8).toLowerCase().equals("e.p."))) {
			  return  Pawn.captureEnPassant(input);
		  } else throw new IllegalMoveException("Mossa non valida");
	  } else if (input.length() == 6) {
		  if ((input.substring(1, 2).toLowerCase().equals("x"))
				  && (input.substring(4, 6).toLowerCase().equals("ep"))) {
			  return Pawn.captureEnPassant(input);
		  } else throw new IllegalMoveException("Mossa non valida");
	  } else
		  throw new IllegalMoveException(
				  "Mossa illegale o comando inesistente; Riprova utilizzando un comando consentito o inserisci una mossa legale");
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

  String[] shortCastling() throws IllegalMoveException {
    String[] result = new String[2];
    if (blackTurn == true) {
      if ((board[7][4].getPiece() instanceof King) && (board[7][7].getPiece() instanceof Rook)) {
        // controllo che re e torre siano nella posizione corretta
        King k = (King) board[7][4].getPiece();
        Rook r = (Rook) board[7][7].getPiece();
        if ((k.getNumberOfMoves() == 0)
            && (r.getNumberOfMoves() == 0)) { // controllo che non siano stati
          // ancora mossi

          if ((board[7][5].getPiece() == null)
              && (board[7][6].getPiece() == null)) { // controllo se il
            // percorso e' libero
            if ((King.isThreatened(7, 4))
                || (King.isThreatened(7, 5))
                // controllo che il re non e', e non finisce sotto scacco durante la mossa
                || (King.isThreatened(7, 6))) {
              throw new IllegalMoveException(
                  "Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
            }
            k.incrementMoves();
            r.incrementMoves();
            board[7][6].setPiece(k);
            board[7][5].setPiece(r);
            board[7][4].setEmpty();
            board[7][7].setEmpty();
            movesDone.add("0-0");
            setBlackTurn();
            result[0] = "0-0";
            return result;
          } else {
            throw new IllegalMoveException("Mossa illegale; Il percorso non e' libero");
          }

        } else {
          throw new IllegalMoveException(
              "Mossa illegale; Il re o la torre sono gia' stati mossi in precedenza");
        }

      } else {
        throw new IllegalMoveException(
            "Mossa illegale; Impossibile effettuare arrocco corto, re e torre non sono nella posizione iniziale");
      }
    } else {
      if ((board[0][4].getPiece() instanceof King) && (board[0][7].getPiece() instanceof Rook)) {
        // controllo che re e torre siano nella posizione corretta
        King k = (King) board[0][4].getPiece();
        Rook r = (Rook) board[0][7].getPiece();
        if ((k.getNumberOfMoves() == 0)
            && (r.getNumberOfMoves() == 0)) { // controllo che non siano stati
          // ancora mossi

          if ((board[0][5].getPiece() == null) && (board[0][6].getPiece() == null)) {
            if ((King.isThreatened(0, 4))
                || (King.isThreatened(0, 5))
                || (King.isThreatened(0, 6))) { // controllo che il re non e', e non
              // finisce sotto scacco durante la
              // mossa
              throw new IllegalMoveException(
                  "Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
            }
            // controllo se il percorso e' libero
            k.incrementMoves();
            r.incrementMoves();
            board[0][6].setPiece(k);
            board[0][5].setPiece(r);
            board[0][4].setEmpty();
            board[0][7].setEmpty();
            movesDone.add("0-0");
            setNotBlackTurn();
            result[0] = "0-0";
            return result;
          } else {
            throw new IllegalMoveException("Mossa illegale; Il percorso non e' libero");
          }

        } else {
          throw new IllegalMoveException(
              "Mossa illegale; Il re o la torre sono gia'  stati mossi in precedenza");
        }

      } else {
        throw new IllegalMoveException(
            "Mossa illegale; Impossibile effettuare arrocco corto, re e torre non sono nella posizione iniziale");
      }
    }
  }

  String[] longCastling() throws IllegalMoveException {
    String[] result = new String[2];
    if (blackTurn == true) {
      if ((board[7][4].getPiece() instanceof King) && (board[7][0].getPiece() instanceof Rook)) {
        // controllo che re e torre siano nella posizione corretta
        King k = (King) board[7][4].getPiece();
        Rook r = (Rook) board[7][0].getPiece();
        if ((k.getNumberOfMoves() == 0)
            && (r.getNumberOfMoves() == 0)) { // controllo che non siano stati
          // ancora mossi

          if ((board[7][3].getPiece() == null) && (board[7][2].getPiece() == null)) {
            if ((King.isThreatened(7, 4))
                || (King.isThreatened(7, 3))
                // controllo che il re non e', e non finisce sotto scacco durante la mossa
                || (King.isThreatened(7, 2))) {
              throw new IllegalMoveException(
                  "Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
            }
            // controllo se il percorso e' libero
            k.incrementMoves();
            r.incrementMoves();
            board[7][2].setPiece(k);
            board[7][3].setPiece(r);
            board[7][4].setEmpty();
            board[7][0].setEmpty();
            movesDone.add("0-0-0");
            result[0] = "0-0-0";
            setBlackTurn();
            return result;
          } else {
            throw new IllegalMoveException("Mossa illegale; Il percorso non e' libero");
          }

        } else {
          throw new IllegalMoveException(
              "Mossa illegale; Il re o la torre sono gia' stati mossi in precedenza");
        }

      } else {
        throw new IllegalMoveException(
            "Mossa illegale; Impossibile effettuare arrocco lungo, re e torre non sono nella posizione iniziale");
      }
    } else {
      if ((board[0][4].getPiece() instanceof King) && (board[0][0].getPiece() instanceof Rook)) {
        // controllo che re e torre siano nella posizione corretta
        King k = (King) board[0][4].getPiece();
        Rook r = (Rook) board[0][0].getPiece();
        if ((k.getNumberOfMoves() == 0)
            && (r.getNumberOfMoves() == 0)) { // controllo che non siano stati
          // ancora mossi

          if ((board[0][3].getPiece() == null) && (board[0][2].getPiece() == null)) {
            if ((King.isThreatened(0, 4))
                || (King.isThreatened(0, 3))
                || (King.isThreatened(0, 2))) { // controllo che il re non e', e non
              // finisce sotto scacco durante la
              // mossa
              throw new IllegalMoveException(
                  "Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
            }
            // controllo se il percorso e' libero
            k.incrementMoves();
            r.incrementMoves();
            board[0][2].setPiece(k);
            board[0][3].setPiece(r);
            board[0][4].setEmpty();
            board[0][0].setEmpty();
            movesDone.add("0-0-0");
            result[0] = "0-0-0";
            setNotBlackTurn();
            return result;
          } else {
            throw new IllegalMoveException("Mossa illegale; Il percorso non e' libero");
          }

        } else {
          throw new IllegalMoveException(
              "Mossa illegale; Il re o la torre sono gia'  stati mossi in precedenza");
        }

      } else {
        throw new IllegalMoveException(
            "Mossa illegale; Impossibile effettuare arrocco lungo, re e torre non sono nella posizione iniziale");
      }
    }
  }

  static boolean getBlackTurn() {
    return blackTurn;
  }

  void changeTurn() {
	  setNotBlackTurn();
  }
  
  private static void setBlackTurn(){
	  blackTurn= false;
	  }
  
  private static void setNotBlackTurn(){
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
