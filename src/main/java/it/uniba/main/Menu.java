package it.uniba.main;

import java.util.Scanner;
import java.util.ArrayList;

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
				"To perform a move it is sufficient to specify it in algebric notation; \nFor en Passant captures add 'e.p.' at the end of algebric notation'");
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
			if (input.substring(1, 2) == "x")
				;
			try {
				game.pawnCapture(input);
			} catch (IllegalArgumentException e) {
				System.err.println("Illegal move; Please try again");
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Illegal move; Please try again");
			} catch (IllegalMoveException e) {
				System.err.println(e.getMessage());
			}

		} else if (input.length() == 8) {
			if ((input.substring(0, 2) == "Px") && (input.substring(4, 8) == "e.p."))
				;
			try {
				game.captureEnPassant(input);
			} catch (IllegalArgumentException e) {
				System.err.println("Illegal move; Please try again");
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Illegal move; Please try again");
			} catch (IllegalMoveException e) {
				System.err.println(e.getMessage());
			}
		} else {
			System.out.println("Illegal move, please try again");
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
			if (answer.equals("YES")) {
				return true;
			} else if (answer.equals("NO")) {
				return false;
			} else {
				System.out.println("Invalid answer, please type again:");
			}
			in.close();
		}
	}

	boolean getTurn() {
		return game.getWhiteTurn();
	}

	void resetTurn() {
		game.setWhiteTurn();
	}
}
