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

		// array di mosse eseguite da confrontare

		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "b5" };
		String[] mossa3 = { "\u2659", null, "c3" };
		String[] mossa4 = { "\u265F", null, "d6" };

		//test mossa pedone su cella lecita

		assertAll("Moving pawns on lecit cells", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("b5"));
			assertArrayEquals(mossa3, menu.getMove("c3"));
			assertArrayEquals(mossa4, menu.getMove("d6"));
			assertEquals(expectedMoves,menu.moves());
		});
	}



	//test mossa illegale per pedone
	@Test
	void testIllegalPawnMove() {
		assertAll("Try a move on not lecit cell", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("f5");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("t9");
			});

		});

	}

	//test mossa lecita, ma cella già occupata
	@Test
	void testMoveOnOccupiedCell() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "a5" };
		assertAll("Try a move on occupied cell", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("a5"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("a5");
			});
		});


	}

	//test mossa Pedone che lascerebbe il re sotto scacco
	@Test
	void testPawnMoveThreatenedKing() {
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
		assertAll("Moving pawns on lecit cells", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("b5"));
			assertArrayEquals(mossa3, menu.getMove("c3"));
			assertArrayEquals(mossa4, menu.getMove("d6"));
			assertArrayEquals(mossa5, menu.getMove("axb5"));
			assertArrayEquals(mossa6, menu.getMove("a6"));
			assertArrayEquals(mossa7, menu.getMove("b4"));
			assertArrayEquals(mossa8, menu.getMove("a5"));
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
	}

	//test tentativo di mossa di un pezzo diverso da pedone, con notazione per pedone
	@Test
	void testMoveAPieceAsPawn() {
		String[] mossa1 = { "\u2659", null, "c4" };
		String[] mossa2 = { "\u265F", null, "d5" };

		assertAll("Test moving a different piece as a Pawn", () -> {
			assertArrayEquals(mossa1, menu.getMove("c4"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("c3");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("c2");
			});
			assertArrayEquals(mossa2, menu.getMove("d5"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("b6");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("b7");
			});
		});
	}

	//test tentativo di mossa lecita ma su pedone colore opposto
	@Test
	void testMoveDifferentColorPawn() {
		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "f5" };
		String[] mossa3 = { "\u2659", null, "h4" };
		assertAll("Test moving a Pawn with different color", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("f5"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("f6");
			});
			assertArrayEquals(mossa3, menu.getMove("h4"));

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("e3");
			});


		});


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
			assertEquals(expectedMoves,menu.moves());
		});
	}


	//test cattura ep per entrambi i colori, senza specificare ep
	@Test
	void testCaptureEPfromNormalCapture() {
		String[] mossa1 = { "\u2659", null, "d4" };
		String[] mossa2 = { "\u265F", null, "c5" };
		String[] mossa3 = { "\u2659", null, "d5" };
		String[] mossa4 = { "\u265F", null, "g5" };
		String[] mossa5 = { "\u2659", null, "h4" };
		String[] mossa6 = { "\u265F", null, "g4" };
		String[] mossa7 = { "\u2659", "\u265F", "c6 e.p." };
		String[] mossa8 = { "\u265F", "\u2659", "h3 e.p." };

		expectedWhitePieceCaptured.add("\u2659");
		expectedBlackPieceCaptured.add("\u265F");

		assertAll("capture en passant without specifying", () -> {
			assertArrayEquals(mossa1, menu.getMove("d4"));
			assertArrayEquals(mossa2, menu.getMove("c5"));
			assertArrayEquals(mossa3, menu.getMove("d5"));
			assertArrayEquals(mossa4, menu.getMove("g5"));
			assertArrayEquals(mossa5, menu.getMove("h4"));
			assertArrayEquals(mossa6, menu.getMove("g4"));
			assertArrayEquals(mossa7, menu.getMove("dxc6"));
			assertArrayEquals(mossa8, menu.getMove("gxh3"));
			assertEquals(expectedBlackPieceCaptured,menu.Blackcaptured());
			assertEquals(expectedWhitePieceCaptured,menu.Whitecaptured());

		});

	}

	//test cattura su cella vuota
	@Test
	void testCaptureOnEmptyCell() {
		String[] mossa1 = { "\u2659", null, "h4" };
		assertAll("capture fail on empty cell", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("dxc3");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("fxg3");
			});
			assertArrayEquals(mossa1, menu.getMove("h4"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("bxa6");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("exf6");
			});

		});
	}
	//test cattura su pezzo alleato
	@Test
	void testCaptureAlliedPiece() {

		String[] mossa1 = { "\u2658", null, "a3" };
		String[] mossa2 = { "\u265E", null, "a6" };
		String[] mossa3 = { "\u2658", null, "h3" };
		String[] mossa4 = { "\u265E", null, "h6" };
		String[] mossa5 = { "\u2659", null, "h4" };

		assertAll("test capture on allied piece", () -> {
			assertArrayEquals(mossa1, menu.getMove("Ca3"));
			assertArrayEquals(mossa2, menu.getMove("Ca6"));
			assertArrayEquals(mossa3, menu.getMove("Ch3"));
			assertArrayEquals(mossa4, menu.getMove("Ch6"));

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("bxa3");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("gxh3");
			});
			assertArrayEquals(mossa5, menu.getMove("h4"));

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("bxa6");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("gxh6");
			});		
		});
	}

	//test cattura che lascerebbe il re sotto scacco lato bianchi
	@Test
	void testCaptureThreatenedKingWhiteSide() {
		String[] mossa1 = { "\u2659", null, "b3" };
		String[] mossa2 = { "\u265F", null, "g6" };
		String[] mossa3 = { "\u2657", null, "a3" };
		String[] mossa4 = { "\u265D", null, "h6" };
		String[] mossa5 = { "\u2659", null, "c3" };
		String[] mossa6 = { "\u265A", null, "f8" };
		String[] mossa7 = { "\u2655", null, "c2" };
		String[] mossa8 = { "\u265F", null, "b5" };
		String[] mossa9 = { "\u2654", null, "d1" };
		String[] mossa10 = { "\u265F", null, "b4" };
		String[] mossa11 = { "\u2654", null, "c1" };
		String[] mossa12 = { "\u265F", null, "c5" };
		String[] mossa13 = { "\u2659", "\u265F", "b4" };
		String[] mossa14 = { "\u265F", null, "c4" };
		String[] mossa15 = { "\u2659", null, "b5" };
		String[] mossa16 = { "\u265F", null, "c3" };
		assertAll("capture with threatened King", () -> {
			assertArrayEquals(mossa1, menu.getMove("b3"));
			assertArrayEquals(mossa2, menu.getMove("g6"));
			assertArrayEquals(mossa3, menu.getMove("Aa3"));
			assertArrayEquals(mossa4, menu.getMove("Ah6"));
			assertArrayEquals(mossa5, menu.getMove("c3"));
			assertArrayEquals(mossa6, menu.getMove("Rf8"));
			assertArrayEquals(mossa7, menu.getMove("Dc2"));
			assertArrayEquals(mossa8, menu.getMove("b5"));
			assertArrayEquals(mossa9, menu.getMove("Rd1"));
			assertArrayEquals(mossa10, menu.getMove("b4"));
			assertArrayEquals(mossa11, menu.getMove("Rc1"));
			assertArrayEquals(mossa12, menu.getMove("c5"));
			assertArrayEquals(mossa13, menu.getMove("cxb4"));
			assertArrayEquals(mossa14, menu.getMove("c4"));
			assertArrayEquals(mossa15, menu.getMove("b5"));
			assertArrayEquals(mossa16, menu.getMove("c3"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("dxc3");
			});

		});
	}
	//test cattura che lascerebbe il re sotto scacco lato neri
	@Test
	void testCaptureThreatenedKingBlackSide() {
		String[] mossa1 = { "\u2659", null, "b3" };
		String[] mossa2 = { "\u265F", null, "g6" };
		String[] mossa3 = { "\u2657", null, "a3" };
		String[] mossa4 = { "\u265D", null, "h6" };
		String[] mossa5 = { "\u2659", null, "c3" };
		String[] mossa6 = { "\u265A", null, "f8" };
		String[] mossa7 = { "\u2655", null, "c2" };
		String[] mossa8 = { "\u265F", null, "b5" };
		String[] mossa9 = { "\u2654", null, "d1" };
		String[] mossa10 = { "\u265F", null, "b4" };
		String[] mossa11 = { "\u2654", null, "c1" };
		String[] mossa12 = { "\u265F", null, "c5" };
		String[] mossa13 = { "\u2659", "\u265F", "b4" };
		String[] mossa14 = { "\u265F", null, "c4" };
		String[] mossa15 = { "\u2659", null, "b5" };
		String[] mossa16 = { "\u265F", null, "c3" };
		String[] mossa17 = { "\u2659", null, "f4" };
		String[] mossa18 = { "\u265F", null, "a6" };
		String[] mossa19 = { "\u2659", null, "f5" };
		String[] mossa20 = { "\u265F", null, "a5" };
		String[] mossa21 = { "\u2659", null, "f6" };
		assertAll("capture with threatened King", () -> {
			assertArrayEquals(mossa1, menu.getMove("b3"));
			assertArrayEquals(mossa2, menu.getMove("g6"));
			assertArrayEquals(mossa3, menu.getMove("Aa3"));
			assertArrayEquals(mossa4, menu.getMove("Ah6"));
			assertArrayEquals(mossa5, menu.getMove("c3"));
			assertArrayEquals(mossa6, menu.getMove("Rf8"));
			assertArrayEquals(mossa7, menu.getMove("Dc2"));
			assertArrayEquals(mossa8, menu.getMove("b5"));
			assertArrayEquals(mossa9, menu.getMove("Rd1"));
			assertArrayEquals(mossa10, menu.getMove("b4"));
			assertArrayEquals(mossa11, menu.getMove("Rc1"));
			assertArrayEquals(mossa12, menu.getMove("c5"));
			assertArrayEquals(mossa13, menu.getMove("cxb4"));
			assertArrayEquals(mossa14, menu.getMove("c4"));
			assertArrayEquals(mossa15, menu.getMove("b5"));
			assertArrayEquals(mossa16, menu.getMove("c3"));
			assertArrayEquals(mossa17, menu.getMove("f4"));
			assertArrayEquals(mossa18, menu.getMove("a6"));
			assertArrayEquals(mossa19, menu.getMove("f5"));
			assertArrayEquals(mossa20, menu.getMove("a5"));
			assertArrayEquals(mossa21, menu.getMove("f6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("exf6");
			});

		});
	}


	//test cattura con lettera diversa da x
	@Test
	void testCaptureOnIllegalCell() {
		assertAll("Test on Illegal Cell", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("edc4");
			});
			//test cattura con colonna di partenza lontana da quella di arrivo
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("axf8");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("t9");
			});
			//test cattura da pezzo diverso da pedone
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("axb2");
			});
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
			assertArrayEquals(mossa8, menu.getMove("hxg3e.p."));

			assertEquals(expectedBlackPieceCaptured,menu.Blackcaptured());
			assertEquals(expectedWhitePieceCaptured,menu.Whitecaptured());

		});
	}

	//test cattura ep non consentita
	@Test
	void testNotAllowedEPCapture() {
		String[] mossa1 = { "\u2659", null, "d3" };
		String[] mossa2 = { "\u265F", null, "a6" };
		assertAll("test illegal EP capture", () -> {
			assertArrayEquals(mossa1, menu.getMove("d3"));
			assertArrayEquals(mossa2, menu.getMove("a6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("dxe4e.p.");
			});
		});
	}

	//test cattura ep pezzo non catturabile ep
	@Test
	void testNotEPCatturablePiece() {
		String[] mossa1 = { "\u2659", null, "f3" };
		String[] mossa2 = { "\u265F", null, "g5" };
		String[] mossa3 = { "\u2659", null, "f4" };
		String[] mossa4 = { "\u265F", null, "g4" };
		assertAll("capture en passant on not catturable ep piece ", () -> {
			assertArrayEquals(mossa1, menu.getMove("f3"));
			assertArrayEquals(mossa2, menu.getMove("g5"));
			assertArrayEquals(mossa3, menu.getMove("f4"));
			assertArrayEquals(mossa4, menu.getMove("g4"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("fxg5ep");
			});
		});
	}
	//test cattura ep da pezzo diverso da pedone
	@Test
	void testEPCaptureFromNotAPawn() {
		String[] mossa1 = { "\u2659", null, "d3" };
		String[] mossa2 = { "\u265F", null, "a6" };
		String[] mossa3 = { "\u2655", null, "d2" };
		String[] mossa4 = { "\u265F", null, "b5" };
		String[] mossa5 = { "\u2655", null, "c3" };
		String[] mossa6 = { "\u265F", null, "h6" };
		String[] mossa7 = { "\u2655", null, "c5" };
		String[] mossa8 = { "\u265F", null, "h5" };
		assertAll("test ep capture from a queen", () -> {
			assertArrayEquals(mossa1, menu.getMove("d3"));
			assertArrayEquals(mossa2, menu.getMove("a6"));
			assertArrayEquals(mossa3, menu.getMove("Dd2"));
			assertArrayEquals(mossa4, menu.getMove("b5"));
			assertArrayEquals(mossa5, menu.getMove("Dc3"));
			assertArrayEquals(mossa6, menu.getMove("h6"));
			assertArrayEquals(mossa7, menu.getMove("Dc5"));
			assertArrayEquals(mossa8, menu.getMove("h5"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("cxb6e.p.");
			});
		});
	}

	//test cattura ep pezzo che lascia sotto scacco il re
	@Test
	void testEpCaptureThreatenedKing() {
		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "f5" };
		String[] mossa3 = { "\u2659", null, "e5" };
		String[] mossa4 = { "\u265F", null, "e6" };
		String[] mossa5 = { "\u2659", null, "d4" };
		String[] mossa6 = { "\u265B", null, "e7" };
		String[] mossa7 = { "\u2659", null, "d5" };
		String[] mossa8 = { "\u265F", "\u2659", "d5" };

		assertAll("test ep that will leave King Threatened", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("f5"));
			assertArrayEquals(mossa3, menu.getMove("e5"));
			assertArrayEquals(mossa4, menu.getMove("e6"));
			assertArrayEquals(mossa5, menu.getMove("d4"));
			assertArrayEquals(mossa6, menu.getMove("De7"));
			assertArrayEquals(mossa7, menu.getMove("d5"));
			assertArrayEquals(mossa8, menu.getMove("exd5"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("exf6e.p.");
			});
		});


		//test cattura con colonna di partenza lontana da quella di arrivo
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("axf6ep");
		});
	}

	//test spostamento regina lecito
	@Test
	void testMoveQueen() {
		String[] mossa1 = { "\u2659", null, "d4" };
		String[] mossa2 = { "\u265F", null, "d5" };
		String[] mossa3 = { "\u2655", null, "d3" };
		String[] mossa4 = { "\u265B", null, "d6" };
		String[] mossa5 = { "\u2655", null, "a3" };
		String[] mossa6 = { "\u265B", null, "h6" };
		String[] mossa7 = { "\u2655", null, "d6" };
		String[] mossa8 = { "\u265B", null, "g5" };
		String[] mossa9 = { "\u2655", null, "g3" };
		String[] mossa10 = { "\u265B", null, "d2" };
		expectedMoves.add("d4");
		expectedMoves.add("d5");
		expectedMoves.add("Dd3");
		expectedMoves.add("Dd6");
		expectedMoves.add("Da3");
		expectedMoves.add("Dh6");
		expectedMoves.add("Dd6");
		expectedMoves.add("Dg5");
		expectedMoves.add("Dg3");
		expectedMoves.add("Dd2");

		assertAll("Moving Queen on lecit cells", () -> {
			assertArrayEquals(mossa1, menu.getMove("d4"));
			assertArrayEquals(mossa2, menu.getMove("d5"));
			assertArrayEquals(mossa3, menu.getMove("Dd3"));
			assertArrayEquals(mossa4, menu.getMove("Dd6"));
			assertArrayEquals(mossa5, menu.getMove("Da3"));
			assertArrayEquals(mossa6, menu.getMove("Dh6"));
			assertArrayEquals(mossa7, menu.getMove("Dd6"));
			assertArrayEquals(mossa8, menu.getMove("Dg5"));
			assertArrayEquals(mossa9, menu.getMove("Dg3"));
			assertArrayEquals(mossa10, menu.getMove("Dd2"));
			assertEquals(expectedMoves,menu.moves());

		});

	}
	//test mossa regina lecita ma ostacolata da un altro pezzo 
	@Test
	void testObstacledMoveQueen() {
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("Dd5");
		});
	}

	//test mossa regina su casella occupata da alleato
	@Test
	void testOccupiedCellMoveQueen() {
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("Dc2");
		});
	}

	//test mossa regina illegale
	@Test
	void testIllegalMoveQueen() {
		assertAll("Testing illegal moves for the Queen", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("De3");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dff");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("D44");
			});
		});
	}



}
