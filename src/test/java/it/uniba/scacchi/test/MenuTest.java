package it.uniba.scacchi.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.uniba.main.IllegalMoveException;
import it.uniba.main.Menu;

public class MenuTest {

	private static InputStream in;
	private static Menu menu = new Menu();

	@BeforeAll
	static void setUpAll() {
		System.out.println("test");
		String input = "play";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		System.out.println("setUpAll");
		menu.play();

	}

	@AfterAll
	static void tearDownAll() {
		System.out.println("teadDownAll");

	}

	@Test
	void helpTest() {
		String help = "Lista di comandi utilizzabili:\n" + "help\n" + "play\n" + "quit\n"
				+ "Lista di comandi utilizzabili solo se in partita:\n" + "board\n" + "captures\n" + "moves\n"
				+ "Per effettuare una mossa e' necessario specificarla in notazione algebrica; \nPer la cattura en passant si puo' specificare 'e.p.' o 'ep' alla fine della mossa in notazione algebrica";
		assertEquals(menu.help(), help);
	}

	// @Test
	// void BoardTest() {
	// String[][] board =
	// assertEquals(menu.help(),help);
	// }
	//
	@Test
	void PawnTest() {
		String[] mossa = { "\u2659", null, "e4" };
		try {
			assertArrayEquals(mossa, menu.getMove("e4"));
		} catch (IllegalArgumentException | IndexOutOfBoundsException | IllegalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
