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

	public String[][] board() {
		String[][] board = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				board[i][j] = Game.getCell(i, j).toString();
			}

		}

		return board;
	}

	public ArrayList<String> moves() {
		return game.getMoves();
		
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
			pieces= game.moveBishop(input);
			break;

		case 'D':
			pieces= game.moveQueen(input);
			break;

		case 'R':
			pieces= game.moveKing(input);
			break;

		case '0':
			if (input.equals("0-0") || input.equals("O-O")) {

				pieces = game.shortCastling(); 
				break;


			} 
			else if (input.equals("0-0-0") || input.equals("O-O-O")) {
				pieces = game.longCastling();
				break;


			} else

				throw new IllegalMoveException(
						"Errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; 0-0-0 oppure O-O-O per arrocco lungo");

		default:
			pieces = game.moveAPawn(input);
			break;
		}

		game.addMove(input);
		game.changeTurn();
		return pieces;
	}

	public Boolean getBlackTurn() {
		return Game.getBlackTurn(); 
	}


	public ArrayList<String> Blackcaptured() {
		return game.getBlacks();
	}
	public ArrayList<String> Whitecaptured() {
		return game.getWhites();
	}

}
