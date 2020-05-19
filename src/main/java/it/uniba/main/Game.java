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
    int x; // ascissa
    int y; // ordinata
    int vCheck; // sentinella dell'ascissa
    int hCheck; // sentinella dell'ordinata
    Queen q;
    String[] pieceAndCell = new String[3]; //0 Donna, 2 cella di dest

    y = (int) move.charAt(1) - 97;
    x = 8 - Integer.parseInt(move.substring(2, 3));
    
      if (board[x][y].getPiece() == null) {
        vCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
        while (vCheck < 8) {
          if ((board[vCheck][y].getPiece() instanceof Queen)
              && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][y].getPiece();
            board[vCheck][y].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setEmpty();
              board[vCheck][y].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");

            } else {
              pieceAndCell[0] = q.toString();
              pieceAndCell[2] = move.substring(1, 3);
              return pieceAndCell;
            }

          } else if (board[vCheck][y].getPiece() != null) {
            break;
          } else {
            vCheck++;
          }
        }
        vCheck = x - 1;
        while (vCheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
          if ((board[vCheck][y].getPiece() instanceof Queen)
              && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][y].getPiece();
            board[vCheck][y].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setEmpty();
              board[vCheck][y].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              pieceAndCell[0] = q.toString();
              pieceAndCell[2] = move.substring(1, 3);
              return pieceAndCell;
            }
          } else if (board[vCheck][y].getPiece() != null) {
            break;
          } else {
            vCheck--;
          }
        }
        hCheck = y + 1; // controllo in orizzontale a destra
        while (hCheck < 8) {
          if ((board[x][hCheck].getPiece() instanceof Queen)
              && (board[x][hCheck].getPiece().getColor() == (blackTurn ? 0 : 1))) {
            q = (Queen) board[x][hCheck].getPiece();
            board[x][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setEmpty();
              board[x][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
            
              pieceAndCell[0] = q.toString();
              pieceAndCell[2] = move.substring(1, 3);
              return pieceAndCell;
            }
          } else if (board[x][hCheck].getPiece() != null) {
            break;
          } else {
            hCheck++;
          }
        }
        hCheck = y - 1;
        while (hCheck >= 0) { // controllo in orizzontale a sinistra
          if ((board[x][hCheck].getPiece() instanceof Queen)
              && (board[x][hCheck].getPiece().getColor() == (blackTurn ? 0 : 1))) {
            q = (Queen) board[x][hCheck].getPiece();
            board[x][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setEmpty();
              board[x][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
           
              pieceAndCell[0] = q.toString();
              pieceAndCell[2] = move.substring(1, 3);
              return pieceAndCell;
            }
          } else if (board[x][hCheck].getPiece() != null) {
            break;
          } else {
            hCheck--;
          }
        }
        vCheck = x - 1;
        hCheck = y - 1;
        while (vCheck >= 0 && hCheck >= 0) { // controllo diagonale alta sinistra
          if ((board[vCheck][hCheck].getPiece() instanceof Queen)
              && (board[vCheck][hCheck].getPiece().getColor() == (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][hCheck].getPiece();
            board[vCheck][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setEmpty();
              board[vCheck][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
           
              pieceAndCell[0] = q.toString();
              pieceAndCell[2] = move.substring(1, 3);
              return pieceAndCell;
            }
          } else if (board[vCheck][hCheck].getPiece() != null) {
            break;
          } else {
            vCheck--;
            hCheck--;
          }
        }
        vCheck = x - 1;
        hCheck = y + 1;
        while (vCheck >= 0 && hCheck < 8) { // controllo diagonale alta destra
          if ((board[vCheck][hCheck].getPiece() instanceof Queen)
              && (board[vCheck][hCheck].getPiece().getColor() == (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][hCheck].getPiece();
            board[vCheck][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setEmpty();
              board[vCheck][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
             
              pieceAndCell[0] = q.toString();
              pieceAndCell[2] = move.substring(1, 3);
              return pieceAndCell;
            }
          } else if (board[vCheck][hCheck].getPiece() != null) {
            break;
          } else {
            vCheck--;
            hCheck++;
          }
        }
        vCheck = x + 1;
        hCheck = y - 1;
        while (vCheck < 8 && hCheck >= 0) { // controllo diagonale bassa sinistra
          if ((board[vCheck][hCheck].getPiece() instanceof Queen)
              && (board[vCheck][hCheck].getPiece().getColor() == (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][hCheck].getPiece();
            board[vCheck][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setEmpty();
              board[vCheck][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
          
              pieceAndCell[0] = q.toString();
              pieceAndCell[2] = move.substring(1, 3);
              return pieceAndCell;
            }
          } else if (board[vCheck][hCheck].getPiece() != null) {
            break;
          } else {
            vCheck++;
            hCheck--;
          }
        }

        vCheck = x + 1;
        hCheck = y + 1;
        while (vCheck < 8 && hCheck < 8) { // controllo diagonale bassa destra
          if ((board[vCheck][hCheck].getPiece() instanceof Queen)
              && (board[vCheck][hCheck].getPiece().getColor() == (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][hCheck].getPiece();
            board[vCheck][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setEmpty();
              board[vCheck][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              
              pieceAndCell[0] = q.toString();
              pieceAndCell[2] = move.substring(1, 3);
              return pieceAndCell;
            }
          } else if (board[vCheck][hCheck].getPiece() != null) {
            break;
          } else {
            vCheck++;
            hCheck++;
          }
        }
        throw new IllegalMoveException("Mossa illegale; La donna non puo' muoversi qui");

      } else
        throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota");

  }

  String[] captureQueen(String move) throws IllegalMoveException {
    int x; // ascissa
    int y; // ordinata
    int vCheck; // sentinella dell'ascissa
    int hCheck; // sentinella dell'ordinata
    Queen q;
    Piece caught;
    String pieces[] = new String[3];
    
    y = (int) move.charAt(2) - 97;
    x = 8 - Integer.parseInt(move.substring(3, 4));
      if (board[x][y].getPiece() != null) {
        vCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
        while (vCheck < 8) {
          if ((board[vCheck][y].getPiece() instanceof Queen)
              && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))
              && (board[x][y].getPiece().getColor() != (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][y].getPiece();
            caught = (Piece) board[x][y].getPiece();
            pieces[0] = q.toString();
            pieces[1] = caught.toString();
            pieces[2] = move.substring(2, 4);
            board[vCheck][y].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setPiece(caught);
              board[vCheck][y].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              if (blackTurn) {
              BlacksCaptured.add(caught.toString());
              } else {
                WhitesCaptured.add(caught.toString());
              }
              return pieces;
            }

          } else if (board[vCheck][y].getPiece() != null) {
            break;
          } else {
            vCheck++;
          }
        }
        vCheck = x - 1;
        while (vCheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
          if ((board[vCheck][y].getPiece() instanceof Queen)
          && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))
          && (board[x][y].getPiece().getColor() != (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][y].getPiece();
            caught = (Piece) board[x][y].getPiece();
            pieces[0] = q.toString();
            pieces[1] = caught.toString();
            pieces[2] = move.substring(2, 4);
            board[vCheck][y].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setPiece(caught);
              board[vCheck][y].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              if (blackTurn) {
                BlacksCaptured.add(caught.toString());
                } else {
                  WhitesCaptured.add(caught.toString());
                }
            
              return pieces;
            }
          } else if (board[vCheck][y].getPiece() != null) {
            break;
          } else {
            vCheck--;
          }
        }
        hCheck = y + 1; // controllo in orizzontale a destra
        while (hCheck < 8) {
          if ((board[x][hCheck].getPiece() instanceof Queen)
          && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))
          && (board[x][y].getPiece().getColor() != (blackTurn ? 0 : 1))) {
            q = (Queen) board[x][hCheck].getPiece();
            caught = (Piece) board[x][y].getPiece();
            pieces[0] = q.toString();
            pieces[1] = caught.toString();
            pieces[2] = move.substring(2, 4);
            board[x][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setPiece(caught);
              board[x][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              if (blackTurn) {
                BlacksCaptured.add(caught.toString());
                } else {
                  WhitesCaptured.add(caught.toString());
                }
            
              return pieces;
            }
          } else if (board[x][hCheck].getPiece() != null) {
            break;
          } else {
            hCheck++;
          }
        }
        hCheck = y - 1;
        while (hCheck >= 0) { // controllo in orizzontale a sinistra
          if ((board[x][hCheck].getPiece() instanceof Queen)
          && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))
          && (board[x][y].getPiece().getColor() != (blackTurn ? 0 : 1))) {
            q = (Queen) board[x][hCheck].getPiece();
            caught = (Piece) board[x][y].getPiece();
            pieces[0] = q.toString();
            pieces[1] = caught.toString();
            pieces[2] = move.substring(2, 4);
            board[x][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setPiece(caught);
              board[x][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              if (blackTurn) {
                BlacksCaptured.add(caught.toString());
                } else {
                  WhitesCaptured.add(caught.toString());
                }
           
              return pieces;
            }
          } else if (board[x][hCheck].getPiece() != null) {
            break;
          } else {
            hCheck--;
          }
        }
        vCheck = x - 1;
        hCheck = y - 1;
        while (vCheck >= 0 && hCheck >= 0) { // controllo diagonale alta sinistra
          if ((board[vCheck][hCheck].getPiece() instanceof Queen)
          && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))
          && (board[x][y].getPiece().getColor() != (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][hCheck].getPiece();
            caught = (Piece) board[x][y].getPiece();
            pieces[0] = q.toString();
            pieces[1] = caught.toString();
            pieces[2] = move.substring(2, 4);
            board[vCheck][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setPiece(caught);
              board[vCheck][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              if (blackTurn) {
                BlacksCaptured.add(caught.toString());
                } else {
                  WhitesCaptured.add(caught.toString());
                }
              
              return pieces;
            }
          } else if (board[vCheck][hCheck].getPiece() != null) {
            break;
          } else {
            vCheck--;
            hCheck--;
          }
        }
        vCheck = x - 1;
        hCheck = y + 1;
        while (vCheck >= 0 && hCheck < 8) { // controllo diagonale alta destra
          if ((board[vCheck][hCheck].getPiece() instanceof Queen)
          && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))
          && (board[x][y].getPiece().getColor() != (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][hCheck].getPiece();
            caught = (Piece) board[x][y].getPiece();
            pieces[0] = q.toString();
            pieces[1] = caught.toString();
            pieces[2] = move.substring(2, 4);
            board[vCheck][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setPiece(caught);
              board[vCheck][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              if (blackTurn) {
                BlacksCaptured.add(caught.toString());
                } else {
                  WhitesCaptured.add(caught.toString());
                }
              
              return pieces;
            }
          } else if (board[vCheck][hCheck].getPiece() != null) {
            break;
          } else {
            vCheck--;
            hCheck++;
          }
        }
        vCheck = x + 1;
        hCheck = y - 1;
        while (vCheck < 8 && hCheck >= 0) { // controllo diagonale bassa sinistra
          if ((board[vCheck][hCheck].getPiece() instanceof Queen)
          && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))
          && (board[x][y].getPiece().getColor() != (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][hCheck].getPiece();
            caught = (Piece) board[x][y].getPiece();
            pieces[0] = q.toString();
            pieces[1] = caught.toString();
            pieces[2] = move.substring(2, 4);
            board[vCheck][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setPiece(caught);
              board[vCheck][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              if (blackTurn) {
                BlacksCaptured.add(caught.toString());
                } else {
                  WhitesCaptured.add(caught.toString());
                }
              
              return pieces;
            }
          } else if (board[vCheck][hCheck].getPiece() != null) {
            break;
          } else {
            vCheck++;
            hCheck--;
          }
        }

        vCheck = x + 1;
        hCheck = y + 1;
        while (vCheck < 8 && hCheck < 8) { // controllo diagonale bassa destra
          if ((board[vCheck][hCheck].getPiece() instanceof Queen)
          && (board[vCheck][y].getPiece().getColor() == (blackTurn ? 0 : 1))
          && (board[x][y].getPiece().getColor() != (blackTurn ? 0 : 1))) {
            q = (Queen) board[vCheck][hCheck].getPiece();
            caught = (Piece) board[x][y].getPiece();
            pieces[0] = q.toString();
            pieces[1] = caught.toString();
            pieces[2] = move.substring(2, 4);
            board[vCheck][hCheck].setEmpty();
            board[x][y].setPiece(q);
            if (King.isThreatened()) {
              board[x][y].setPiece(caught);
              board[vCheck][hCheck].setPiece(q);
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
            } else {
              if (blackTurn) {
                BlacksCaptured.add(caught.toString());
                } else {
                  WhitesCaptured.add(caught.toString());
                }
             
              return pieces;
            }
          } else if (board[vCheck][hCheck].getPiece() != null) {
            break;
          } else {
            vCheck++;
            hCheck++;
          }
        }
        throw new IllegalMoveException(

            "Mossa illegale; La donna non puo' effettuare la cattura nella cella di destinazione data");
      } else throw new IllegalMoveException("Mossa illegale; La cella di destinazione e' vuota");

  }

  String[] moveBishop(String move) throws IllegalMoveException {
    int x;
    int y;
    int xB;
    int yB;
    Bishop b;

    y = (int) move.charAt(1) - 97;
    x = 8 - Integer.parseInt(move.substring(2, 3));
    String[] piecesAndCell = new String[3];

      if (board[x][y].getPiece() == null) {
        xB = x - 1;
        yB = y - 1;
        while (xB >= 0 && yB >= 0) {
          if (board[xB][yB].getPiece() instanceof Bishop
              && board[xB][yB].getPiece().getColor() == (blackTurn ? 0 : 1)) {
            b = (Bishop) board[xB][yB].getPiece();
            board[xB][yB].setEmpty();
            board[x][y].setPiece(b);
            if (King.isThreatened()) {
              board[xB][yB].setPiece(b);
              board[x][y].setEmpty();
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");

            } else {
              movesDone.add(move);
              setBlackTurn();
              piecesAndCell[0] = b.toString();
              piecesAndCell[2] = move.substring(1, 3);
              return piecesAndCell;
            }
          } else if (board[xB][yB].getPiece() != null) {
            break;
          } else {
            xB--;
            yB--;
          }
        }
        xB = x - 1;
        yB = y + 1;
        while (xB >= 0 && yB < 8) {
          if (board[xB][yB].getPiece() instanceof Bishop
              && board[xB][yB].getPiece().getColor() == (blackTurn ? 0 : 1)) {
            b = (Bishop) board[xB][yB].getPiece();
            board[xB][yB].setEmpty();
            board[x][y].setPiece(b);
            if (King.isThreatened()) {
              board[xB][yB].setPiece(b);
              board[x][y].setEmpty();
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");

            } else {
              movesDone.add(move);
              setBlackTurn();
              piecesAndCell[0] = b.toString();
              piecesAndCell[2] = move.substring(1, 3);
              return piecesAndCell;
            }
          } else if (board[xB][yB].getPiece() != null) {
            break;
          } else {
            xB--;
            yB++;
          }
        }
        xB = x + 1;
        yB = y - 1;
        while (xB < 8 && yB >= 0) {
          if (board[xB][yB].getPiece() instanceof Bishop
              && board[xB][yB].getPiece().getColor() == (blackTurn ? 0 : 1)) {
            b = (Bishop) board[xB][yB].getPiece();
            board[xB][yB].setEmpty();
            board[x][y].setPiece(b);
            if (King.isThreatened()) {
              board[xB][yB].setPiece(b);
              board[x][y].setEmpty();
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");

            } else {
              movesDone.add(move);
              setBlackTurn();
              piecesAndCell[0] = b.toString();
              piecesAndCell[2] = move.substring(1, 3);
              return piecesAndCell;
            }
          } else if (board[xB][yB].getPiece() != null) {
            break;
          } else {
            xB++;
            yB--;
          }
        }
        xB = x + 1;
        yB = y + 1;
        while (xB < 8 && yB < 8) {
          if (board[xB][yB].getPiece() instanceof Bishop
              && board[xB][yB].getPiece().getColor() == (blackTurn ? 0 : 1)) {
            b = (Bishop) board[xB][yB].getPiece();
            board[xB][yB].setEmpty();
            board[x][y].setPiece(b);
            if (King.isThreatened()) {
              board[xB][yB].setPiece(b);
              board[x][y].setEmpty();
              throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");

            } else {
              movesDone.add(move);
              setBlackTurn();
              piecesAndCell[0] = b.toString();
              piecesAndCell[2] = move.substring(1, 3);
              return piecesAndCell;
            }
          } else if (board[xB][yB].getPiece() != null) {
            break;
          } else {
            xB++;
            yB++;
          }
        }
        throw new IllegalMoveException("Mossa illegale, l'alfiere non puo' muoversi qui");
        } else
        throw new IllegalMoveException("Mossa illegale, la cella di destinazione non e' vuota");
    }

  String[] captureBishop(String move) throws IllegalMoveException {
    int x;
    int y;
    int xb;
    int yb;
    Bishop b;
    Piece caught;
    String pieces[] = new String[3];

    y = (int) move.charAt(2) - 97;
    x = 8 - Integer.parseInt(move.substring(3, 4));
    if (board[x][y].getPiece() != null) {
      xb = x - 1;
      yb = y - 1;
      while (xb >= 0 && yb >= 0) {
        if ((board[xb][yb].getPiece() instanceof Bishop)
            && (board[xb][yb].getPiece().getColor() == (blackTurn ? 0 : 1))
            && (board[x][y].getPiece().getColor() == (blackTurn ? 0 : 1))) {
          b = (Bishop) board[xb][yb].getPiece();
          caught = board[x][y].getPiece();
          pieces[0] = b.toString();
          pieces[1] = caught.toString();
          pieces[2] = move.substring(2, 4);
          board[xb][yb].setEmpty();
          board[x][y].setPiece(b);
          if (King.isThreatened()) {
            board[x][y].setPiece(caught);
            board[xb][yb].setPiece(b);
            throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
          } else {
            if (blackTurn) {
              BlacksCaptured.add(caught.toString());
            } else {
              WhitesCaptured.add(caught.toString());
            }
            movesDone.add(move);
            setBlackTurn();
            return pieces;
          }
        } else if (board[xb][yb].getPiece() != null) {
          break;
        } else {
          xb--;
          yb--;
        }
      }
      xb = x - 1;
      yb = y + 1;
      while (xb >= 0 && yb < 8) {
        if ((board[xb][yb].getPiece() instanceof Bishop)
            && (board[xb][yb].getPiece().getColor() == (blackTurn ? 0 : 1))
            && (board[x][y].getPiece().getColor() == (blackTurn ? 0 : 1))) {
          b = (Bishop) board[xb][yb].getPiece();
          caught = board[x][y].getPiece();
          pieces[0] = b.toString();
          pieces[1] =caught.toString();
          pieces[2] = move.substring(2, 4);
          board[xb][yb].setEmpty();
          board[x][y].setPiece(b);
          if (King.isThreatened()) {
            board[x][y].setPiece(caught);
            board[xb][yb].setPiece(b);
            throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
          } else {
            if (blackTurn) {
              BlacksCaptured.add(caught.toString());
            } else {
              WhitesCaptured.add(caught.toString());
            }
            movesDone.add(move);
            setBlackTurn();
            return pieces;
          }
        } else if (board[xb][yb].getPiece() != null) {
          break;
        } else {
          xb--;
          yb++;
        }
      }
      xb = x + 1;
      yb = y - 1;
      while (xb < 8 && yb >= 0) {
        if ((board[xb][yb].getPiece() instanceof Bishop)
            && (board[xb][yb].getPiece().getColor() == (blackTurn ? 0 : 1))
            && (board[x][y].getPiece().getColor() == (blackTurn ? 0 : 1))) {
          b = (Bishop) board[xb][yb].getPiece();
          caught = board[x][y].getPiece();
          pieces[0] = b.toString();
          pieces[1] = caught.toString();
          pieces[2] = move.substring(2, 4);
          board[xb][yb].setEmpty();
          board[x][y].setPiece(b);
          if (King.isThreatened()) {
            board[x][y].setPiece(caught);
            board[xb][yb].setPiece(b);
            throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
          } else {
            if (blackTurn) {
              BlacksCaptured.add(caught.toString());
            } else {
              WhitesCaptured.add(caught.toString());
            }
            movesDone.add(move);
            setBlackTurn();
            return pieces;
          }
        } else if (board[xb][yb].getPiece() != null) {
          break;
        } else {
          xb++;
          yb--;
        }
      }

      xb = x + 1;
      yb = y + 1;
      while (xb < 8 && yb < 8) {
        if ((board[xb][yb].getPiece() instanceof Bishop)
            && (board[xb][yb].getPiece().getColor() == (blackTurn ? 0 : 1))
            && (board[x][y].getPiece().getColor() == (blackTurn ? 0 : 1))) {
          b = (Bishop) board[xb][yb].getPiece();
          caught = board[x][y].getPiece();
          pieces[0] = b.toString();
          pieces[1] =caught.toString();
          pieces[2] = move.substring(2, 4);
          board[xb][yb].setEmpty();
          board[x][y].setPiece(b);
          if (King.isThreatened()) {
            board[x][y].setPiece(caught);
            board[xb][yb].setPiece(b);
            throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
          } else {
            if (blackTurn) {
              BlacksCaptured.add(caught.toString());
            } else {
              WhitesCaptured.add(caught.toString());
            }
            movesDone.add(move);
            setBlackTurn();
            return pieces;
          }
        } else if (board[xb][yb].getPiece() != null) {
          break;
        } else {
          xb++;
          yb++;
        }
      }
      throw new IllegalMoveException("Mossa illegale, l'alfiere non puo' catturare qui");
    } else throw new IllegalMoveException("Mossa illegale, la cella di destinazione e' vuota");

  }
  
  boolean isMovableKnight(int x, int y, int a, int b) {
    if ((Math.abs(x - a) == 1 && Math.abs(y - b) == 2)
        || (Math.abs(y - b) == 1 && Math.abs(x - a) == 2)) {
      if (board[a][b].getPiece() == null
          || board[a][b].getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
    }
    return false;
  }

  String[] moveKnight(String move) throws IllegalMoveException {
    int count = 0;
    String[] printOut = new String[2];
    int xC1 = -1, yC1 = -1, xC2 = -1, yC2 = -1;
    int a = 8 - Integer.parseInt(move.substring(move.length() - 1));
    int b = (int) move.charAt(move.length() - 2) - 97;
    if (board[a][b].getPiece() != null
        && board[a][b].getPiece().getColor() != (blackTurn ? 1 : 0)) {
      throw new IllegalMoveException("Non puoi spostarti sulla cella di un alleato.");
    }
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 7; j++) {
        if (board[i][j].getPiece() instanceof Knight
            && board[i][j].getPiece().getColor() != (blackTurn ? 1 : 0)) {
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
      if (isMovableKnight(xC1, yC1, a, b)) {
        count = count + 1;
      }
    }
    if (xC2 != -1 && yC2 != -1) {
      if (isMovableKnight(xC2, yC2, a, b)) {
        count = count + 2;
      }
    }
    if (count == 0) {
      throw new IllegalMoveException("Nessun cavallo puo' spostarsi in quella cella.");
    }

    if (count == 1) {
      if (move.charAt(1) == 'x') {
        printOut[0] = board[a][b].getPiece().getType();
        printOut[1] = board[xC1][yC1].getPiece().getType();
        captureKnight(xC1, yC1, a, b, move);
        return printOut;
      } else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
        actualMoveKnight(xC1, yC1, a, b, move);
        printOut[1] = board[a][b].getPiece().getType();
        return printOut;
      } else {
        throw new IllegalMoveException("Mossa non riconosciuta.");
      }
    } else if (count == 2) {
      if (move.charAt(1) == 'x') {
        printOut[0] = board[a][b].getPiece().getType();
        printOut[1] = board[xC2][yC2].getPiece().getType();
        captureKnight(xC2, yC2, a, b, move);
        return printOut;
      } else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
        actualMoveKnight(xC2, yC2, a, b, move);
        printOut[1] = board[a][b].getPiece().getType();
        return printOut;
      } else {
        throw new IllegalMoveException("Mossa non riconosciuta.");
      }
    } else if (count == 3) {
      if (move.charAt(1) == 'x') {
        throw new IllegalMoveException(
            "Mossa ambigua, devi specificare quale dei due cavalli muovere secondo la notazione algebrica.");
      }
      if (move.length() == 3) {
        throw new IllegalMoveException(
            "Mossa ambigua, devi specificare quale dei due cavalli muovere secondo la notazione algebrica.");
      }

      int x, y;
      if (move.charAt(1) >= '1' && move.charAt(1) <= '8') {
        if (xC1 == xC2) {
          throw new IllegalMoveException(
              "Quando i due cavalli si trovano sulla stessa riga e' necessario specificare la colonna!");
        }
        if (xC1 == (8 - Integer.parseInt(move.substring(1, 2)))) {
          x = xC1;
          y = yC1;
        } else if (xC2 == (8 - Integer.parseInt(move.substring(1, 2)))) {
          x = xC2;
          y = yC2;
        } else {
          throw new IllegalMoveException(
              "Nessun cavallo appartenente alla riga di disambiguazione specificata.");
        }
        if (move.length() == 4) {
          actualMoveKnight(x, y, a, b, move);
          printOut[1] = board[a][b].getPiece().getType();
          return printOut;
        } else if (move.length() == 5) {
          printOut[0] = board[a][b].getPiece().getType();
          printOut[1] = board[x][y].getPiece().getType();
          captureKnight(x, y, a, b, move);
          return printOut;
        } else {
          throw new IllegalMoveException("Mossa non riconosciuta.");
        }
      } else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
        if (yC1 == yC2) {
          throw new IllegalMoveException(
              "Quando i due cavalli si trovano sulla stessa colonna e' necessario specificare la riga!");
        }
        if (yC1 == ((int) move.charAt(1) - 97)) {
          x = xC1;
          y = yC1;
        } else if (yC2 == ((int) move.charAt(1) - 97)) {
          x = xC2;
          y = yC2;
        } else {
          throw new IllegalMoveException(
              "Nessun cavallo appartenente alla colonna di disambiguazione specificata.");
        }
        if (move.length() == 4) {
          actualMoveKnight(x, y, a, b, move);
          printOut[1] = board[a][b].getPiece().getType();
          return printOut;
        } else if (move.length() == 5) {
          printOut[0] = board[a][b].getPiece().getType();
          printOut[1] = board[x][y].getPiece().getType();
          captureKnight(x, y, a, b, move);
          return printOut;
        } else {
          throw new IllegalMoveException("Mossa non riconosciuta.");
        }
      } else {
        throw new IllegalMoveException("Mossa non riconosciuta.");
      }
    }

    return printOut;
  }

  void actualMoveKnight(int xC, int yC, int x, int y, String move) throws IllegalMoveException {
    if (board[x][y].getPiece() != null
        && board[x][y].getPiece().getColor() == (blackTurn ? 1 : 0)) {
      throw new IllegalMoveException(
          "Mossa non valida, devi specificare la cattura come da notazione algebrica.");
    }
    if (blackTurn == true) {
    	 board[x][y].setPiece(board[xC][yC].getPiece());
    	 board[xC][yC].setEmpty();
      if (King.isThreatened()) {
    	  board[xC][yC].setPiece(board[x][y].getPiece());
    	  board[x][y].setEmpty();
        throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
      }
    } else {
    	 board[x][y].setPiece(board[xC][yC].getPiece());
    	 board[xC][yC].setEmpty();
      if (King.isThreatened()) {
    	  board[xC][yC].setPiece(board[x][y].getPiece());
    	  board[x][y].setEmpty();
        throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
      }
    }
    
    movesDone.add(move);
    setNotBlackTurn();
  }

  void captureKnight(int xC, int yC, int x, int y, String move) throws IllegalMoveException {
    if (board[x][y].getPiece() == null) {
      throw new IllegalMoveException("Mossa non valida, non c'e' nessun pezzo da catturare.");
    }
    if (board[x][y].getPiece().getColor() == 0) {
      WhitesCaptured.add(board[x][y].getPiece().toString());
    } else {
      BlacksCaptured.add(board[x][y].getPiece().toString());
    }
    board[x][y].setPiece(board[xC][yC].getPiece());
    board[xC][yC].setEmpty();
    movesDone.add(move);
    setNotBlackTurn();
  }

  boolean isMovableRook(int x, int y, int a, int b) {
    int i;

    if (x == a && y == b) return false;

    if (x == a) { // controllo orizzontale
      int dx = (y < b) ? 1 : -1;

      for (i = y + dx; i != b; i += dx) if (board[x][i].getPiece() != null) return false;
    } else if (y == b) { // in verticale
      int dy = (x < a) ? 1 : -1;

      for (i = x + dy; i != a; i += dy) if (board[i][y].getPiece() != null) return false;
    } else { // Non valido
      return false;
    }

    // Return true
    return true;
  }

  String[] moveRook(String move) throws IllegalMoveException {
	    int count = 0;
	    int xC1 = -1;
	    int yC1 = -1;
	    int xC2 = -1;
	    int yC2 = -1;
	    String[] piecesAndCell=new String[3];
	    
	    int a = 8 - Integer.parseInt(move.substring(move.length() - 1));
	    int b = (int) move.charAt(move.length() - 2) - 97;
	    
	    if (board[a][b].getPiece() != null
	        && board[a][b].getPiece().getColor() != (blackTurn ? 1 : 0)) {
	      throw new IllegalMoveException("Non puoi spostarti sulla cella di un alleato.");
	    }
	    
	    for (int i = 0; i <= 7; i++) {
	      for (int j = 0; j <= 7; j++) {
	        if (board[i][j].getPiece() instanceof Rook
	            && board[i][j].getPiece().getColor() != (blackTurn ? 1 : 0)) {
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
	      if (isMovableRook(xC1, yC1, a, b)) {
	        count = count + 1;
	      }
	    }
	    if (xC2 != -1 && yC2 != -1) {
	      if (isMovableRook(xC2, yC2, a, b)) {
	        count = count + 2;
	      }
	    }

	    if (count == 0) {
	      throw new IllegalMoveException("Nessuna torre puo' spostarsi in quella cella.");
	    }

	    if (count == 1) {
	      if (move.charAt(1) == 'x') {
	        piecesAndCell[1] = board[a][b].getPiece().toString();
	        piecesAndCell[0] = board[a][b].getPiece().toString();
	        piecesAndCell[2] = move.substring(2,4);
	        captureRook(xC1, yC1, a, b, move);
	        return piecesAndCell;
	      } else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
	        actualMoveRook(xC1, yC1, a, b, move);
	        piecesAndCell[0] = board[a][b].getPiece().toString();
	        piecesAndCell[2] = move.substring(1,3);
	        return piecesAndCell;
	      } else {
	        throw new IllegalMoveException("Mossa non riconosciuta.");
	      }
	    } else if (count == 2) {
	      if (move.charAt(1) == 'x') {
	    	  piecesAndCell[1] = board[a][b].getPiece().toString();
	          piecesAndCell[0] = board[a][b].getPiece().toString();
	          piecesAndCell[2] = move.substring(2,4);
	        captureRook(xC2, yC2, a, b, move);
	        return piecesAndCell;
	      } else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
	        actualMoveRook(xC2, yC2, a, b, move);
	        piecesAndCell[0] = board[a][b].getPiece().toString();
	        piecesAndCell[2] = move.substring(1,3);
	        return piecesAndCell;
	      } else {
	        throw new IllegalMoveException("Mossa non riconosciuta.");
	      }
	    } else if (count == 3) {
	      if (move.charAt(1) == 'x') {
	        throw new IllegalMoveException(
	            "Mossa ambigua, devi specificare quale delle due torri muovere secondo la notazione algebrica.");
	      }
	      if (move.length() == 3) {
	        throw new IllegalMoveException(
	            "Mossa ambigua, devi specificare quale delle due torri muovere secondo la notazione algebrica.");
	      }

	      int x, y;
	      if (move.charAt(1) >= '1' && move.charAt(1) <= '8') {
	        if (xC1 == xC2) {
	          throw new IllegalMoveException(
	              "Quando le due torri si trovano sulla stessa riga e' necessario specificare la colonna!");
	        }
	        if (xC1 == (8 - Integer.parseInt(move.substring(1, 2)))) {
	          x = xC1;
	          y = yC1;
	        } else if (xC2 == (8 - Integer.parseInt(move.substring(1, 2)))) {
	          x = xC2;
	          y = yC2;
	        } else {
	          throw new IllegalMoveException(
	              "Nessuna torre appartenente alla riga di disambiguazione specificata.");
	        }
	        if (move.length() == 4) {
	          actualMoveRook(x, y, a, b, move);
	          piecesAndCell[0] = board[a][b].getPiece().toString();
	          piecesAndCell[2] = move.substring(1,3);
	          return piecesAndCell;
	        } else if (move.length() == 5) {
	        	piecesAndCell[0] = board[a][b].getPiece().toString();
	            piecesAndCell[2] = move.substring(1,3);
	            captureRook(x, y, a, b, move);
	            return piecesAndCell;
	            } else {
	          throw new IllegalMoveException("Mossa non riconosciuta.");
	        }
	      } else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
	        if (yC1 == yC2) {
	          throw new IllegalMoveException(
	              "Quando le due torri si trovano sulla stessa colonna e' necessario specificare la riga!");
	        }
	        if (yC1 == ((int) move.charAt(1) - 97)) {
	          x = xC1;
	          y = yC1;
	        } else if (yC2 == ((int) move.charAt(1) - 97)) {
	          x = xC2;
	          y = yC2;
	        } else {
	          throw new IllegalMoveException(
	              "Nessuna torre appartenente alla colonna di disambiguazione specificata.");
	        }
	        if (move.length() == 4) {
	          actualMoveRook(x, y, a, b, move);
	          piecesAndCell[0] = board[a][b].getPiece().toString();
	          piecesAndCell[2] = move.substring(1,3);
	          return piecesAndCell;
	        } else if (move.length() == 5) {
	        	 piecesAndCell[1] = board[a][b].getPiece().toString();
	             piecesAndCell[0] = board[a][b].getPiece().toString();
	             piecesAndCell[2] = move.substring(2,4);
	          captureRook(x, y, a, b, move);
	          return piecesAndCell;
	        } else {
	          throw new IllegalMoveException("Mossa non riconosciuta.");
	        }
	      } else {
	        throw new IllegalMoveException("Mossa non riconosciuta.");
	      }
	    }
	    return piecesAndCell;
	  }

  void actualMoveRook(int xC, int yC, int x, int y, String move) throws IllegalMoveException {
    if (board[x][y].getPiece() != null
        && board[x][y].getPiece().getColor() == (blackTurn ? 1 : 0)) {
      throw new IllegalMoveException(
          "Mossa non valida, devi specificare la cattura come da notazione algebrica.");
    }
    if (blackTurn==true) {
    board[x][y].setPiece(board[xC][yC].getPiece());
    board[xC][yC].setEmpty();
    if(King.isThreatened()) {
      board[xC][yC].setPiece(board[x][y].getPiece());
  	  board[x][y].setEmpty();
      throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
    }
    } else {
    	board[x][y].setPiece(board[xC][yC].getPiece());
   	    board[xC][yC].setEmpty();
   	 if (King.isThreatened()) {
   	     board[xC][yC].setPiece(board[x][y].getPiece());
   	     board[x][y].setEmpty();
         throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
     }
   }
    
    movesDone.add(move);
    ((Rook) board[x][y].getPiece()).incrementMoves();
    setNotBlackTurn();
  }

  void captureRook(int xC, int yC, int x, int y, String move) throws IllegalMoveException {
    if (board[x][y].getPiece() == null) {
      throw new IllegalMoveException("Mossa non valida, non c'e' nessun pezzo da catturare.");
    }
    if (board[x][y].getPiece().getColor() == 0) {
      WhitesCaptured.add(board[x][y].getPiece().toString());
    } else {
      BlacksCaptured.add(board[x][y].getPiece().toString());
    }
    board[x][y].setPiece(board[xC][yC].getPiece());
    board[xC][yC].setEmpty();
    movesDone.add(move);
    ((Rook) board[x][y].getPiece()).incrementMoves();
    setNotBlackTurn();
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
