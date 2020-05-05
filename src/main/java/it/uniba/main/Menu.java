package it.uniba.main;

import java.util.Scanner;
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
 * 
 */
class Menu {
	private Game game = new Game();

	void help() {
		System.out.println("Lista di comandi utilizzabili:");
		System.out.println("help");
		System.out.println("play");
		System.out.println("quit");
		System.out.println("\nLista di comandi utilizzabili solo se in partita:");
		System.out.println("board");
		System.out.println("captures");
		System.out.println("moves");
		System.out.println(
				"Per effettuare una mossa è necessario specificarla in notazione algebrica; \nPer la cattura en passant si può specificare 'e.p.' o 'ep' alla fine della mossa in notazione algebrica");
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
			System.out.println("Non sono ancora state effettuate mosse");
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
					System.err.println("Mossa non riconosciuta");
				} catch (IndexOutOfBoundsException e) {
					System.err.println(
							"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
				} catch (IllegalMoveException e) {
					System.err.println(e.getMessage());
				}
				break;
			case 'C':
				try {
					game.moveKnight(input);
				} catch (IllegalArgumentException e) {
					System.err.println("Mossa non riconosciuta");
				} catch (IndexOutOfBoundsException e) {
					System.err.println(
							"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
				} catch (IllegalMoveException e) {
					System.err.println(e.getMessage());
				}
				break;
			case 'A':
				if (input.length() == 3) {
					try {
						game.moveBishop(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Mossa non riconosciuta");
					} catch (IndexOutOfBoundsException e) {
						System.err.println(
								"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
					break;
				} else if ((input.length() == 4) && (input.substring(1, 2).equals("x"))) {
					try {
						game.captureBishop(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Mossa non riconosciuta");
					} catch (IndexOutOfBoundsException e) {
						System.err.println(
								"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
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
						System.err.println("Mossa non riconosciuta");
					} catch (IndexOutOfBoundsException e) {
						System.err.println(
								"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
					break;

				} else if ((input.length() == 4) && (input.substring(1, 2).equals("x"))) {
					try {
						game.captureQueen(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Mossa non riconosciuta");
					} catch (IndexOutOfBoundsException e) {
						System.err.println(
								"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
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
					System.err.println("Mossa non riconosciuta");
				} catch (IndexOutOfBoundsException e) {
					System.err.println(
							"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
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
				} else
					System.err.println(
							"errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; 0-0-0 oppure O-O-O per arrocco lungo");
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
				} else
					System.err.println(
							"errore di sintassi; Utilizzare 0-0 oppure O-O per arroco corto; 0-0-0 oppure O-O-O per arrocco lungo");
				break;
			default:
				if (input.length() == 2) {
					try {
						game.moveAPawn(input);
					} catch (IllegalArgumentException e) {
						System.err.println("Mossa non riconosciuta");
					} catch (IndexOutOfBoundsException e) {
						System.err.println(
								"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
				} else if (input.length() == 4) {
					if (input.substring(1, 2).equals("x")) {

						try {
							game.pawnCapture(input);
						} catch (IllegalArgumentException e) {
							System.err.println("Mossa non riconosciuta");
						} catch (IndexOutOfBoundsException e) {
							System.err.println(
									"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
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
							System.err.println("Mossa non riconosciuta");
						} catch (IndexOutOfBoundsException e) {
							System.err.println(
									"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
						} catch (IllegalMoveException e) {
							System.err.println(e.getMessage());
						}
					} else
						System.err.println("Mossa non valida");

				} else if (input.length() == 6) {
					if ((input.substring(1, 2).toLowerCase().equals("x"))
							&& (input.substring(4, 6).toLowerCase().equals("ep"))) {

						try {
							game.captureEnPassant(input);
						} catch (IllegalArgumentException e) {
							System.err.println("Mossa non riconosciuta");
						} catch (IndexOutOfBoundsException e) {
							System.err.println(
									"Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8");
						} catch (IllegalMoveException e) {
							System.err.println(e.getMessage());
						}
					} else
						System.err.println("Mossa non valida");
				} else
					System.err.println(
							"Mossa illegale o comando inesistente; Riprova utilizzando un comando consentito o inserisci una mossa legale");
				break;

		}

	}

	void captures() {
		if (game.getBlacks().size() == 0) {
			System.out.println("Nessun pezzo nero catturato");
		} else {
			System.out.println("Pezzi neri catturati: " + game.getBlacks());
		}
		if (game.getWhites().size() == 0) {
			System.out.println("Nessun pezzo bianco catturato");
		} else {
			System.out.println("Pezzi bianchi catturati: " + game.getWhites());
		}
	}

	boolean quit() {
		Scanner in = new Scanner(System.in);
		String answer;
		System.out.println("Sei sicuro di voler uscire?:");
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
