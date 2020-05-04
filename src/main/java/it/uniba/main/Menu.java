package it.uniba.main;

import java.util.Scanner;
import java.util.ArrayList;

/**
* ï¿½controlï¿½<br>
* Menu class, containing all methods of the command list.
* 
* @author Megi Gjata
* @author Mario Giordano
* @author Donato Lucente
* @author Patrick Clark
* @author Filippo Iacobellis
* 
*/
class Menu {
	private Game game = new Game();

	void help() {
		System.out.println("List of executable commands:");
		System.out.println("help");
		System.out.println("play");
		System.out.println("quit");
		System.out.println("\nList of commands that can only be executed in the game:");
		System.out.println("board");
		System.out.println("captures");
		System.out.println("moves");
		System.out.println(
				"To perform a move it is sufficient to specify it in algebric notation; \nFor en Passant captures you can add 'e.p.' or 'ep' at the end of algebric notation'");
	}

	void board() {
		System.out.println("    a    b    c    d    e    f    g    h");
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i + "  ");
			for (int j = 0; j < 8; j++) {
				Cell c = Game.getCell(i, j);
				System.out.print(c + "  ");
			}
			System.out.print(8 - i + "  ");
			System.out.println("\n");
		}
		System.out.println("    a    b    c    d    e    f    g    h");
	}

	void moves() {
		ArrayList<String> movesDone = game.getMoves();
		if (game.getMoves().size() == 0) {
			System.out.println("No moves done");
		} else {
			int j; // secondo indice
			int k = 1; // numero mossa
			for (int i = 0; i < movesDone.size(); i++) {
				j = i + 1;
				if (j < movesDone.size()) {

					System.out.println(k + ". " + movesDone.get(i) + " " + movesDone.get(j));
					i++;
					k++;

				} else if (i == movesDone.size() - 1) {
					System.out.println(k + ". " + movesDone.get(i));

				}
			}
		}

	}

	void play() {
		game.newGame();
	}

	void getMove(String input) {
		char chosenPiece = input.charAt(0);
		switch (chosenPiece) {
			case 'T':
				try {
					game.moveRook(input);
				} catch (IllegalArgumentException e) {
					System.err.println("Illegal move; Please try again");
				} catch (IndexOutOfBoundsException e) {
					System.err.println("Illegal move; Please try again");
				} catch (IllegalMoveException e) {
					System.err.println(e.getMessage());
				}
				break;
			case 'C':
				try {
					game.moveKnight(input);
				} catch (IllegalArgumentException e) {
					System.err.println("Illegal move knightillegalargument; Please try again");
				} catch (IndexOutOfBoundsException e) {
					System.err.println("Illegal move; Please try again");
				} catch (IllegalMoveException e) {
					System.err.println(e.getMessage());
				}
				break;
			case 'A':
				if (input.length() == 3) {
					try {
						game.moveBishop(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IndexOutOfBoundsException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
					break;
				} else if ((input.length() == 4) && (input.substring(1, 2).equals("x"))) {
					try {
						game.captureBishop(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IndexOutOfBoundsException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
					break;
				} else
					System.err.println("Mossa non consentita per l'Alfiere");
				break;
			case 'D':
				if (input.length() == 3) {
					try {
						game.moveQueen(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IndexOutOfBoundsException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
					break;

				} else if ((input.length() == 4) && (input.substring(1, 2).equals("x"))) {
					try {
						game.captureQueen(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IndexOutOfBoundsException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
					break;
				} else
					System.err.println("Mossa non consentita per la Donna");
				break;
			case 'R':
				try {
					game.moveKing(input);
				} catch (IllegalArgumentException e) {
					System.err.println("Illegal move; Please try again");
				} catch (IndexOutOfBoundsException e) {
					System.err.println("Illegal move; Please try again");
				} catch (IllegalMoveException e) {
					System.err.println(e.getMessage());
				}
				break;
			case '0':
				if (input.equals("0-0")) {
					try {
						game.shortCastling();
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
				} else if (input.equals("0-0-0")) {
					try {
						game.longCastling();
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
				} else System.err.println("errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; 0-0-0 oppure O-O-O per arrocco lungo");
				break;
			case 'O':
				if (input.equals("O-O")) {
					try {
						game.shortCastling();
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
				} else if (input.equals("O-O-O")) {
					try {
						game.longCastling();
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
				} else System.err.println("errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; 0-0-0 oppure O-O-O per arrocco lungo");
				break;
			default:
				if (input.length() == 2) {
					try {
						game.moveAPawn(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IndexOutOfBoundsException e) {
						System.err.println("Illegal move; Please try again");
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
				} else if (input.length() == 4) {
					if (input.substring(1, 2).equals("x")) {

						try {
							game.pawnCapture(input);
						} catch (IllegalArgumentException e) {
							System.err.println("Illegal move; Please try again");
						} catch (IndexOutOfBoundsException e) {
							System.err.println("Illegal move; Please try again");
						} catch (IllegalMoveException e) {
							System.err.println(e.getMessage());
						}
					} else
						System.err.println("Mossa non valida");

				} else if (input.length() == 8) {
					if ((input.substring(1, 2).toLowerCase().equals("x"))
							&& (input.substring(4, 8).toLowerCase().equals("e.p."))) {

						try {
							game.captureEnPassant(input);
						} catch (IllegalArgumentException e) {
							System.err.println("Illegal move; Please try again");
						} catch (IndexOutOfBoundsException e) {
							System.err.println("Illegal move; Please try again");
						} catch (IllegalMoveException e) {
							System.err.println(e.getMessage());
						}
					} else
						System.err.println("Illegal move; Please try again");

				} else if (input.length() == 6) {
					if ((input.substring(1, 2).toLowerCase().equals("x"))
							&& (input.substring(4, 6).toLowerCase().equals("ep"))) {

						try {
							game.captureEnPassant(input);
						} catch (IllegalArgumentException e) {
							System.err.println("Illegal move; Please try again");
						} catch (IndexOutOfBoundsException e) {
							System.err.println("Illegal move; Please try again");
						} catch (IllegalMoveException e) {
							System.err.println(e.getMessage());
						}
					} else
						System.err.println("Illegal move; Please try again");
				} else
					System.err.println(
							"Mossa illegale o comando inesistente; Riprova utilizzando un comando consentito o inserisci una mossa legale");
				break;

		}

	}

	void captures() {
		if (game.getBlacks().size() == 0) {
			System.out.println("No black pieces captured");
		} else {
			System.out.println("Captured black pieces: " + game.getBlacks());
		}
		if (game.getWhites().size() == 0) {
			System.out.println("No white pieces captured");
		} else {
			System.out.println("Captured white pieces: " + game.getWhites());
		}
	}

	boolean quit() {
		Scanner in = new Scanner(System.in);
		String answer;
		System.out.println("Are you sure you want to quit?:");
		while (true) {
			answer = in.nextLine();
			answer = answer.toUpperCase();
			if (answer.equals("YES") || answer.equals("SI") || answer.equals("SÌ")) {
				in.close();
				return true;
			} else if (answer.equals("NO")) {
				in.close();
				return false;
			} else {
				System.out.println("Invalid answer, please type again:");
			}
		}
	}

	void resetTurn() {
		game.setWhiteTurn();
	}
}
