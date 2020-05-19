package it.uniba.main;

import java.util.ArrayList;

/**
 * <<control>><br>
 * Menu class, containing all methods of the command list.
 *
 * @author Megi Gjata
 * @author Mario Giordano
 * @author Donato Lucente
 * @author Patrick Clark
 * @author Filippo Iacobellis
 */
public class Menu {
  private Game game = new Game();

 public String help() {
	 return "Lista di comandi utilizzabili:\n" + "help\n" +"play\n"
	    +"quit\n" +
	    "Lista di comandi utilizzabili solo se in partita:\n" +
	    "board\n" 
	    +"captures\n"
	    +"moves\n"
	    +"Per effettuare una mossa e' necessario specificarla in notazione algebrica; \nPer la cattura en passant si puo' specificare 'e.p.' o 'ep' alla fine della mossa in notazione algebrica";
  }

  String[][] board() {
    String[][] board = new String[8][8];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (Game.getCell(i, j) == null) {
          board[i][j] = "";
        } else {
          board[i][j] = Game.getCell(i, j).toString();
        }
      }
    }

    return board;
  }

  void moves() {
    ArrayList<String> movesDone = game.getMoves();
    PrintMessage.printMoves(movesDone);
  }

  public void play() {
    game.newGame();
  }

  public String[] getMove(String input) throws IllegalArgumentException, IndexOutOfBoundsException, IllegalMoveException{
    char chosenPiece = input.charAt(0);
    String[] pieces = new String[3];
    switch (chosenPiece) {
    case 'T': //da sistemare
        pieces = game.moveRook(input);
        if (pieces[0] == null) {
          if (input.length() == 3) {
            //PrintMessage.printAMove(piece[1], input.substring(1, 3));
            return pieces;
          } else if (input.length() == 4) {
            //PrintMessage.printAMove(piece[1], input.substring(2, 4));
            return pieces;
          }
        } else {
          if (input.length() == 4) {
            //PrintMessage.printACapture(piece, input.substring(2, 4));
            //PrintMessage.printAMove(piece[1], input.substring(2, 4));
            return pieces;
          } else if (input.length() == 5) {
            //PrintMessage.printACapture(piece, input.substring(3, 5));
            //PrintMessage.printAMove(piece[1], input.substring(3, 5));
            return pieces;
          }
        } 
       throw new IllegalMoveException("Mossa non consentita per la torre");
      
    case 'C': //da sistemare
      
        if (pieces[0] == null) {
          if (input.length() == 3) {
            //PrintMessage.printAMove(piece[1], input.substring(1, 3));
            return game.moveKnight(input);
          } else if (input.length() == 4) {
            //PrintMessage.printAMove(piece[1], input.substring(2, 4));
            return game.moveKnight(input);
          }
        } else {
          if (input.length() == 4) {
            //PrintMessage.printACapture(piece, input.substring(2, 4));
            //PrintMessage.printAMove(piece[1], input.substring(2, 4));
            return game.moveKnight(input);
          } else if (input.length() == 5) {
            //PrintMessage.printACapture(piece, input.substring(3, 5));
            //PrintMessage.printAMove(piece[1], input.substring(3, 5));
            return game.moveKnight(input);
          }
        }
        throw new IllegalMoveException("Mossa non consentita per il cavallo");
      
    case 'A':
      if (input.length() == 3) {
        
          //PrintMessage.printAMove(piece, input);
          return game.moveBishop(input);
      } else if ((input.length() == 4) && (input.substring(1, 2).equals("x"))) {
        
          return game.captureBishop(input);
          //PrintMessage.printACapture(piece, input.substring(2, 4));
          //PrintMessage.printAMove(piece[1], input.substring(2, 4));
      } else throw new IllegalMoveException("Mossa non consentita per l'Alfiere");
    case 'D':
      if (input.length() == 3) {
        
          return game.moveQueen(input);

      } else if ((input.length() == 4) && (input.substring(1, 2).equals("x"))) {
        
          return game.captureQueen(input);

      } else throw new IllegalMoveException("Mossa non consentita per la Donna");
       
      case 'R': //da sistemare
        
          return game.moveKing(input);
 
      case '0':
          if (input.equals("0-0")) {
                return game.shortCastling(); 
            } 
           else if (input.equals("0-0-0")) {
            
              return game.longCastling();

            
          } else

          throw new IllegalMoveException(
                "Errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; 0-0-0 oppure O-O-O per arrocco lungo");
          

        case 'O':
          if (input.equals("O-O")) {
           
              return game.shortCastling();
             
            
          } else if (input.equals("O-O-O")) {
            
              return game.longCastling();
            
          } else

          throw new IllegalMoveException(
                "Errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; 0-0-0 oppure O-O-O per arrocco lungo");

      default:
        return game.moveAPawn(input);
    }
  }

  void captures() {
    PrintMessage.printCaptures(game.getBlacks(), game.getWhites());
  }
}
