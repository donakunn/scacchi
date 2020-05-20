package it.uniba.scacchi.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.main.IllegalMoveException;
import it.uniba.main.Menu;

public class MenuTest {

	private static Menu menu = new Menu();
	private ArrayList<String> expectedMoves = new ArrayList<String>();
	private ArrayList<String> expectedBlackPieceCaptured = new ArrayList<String>();
	private ArrayList<String> expectedWhitePieceCaptured = new ArrayList<String>();


	//	@BeforeAll
	//	static void setUpAll() {
	//		System.out.println("test");
	//		String input = "play";
	//		in = new ByteArrayInputStream(input.getBytes());
	//		System.setIn(in);
	//		System.out.println("setUpAll");
	//		menu.play();
	//
	//	}

	//	@AfterAll
	//	static void tearDownAll() {
	//		System.out.println("teadDownAll");
	//
	//	}

	@BeforeEach
	void setUp() { 
		//reset partita dopo ogni test
		menu.play();
		expectedMoves.clear();
		expectedBlackPieceCaptured.clear();
		expectedWhitePieceCaptured.clear();
	}


	@Test
	@DisplayName("Testing help menu print")
	void testhelp() {
		//test stampa help
		String help = "Lista di comandi utilizzabili:\n" + "help\n" + "play\n" + "quit\n"
				+ "Lista di comandi utilizzabili solo se in partita:\n" + "board\n" + "captures\n" + "moves\n"
				+ "Per effettuare una mossa e' necessario specificarla in notazione algebrica; \nPer la cattura en passant si puo' specificare 'e.p.' o 'ep' alla fine della mossa in notazione algebrica";
		assertEquals(menu.help(), help);
	}

	@Test
	@DisplayName("Testing new game board print")
	void newGameBoardTest() {
		//test stampa scacchiera nuova partita
		String[][] board = { {"[\u265C]","[\u265E]","[\u265D]","[\u265B]","[\u265A]","[\u265D]","[\u265E]","[\u265C]"},	 
				{"[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]"},
				{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				{"[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]"},
				{"[\u2656]","[\u2658]","[\u2657]","[\u2655]","[\u2654]","[\u2657]","[\u2658]","[\u2656]"},
		};
		assertArrayEquals(menu.board(),board);
	}

	@Test
	@DisplayName("Testing get turn")
	void getTurnTest() {
		//test lettura turno
		assertFalse(menu.getBlackTurn());
	}

	@Test
	@DisplayName("Testing move a Pawn")
	void printMovesTest() {
		//test mossa Pedone, avvaloro lista mosse attese
		ArrayList<String> expectedMoves = new ArrayList<String>();
		expectedMoves.add("a4");
		expectedMoves.add("b5");
		expectedMoves.add("c3");
		expectedMoves.add("d6");
		expectedMoves.add("axb5");
		expectedMoves.add("a6");
		expectedMoves.add("b4");
		expectedMoves.add("a5");
		expectedMoves.add("Ab2");
		expectedMoves.add("g6");
		expectedMoves.add("h3");
		expectedMoves.add("Ah6");
		expectedMoves.add("Db3");
		expectedMoves.add("f6");
		expectedMoves.add("Rd1");
		expectedMoves.add("e6");
		expectedMoves.add("Rc1");
		expectedMoves.add("c6");
		expectedMoves.add("f3");
		expectedMoves.add("e5");

		// array di mosse eseguite da confrontare

		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "b5" };
		String[] mossa3 = { "\u2659", null, "c3" };
		String[] mossa4 = { "\u265F", null, "d6" };
		String[] mossa5 = { "\u2659", "\u265F", "b5" };
		String[] mossa6 = { "\u265F", null, "a6" };
		String[] mossa7 = { "\u2659", null, "b4" };
		String[] mossa8 = { "\u265F", null, "a5" };
		String[] mossa9 = { "\u2657", null, "b2" };
		String[] mossa10 = { "\u265F", null, "g6" };
		String[] mossa11 = { "\u2659", null, "h3" };
		String[] mossa12 = { "\u265D", null, "h6" };
		String[] mossa13 = { "\u2655", null, "b3" };
		String[] mossa14 = { "\u265F", null, "f6" };
		String[] mossa15 = { "\u2654", null, "d1" };
		String[] mossa16 = { "\u265F", null, "e6" };
		String[] mossa17 = { "\u2654", null, "c1" };
		String[] mossa18 = { "\u265F", null, "c6" };
		String[] mossa19 = { "\u2659", null, "f3" };
		String[] mossa20 = { "\u265F", null, "e5" };

		//test mossa pedone su cella lecita

		assertAll("Moving pawns on lecit cells", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("b5"));
			assertArrayEquals(mossa3, menu.getMove("c3"));
			assertArrayEquals(mossa4, menu.getMove("d6"));
		});

		//test mossa illegale per pedone

		assertAll("Try a move on not lecit cell", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("f5");
			});
			assertThrows(IndexOutOfBoundsException.class, () -> {
				menu.getMove("t9");
			});

		});

		//test mossa lecita, ma cella già occupata

		assertAll("Try a move on lecit cell, but occupied", () -> {
			assertArrayEquals(mossa5, menu.getMove("axb5"));
			assertArrayEquals(mossa6, menu.getMove("a6"));
			assertArrayEquals(mossa7, menu.getMove("b4"));
			assertArrayEquals(mossa8, menu.getMove("a5"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("b5");
			});

		});

		//test mossa Pedone che lascerebbe il re sotto scacco

		assertAll("Test moving a Pawn that will leave king threatened", () -> {
			assertArrayEquals(mossa9, menu.getMove("Ab2"));
			assertArrayEquals(mossa10, menu.getMove("g6"));
			assertArrayEquals(mossa11, menu.getMove("h3"));
			assertArrayEquals(mossa12, menu.getMove("Ah6"));
			assertArrayEquals(mossa13, menu.getMove("Db3"));
			assertArrayEquals(mossa14, menu.getMove("f6"));
			assertArrayEquals(mossa15, menu.getMove("Rd1"));
			assertArrayEquals(mossa16, menu.getMove("e6"));
			assertArrayEquals(mossa17, menu.getMove("Rc1"));
			assertArrayEquals(mossa18, menu.getMove("c6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("d3");
			});

		});

		//test tentativo di mossa di un pezzo diverso da pedone, con notazione per pedone

		assertAll("Test moving a different piece as a Pawn", () -> {

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("a3");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("a2");
			});
			assertArrayEquals(mossa19, menu.getMove("f3"));

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("a6");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("a7");
			});
		});

		//test tentativo di mossa lecita ma su pedone colore opposto
		assertAll("Test moving a Pawn with different color", () -> {

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("f2");
			});
			assertArrayEquals(mossa20, menu.getMove("e5"));

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("c7");
			});


		});

		assertEquals(expectedMoves,menu.moves());

	}



	//test di cattura da parte di pedone
	@Test
	void testCapturefromAPawn() {

		//avvaloro mosse attese

		expectedMoves.add("b4");
		expectedMoves.add("c5");
		expectedMoves.add("bxc5");
		expectedMoves.add("d6");
		expectedMoves.add("e4");
		expectedMoves.add("dxc5");
		expectedMoves.add("d4");
		expectedMoves.add("c4");
		expectedMoves.add("f4");
		expectedMoves.add("cxd3");
		expectedMoves.add("a4");
		expectedMoves.add("b5");
		expectedMoves.add("a5");
		expectedMoves.add("f5");
		expectedMoves.add("axb6");

		//pezzi catturati attesi

		expectedBlackPieceCaptured.add("\u265F");
		expectedWhitePieceCaptured.add("\u2659");

		//array mosse da confrontare con il risultato

		String[] mossa1 = { "\u2659", null, "b4" };
		String[] mossa2 = { "\u265F", null, "c5" };
		String[] mossa3 = { "\u2659", "\u265F", "c5" };
		String[] mossa4 = { "\u265F", null, "d6" };
		String[] mossa5 = { "\u2659", null, "e4" };
		String[] mossa6 = { "\u265F", "\u2659", "c5" };
		String[] mossa7 = { "\u2659", null, "d4" };
		String[] mossa8 = { "\u265F", null, "c4" };
		String[] mossa9 = { "\u2659", null, "f4" };
		String[] mossa10 = { "\u265F", "\u2659", "d3 e.p." };
		String[] mossa11 = { "\u2659", null, "a4" };
		String[] mossa12 = { "\u265F", null, "b5" };
		String[] mossa13 = { "\u2659", null, "a5" };
		String[] mossa14 = { "\u265F", null, "f5" };
		String[] mossa15 = { "\u2659", "\u265F", "b6 e.p." };


		//test cattura semplice per entrambi i colori
		assertAll("Moving pawns and capture", () -> {
			assertArrayEquals(mossa1, menu.getMove("b4"));
			assertArrayEquals(mossa2, menu.getMove("c5"));
			assertArrayEquals(mossa3, menu.getMove("bxc5"));
			assertArrayEquals(mossa4, menu.getMove("d6"));
			assertArrayEquals(mossa5, menu.getMove("e4"));
			assertArrayEquals(mossa6, menu.getMove("dxc5"));
			assertEquals(expectedBlackPieceCaptured,menu.Blackcaptured());
			assertEquals(expectedWhitePieceCaptured,menu.Whitecaptured());
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("b6");
			});
			assertThrows(IndexOutOfBoundsException.class, () -> {
				menu.getMove("t9");
			});
		});
		expectedWhitePieceCaptured.add("\u2659");
		expectedBlackPieceCaptured.add("\u265F");

		//test cattura ep per entrambi i colori, senza specificare ep

		assertAll("capture en passant without specifying", () -> {
			assertArrayEquals(mossa7, menu.getMove("d4"));
			assertArrayEquals(mossa8, menu.getMove("c4"));
			assertArrayEquals(mossa9, menu.getMove("f4"));
			assertArrayEquals(mossa10, menu.getMove("cxd3"));
			assertArrayEquals(mossa11, menu.getMove("a4"));
			assertArrayEquals(mossa12, menu.getMove("b5"));
			assertArrayEquals(mossa13, menu.getMove("a5"));
			assertArrayEquals(mossa14, menu.getMove("f5"));
			assertArrayEquals(mossa15, menu.getMove("axb6"));
			assertEquals(expectedBlackPieceCaptured,menu.Blackcaptured());
			assertEquals(expectedWhitePieceCaptured,menu.Whitecaptured());

		});

		assertEquals(expectedMoves,menu.moves());

		//test cattura su cella vuota

		assertAll("capture fail on empty cell", () -> {
			menu.play();
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("dxc3");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("fxg3");
			});

			menu.getMove("h4");

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("bxa6");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("exf6");
			});

		});

		//test cattura su pezzo alleato
		assertAll("capture on allied piece", () -> {
			menu.getMove("Ca6");
			menu.getMove("Ca3");
			menu.getMove("Ch6");
			menu.getMove("Ch3");

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("bxa6");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("gxh6");
			});

			menu.getMove("b5");

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("bxa3");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("gxh3");
			});
		});

		//test cattura che lascerebbe il re sotto scacco
		assertAll("capture on allied piece", () -> {

			//test lato bianchi
			assertThrows(IllegalMoveException.class, () -> {
				menu.play();
				menu.getMove("b3");
				menu.getMove("g6");
				menu.getMove("Aa3");
				menu.getMove("Ah6");
				menu.getMove("c3");
				menu.getMove("Rf8");
				menu.getMove("Dc2");
				menu.getMove("b5");
				menu.getMove("Rd1");
				menu.getMove("b4");
				menu.getMove("Rc1");
				menu.getMove("c5");
				menu.getMove("cxb4");
				menu.getMove("c4");
				menu.getMove("b5");
				menu.getMove("c3");
				menu.getMove("dxc3");

			});
			//test lato neri
			assertThrows(IllegalMoveException.class, () -> {
				menu.play();
				menu.getMove("b3");
				menu.getMove("g6");
				menu.getMove("Aa3");
				menu.getMove("Ah6");
				menu.getMove("c3");
				menu.getMove("Rf8");
				menu.getMove("Dc2");
				menu.getMove("b5");
				menu.getMove("Rd1");
				menu.getMove("b4");
				menu.getMove("Rc1");
				menu.getMove("c5");
				menu.getMove("cxb4");
				menu.getMove("c4");
				menu.getMove("b5");
				menu.getMove("c3");
				menu.getMove("f4");
				menu.getMove("a6");
				menu.getMove("f5");
				menu.getMove("a5");
				menu.getMove("f6");
				menu.getMove("exf6");

			});

		});
		//test cattura con lettera diversa da x
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("edc4");

		});

		//test cattura con colonna di partenza lontana da quella di arrivo
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("axf6");

		});
		//test cattura da pezzo diverso da pedone
		assertThrows(IllegalMoveException.class, () -> {
			menu.play();
			menu.getMove("axb2");

		});


	}
	//test cattura ep
	@Test
	void testCapturefromAPawnEP() {

		//test cattura ep semplice entrambi i lati

		expectedMoves.add("a4");
		expectedMoves.add("h5");
		expectedMoves.add("g4");
		expectedMoves.add("b5");
		expectedMoves.add("a5");
		expectedMoves.add("h4");
		expectedMoves.add("axb6");
		expectedMoves.add("hxg3");

		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "h5" };
		String[] mossa3 = { "\u2659", null, "g4" };
		String[] mossa4 = { "\u265F", null, "b5" };
		String[] mossa5 = { "\u2659", null, "a5" };
		String[] mossa6 = { "\u265F", null, "h4" };
		String[] mossa7 = { "\u2659", "\u265F", "b6 e.p." };
		String[] mossa8 = { "\u265F", "\u2659", "g3 e.p." };

		expectedBlackPieceCaptured.add("\u265F");
		expectedWhitePieceCaptured.add("\u2659");

		assertAll("capture en passant ", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("h5"));
			assertArrayEquals(mossa3, menu.getMove("g4"));
			assertArrayEquals(mossa4, menu.getMove("b5"));
			assertArrayEquals(mossa5, menu.getMove("a5"));
			assertArrayEquals(mossa6, menu.getMove("h4"));
			assertArrayEquals(mossa7, menu.getMove("axb6ep"));
			assertArrayEquals(mossa8, menu.getMove("hxg3ep"));

			assertEquals(expectedBlackPieceCaptured,menu.Blackcaptured());
			assertEquals(expectedWhitePieceCaptured,menu.Whitecaptured());

		});

		//test cattura ep non consentita
				assertThrows(IllegalMoveException.class, () -> {
					menu.play();
					menu.getMove("d3");
					menu.getMove("a6");
					menu.getMove("dxe4e.p.");

				});


		//test cattura ep pezzo non catturabile ep

		//test cattura ep da pezzo diverso da pedone

		//test cattura ep pezzo che lascia sotto scacco il re

		//test cattura con colonna di partenza lontana da quella di arrivo
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("axf6ep");

		});
	

	}

}
