package it.uniba.main;

//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.security.GeneralSecurityException;
import java.util.Scanner;

//import it.uniba.sotorrent.GoogleDocsUtils;

/**
 * The main class for the project. It must be customized to meet the project
 * assignment specifications.
 * 
 * <b>DO NOT RENAME</b>
 */
public final class AppMain {
	/**
	 * Private constructor. Change if needed.
	 */
	private AppMain() {

	}

	/**
	 * * This is the main entry of the application.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(final String[] args) {
		Menu menu = new Menu();
		String turn = "white";
		boolean inGame = false;
		boolean exit = false;
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to the chess app!");
		System.out.println("Please type commands to be executed or type help to show a list of them.");

		while (!exit) {

			if (menu.getTurn() == false) {
				turn = "black";
			} else {
				turn = "white";
			}
			System.out.println("Please type a command (" + turn + " turn)");
			String input = in.nextLine();
			switch (input) {
				case "board":
					if (inGame) {
						menu.board();
						break;
					} else {
						System.err.println("You have to be in an active game to use that command.");
						break;
					}
				case "captures":
					if (inGame) {
						menu.captures();
						break;
					} else {
						System.err.println("You have to be in an active game to use that command.");
						break;
					}
				case "help":
					menu.help();
					break;
				case "moves":
					if (inGame) {
						menu.moves();
						break;
					} else {
						System.err.println("You have to be in an active game to use that command.");
						break;

					}
				case "play":
					if (!inGame) {
						inGame = true;
						menu.play();
					} else {
						System.out.println("There is already a started game. Are you sure? ");
						while (true) {
							String answer = in.nextLine();
							if (answer.toUpperCase().equals("YES")) {
								System.out.println("Deleting current game and starting new one");
								menu.play();
								menu.resetTurn();
								break;
							} else if (answer.toUpperCase().equals("NO")) {
								break;
							} else
								System.out.println("Invalid answer; Type yes or no");
						}
					}
					break;
				case "quit":
					exit = menu.quit();
					break;
				default:
					if (inGame) {
						menu.getMove(input);
						break;
					} else {
						System.err.println("That is not a valid command nor move.");
						break;
					}
			}
		}
		in.close();
		System.exit(0);
	}
}