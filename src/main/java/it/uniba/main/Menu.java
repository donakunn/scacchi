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
 * 
 */
class Menu {
	private Game game = new Game();

	void help() {
		PrintMessage.helpPrint();
		}

	void board() {
		String [][]board = new String[8][8] ;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (Game.getCell(i, j) == null) {
					board[i][j] = "";
				}
				else {
				board[i][j] = Game.getCell(i, j).toString(); 
				}
				
			}
		}
		
		PrintMessage.printBoard(board);
		
	}

	void moves() {
		ArrayList<String> movesDone = game.getMoves();
		PrintMessage.printMoves(movesDone);
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
						String piece = game.moveBishop(input);
						PrintMessage.printAMove(piece, input);
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
						String piece = game.moveQueen(input);
						PrintMessage.printAMove(piece, input.substring(1, 3));
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
						String pieces[] = game.captureQueen(input);
						PrintMessage.printACapture(pieces, input.substring(2, 4));
						PrintMessage.printAMove(pieces[1], input.substring(2, 4));

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
					String pieces[]=game.moveKing(input);
					if(pieces[0]==null) {
						PrintMessage.printAMove(pieces[1], input.substring(1, 3));
					}else {
						PrintMessage.printACapture(pieces, input.substring(2, 4));
						PrintMessage.printAMove(pieces[1], input.substring(2, 4));
					}
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
						PrintMessage.printShortCastling();
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
				} else if (input.equals("0-0-0")) {
					try {
						game.longCastling();
						PrintMessage.printLongCastling();
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
						PrintMessage.printShortCastling();
					} catch (IllegalMoveException e) {
						System.err.println(e.getMessage());
					}
				} else if (input.equals("O-O-O")) {
					try {
						game.longCastling();
						PrintMessage.printLongCastling();
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
						String piece = game.moveAPawn(input);
						PrintMessage.printAMove(piece, input);
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
							String []pieces = game.pawnCapture(input);
							PrintMessage.printACapture(pieces, input.substring(2, 4));
							PrintMessage.printAMove(pieces[1], input.substring(2, 4));
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
							String []pieces = game.captureEnPassant(input);
							PrintMessage.printACapture(pieces, input.substring(2, 4)+ " e.p.");
							PrintMessage.printAMove(pieces[1], input.substring(2, 4));
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
							String []pieces = game.captureEnPassant(input);
							PrintMessage.printACapture(pieces, input.substring(2, 4)+ " e.p.");
							PrintMessage.printAMove(pieces[1], input.substring(2, 4));
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
		PrintMessage.printCaptures(game.getBlacks(), game.getWhites());
	}

	
	void resetTurn() {
		game.setWhiteTurn();
	}
}
