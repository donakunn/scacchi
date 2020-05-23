package it.uniba.scacchi.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
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
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("y9");
			});

		});

	}

	//test mossa lecita, ma cella gia'  occupata
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
			assertEquals(expectedBlackPieceCaptured,menu.blackCaptured());
			assertEquals(expectedWhitePieceCaptured,menu.whiteCaptured());
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
			assertEquals(expectedBlackPieceCaptured,menu.blackCaptured());
			assertEquals(expectedWhitePieceCaptured,menu.whiteCaptured());

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


	//test cattura illegale
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
			//test cattura da pezzo diverso da pedone
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("axb2");
			});
			//test cattura cella di destinazione fuori dai limiti della scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("bxy9");
			});
			//test cattura colonna di partenzafuori dai limiti della scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("zxb3");
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

			assertEquals(expectedBlackPieceCaptured,menu.blackCaptured());
			assertEquals(expectedWhitePieceCaptured,menu.whiteCaptured());

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
	}

	//test cattura illegale
	@Test
	void testCaptureOnIllegalCellEP() {
		assertAll("Test on Illegal Cell", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("edc4ep");
			});
			//test cattura con colonna di partenza lontana da quella di arrivo
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("axf8ep");
			});
			//test cattura da pezzo diverso da pedone
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("axb2ep");
			});
			//test cattura cella di destinazione fuori dai limiti della scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("bxy9ep");
			});
			//test cattura colonna di partenzafuori dai limiti della scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("zxb3ep");
			});
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
	void testOccupiedCellFromAlliedMoveQueen() {
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("Dc2");
		});
	}

	//test mossa regina casella occupata da pezzo nemico
	@Test
	void testOccupiedCellMoveQueen() {
		String[] mossa1 = { "\u2659", null, "d4" };
		String[] mossa2 = { "\u265F", null, "d5" };
		String[] mossa3 = { "\u2655", null, "d3" };
		String[] mossa4 = { "\u265B", null, "d6" };
		String[] mossa5 = { "\u2655", null, "c3" };
		String[] mossa6 = { "\u265B", null, "e6" };

		assertAll("Moving Queen on occupied from enemy cell", () -> {
			assertArrayEquals(mossa1, menu.getMove("d4"));
			assertArrayEquals(mossa2, menu.getMove("d5"));
			assertArrayEquals(mossa3, menu.getMove("Dd3"));
			assertArrayEquals(mossa4, menu.getMove("Dd6"));
			assertArrayEquals(mossa5, menu.getMove("Dc3"));
			assertArrayEquals(mossa6, menu.getMove("De6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dc7");
			});

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

	//test mossa regina che lascerebbe il re sotto scacco
	@Test
	void testMoveQueenThreatenedKing() {
		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "e5" };
		String[] mossa3 = { "\u2659", null, "d4" };
		String[] mossa4 = { "\u265F", null, "d5" };
		String[] mossa5 = { "\u2655", null, "e2" };
		String[] mossa6 = { "\u265F", "\u2659", "d4" };
		String[] mossa7 = { "\u2659", "\u265F", "d5" };
		String[] mossa8 = { "\u265B", null, "e7" };

		assertAll("Moving Queen that will leave King Threatened", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("e5"));
			assertArrayEquals(mossa3, menu.getMove("d4"));
			assertArrayEquals(mossa4, menu.getMove("d5"));
			assertArrayEquals(mossa5, menu.getMove("De2"));
			assertArrayEquals(mossa6, menu.getMove("exd4"));
			assertArrayEquals(mossa7, menu.getMove("exd5"));
			assertArrayEquals(mossa8, menu.getMove("De7"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Df3");
			});

		});
	}

	//test cattura semplice regina
	@Test
	void testCapturefromAQueen() {

		//avvaloro mosse attese

		expectedMoves.add("e4");
		expectedMoves.add("c5");
		expectedMoves.add("Dg4");
		expectedMoves.add("Db6");
		expectedMoves.add("Dxg7");
		expectedMoves.add("Dxb2");


		//pezzi catturati attesi

		expectedBlackPieceCaptured.add("\u265F");
		expectedWhitePieceCaptured.add("\u2659");

		//array mosse da confrontare con il risultato

		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "c5" };
		String[] mossa3 = { "\u2655", null, "g4" };
		String[] mossa4 = { "\u265B", null, "b6" };
		String[] mossa5 = { "\u2655", "\u265F", "g7" };
		String[] mossa6 = { "\u265B", "\u2659", "b2" };


		//test cattura semplice per entrambi i colori
		assertAll("Capture froma a Queen", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("c5"));
			assertArrayEquals(mossa3, menu.getMove("Dg4"));
			assertArrayEquals(mossa4, menu.getMove("Db6"));
			assertArrayEquals(mossa5, menu.getMove("Dxg7"));
			assertArrayEquals(mossa6, menu.getMove("Dxb2"));
			assertEquals(expectedBlackPieceCaptured,menu.blackCaptured());
			assertEquals(expectedWhitePieceCaptured,menu.whiteCaptured());
			assertEquals(expectedMoves,menu.moves());
		});
	}


	//test cattura su cella vuota
	@Test
	void testCaptureOnEmptyCellFromQueen() {
		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "c5" };
		String[] mossa3 = { "\u2655", null, "g4" };
		String[] mossa4 = { "\u265B", null, "b6" };
		assertAll("capture fail on empty cell from Queen", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("c5"));
			assertArrayEquals(mossa3, menu.getMove("Dg4"));
			assertArrayEquals(mossa4, menu.getMove("Db6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dxh5");
			});

		});
	}
	//test cattura su pezzo alleato
	@Test
	void testCaptureOnAlliedPieceFromQueen() {
		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "c5" };
		String[] mossa3 = { "\u2655", null, "g4" };
		String[] mossa4 = { "\u265B", null, "b6" };
		assertAll("capture fail on allied piece from Queen", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("c5"));
			assertArrayEquals(mossa3, menu.getMove("Dg4"));
			assertArrayEquals(mossa4, menu.getMove("Db6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dxg2");
			});

		});
	}

	//test cattura che lascerebbe il re sotto scacco
	@Test
	void testCaptureThreatenedKingFromQueen() {
		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "e5" };
		String[] mossa3 = { "\u2659", null, "f4" };
		String[] mossa4 = { "\u265F", null, "f5" };
		String[] mossa5 = { "\u2655", null, "e2" };
		String[] mossa6 = { "\u265B", null, "e7" };
		String[] mossa7 = { "\u2659", "\u265F", "f5" };
		String[] mossa8 = { "\u265F", "\u2659", "f4" };
		String[] mossa9 = { "\u2659", null, "a4" };
		String[] mossa10 = { "\u265F", null, "a6" };

		assertAll("capture from Queen with threatened King", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("e5"));
			assertArrayEquals(mossa3, menu.getMove("f4"));
			assertArrayEquals(mossa4, menu.getMove("f5"));
			assertArrayEquals(mossa5, menu.getMove("De2"));
			assertArrayEquals(mossa6, menu.getMove("De7"));
			assertArrayEquals(mossa7, menu.getMove("exf5"));
			assertArrayEquals(mossa8, menu.getMove("exf4"));
			assertArrayEquals(mossa9, menu.getMove("a4"));
			assertArrayEquals(mossa10, menu.getMove("a6"));

			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dxa6");
			});

		});
	}



	//test cattura illegale 
	@Test
	void testCaptureOnIllegalCellFromQueen() {
		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "c5" };
		String[] mossa3 = { "\u2655", null, "g4" };
		String[] mossa4 = { "\u265B", null, "b6" };
		assertAll("Test on Illegal Cell", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("c5"));
			assertArrayEquals(mossa3, menu.getMove("Dg4"));
			assertArrayEquals(mossa4, menu.getMove("Db6"));
			//test cattura con notazione errata ma cella corretta
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dbg7");
			});
			//test cattura su cella non consentita
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dxa3");
			});
			//test cattura con notazione cella oltre limiti scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dxq9");
			});
			//test cattura su pezzo di colore diverso, ma coperto da altro pezzo
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Dxb8");
			});
		});


	}

	//test spostamento Alfiere lecito
	@Test
	void testMoveBishop() {
		String[] mossa1 = { "\u2659", null, "b3" };
		String[] mossa2 = { "\u265F", null, "b6" };
		String[] mossa3 = { "\u2659", null, "g3" };
		String[] mossa4 = { "\u265F", null, "g6" };
		String[] mossa5 = { "\u2657", null, "a3" };
		String[] mossa6 = { "\u265D", null, "a6" };
		String[] mossa7 = { "\u2657", null, "h3" };
		String[] mossa8 = { "\u265D", null, "h6" };
		String[] mossa9 = { "\u2657", null, "d6" };
		String[] mossa10 = { "\u265D", null, "d3" };
		String[] mossa11 = { "\u2657", null, "e6" };
		String[] mossa12 = { "\u265D", null, "e3" };
		expectedMoves.add("b3");
		expectedMoves.add("b6");
		expectedMoves.add("g3");
		expectedMoves.add("g6");
		expectedMoves.add("Aa3");
		expectedMoves.add("Aa6");
		expectedMoves.add("Ah3");
		expectedMoves.add("Ah6");
		expectedMoves.add("Ad6");
		expectedMoves.add("Ad3");
		expectedMoves.add("Ae6");
		expectedMoves.add("Ae3");

		assertAll("Moving Bishop on lecit cells", () -> {
			assertArrayEquals(mossa1, menu.getMove("b3"));
			assertArrayEquals(mossa2, menu.getMove("b6"));
			assertArrayEquals(mossa3, menu.getMove("g3"));
			assertArrayEquals(mossa4, menu.getMove("g6"));
			assertArrayEquals(mossa5, menu.getMove("Aa3"));
			assertArrayEquals(mossa6, menu.getMove("Aa6"));
			assertArrayEquals(mossa7, menu.getMove("Ah3"));
			assertArrayEquals(mossa8, menu.getMove("Ah6"));
			assertArrayEquals(mossa9, menu.getMove("Ad6"));
			assertArrayEquals(mossa10, menu.getMove("Ad3"));
			assertArrayEquals(mossa11, menu.getMove("Ae6"));
			assertArrayEquals(mossa12, menu.getMove("Ae3"));
			assertEquals(expectedMoves,menu.moves());

		});

	}
	//test mossa alfiere lecita ma ostacolata da un altro pezzo 
	@Test
	void testObstacledMoveBishop() {
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("Aa3");
		});
	}

	//test mossa alfiere su casella occupata da alleato
	@Test
	void testOccupiedCellFromAlliedMoveBishop() {
		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("Ag2");
		});
	}


	//test mossa alfiere casella occupata da pezzo nemico
	@Test
	void testOccupiedCellMoveBishop() {
		String[] mossa1 = { "\u2659", null, "g3" };
		String[] mossa2 = { "\u265F", null, "a6" };
		String[] mossa3 = { "\u2657", null, "h3" };
		String[] mossa4 = { "\u265F", null, "b6" };

		assertAll("Moving Bishop on lecit cell, but occupied from an enemy piece", () -> {
			assertArrayEquals(mossa1, menu.getMove("g3"));
			assertArrayEquals(mossa2, menu.getMove("a6"));
			assertArrayEquals(mossa3, menu.getMove("Ah3"));
			assertArrayEquals(mossa4, menu.getMove("b6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ad7");
			});

		});
	}

	//test mossa alfiere illegale
	@Test
	void testIllegalMoveBishop() {
		assertAll("Testing illegal moves for the Bishop", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ae3");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Agg");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("A67");
			});
		});
	}

	//test mossa alfiere che lascerebbe il re sotto scacco
	@Test
	void testMoveBishopThreatenedKing() {
		String[] mossa1 = { "\u2659", null, "e3" };
		String[] mossa2 = { "\u265F", null, "d6" };
		String[] mossa3 = { "\u2657", null, "e2" };
		String[] mossa4 = { "\u265D", null, "g4" };
		String[] mossa5 = { "\u2659", null, "c3" };
		String[] mossa6 = { "\u265F", null, "a6" };
		String[] mossa7 = { "\u2655", null, "c2" };
		String[] mossa8 = { "\u265F", null, "b6" };
		String[] mossa9 = { "\u2654", null, "d1" };
		String[] mossa10 = { "\u265F", null, "c6" };

		assertAll("Moving Bishop that will leave King threatened", () -> {
			assertArrayEquals(mossa1, menu.getMove("e3"));
			assertArrayEquals(mossa2, menu.getMove("d6"));
			assertArrayEquals(mossa3, menu.getMove("Ae2"));
			assertArrayEquals(mossa4, menu.getMove("Ag4"));
			assertArrayEquals(mossa5, menu.getMove("c3"));
			assertArrayEquals(mossa6, menu.getMove("a6"));
			assertArrayEquals(mossa7, menu.getMove("Dc2"));
			assertArrayEquals(mossa8, menu.getMove("b6"));
			assertArrayEquals(mossa9, menu.getMove("Rd1"));
			assertArrayEquals(mossa10, menu.getMove("c6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ad3");
			});

		});
	}

	//test cattura semplice Alfiere

	@Test
	void testCapturefromABishop() {

		//avvaloro mosse attese

		expectedMoves.add("b3");
		expectedMoves.add("g6");
		expectedMoves.add("Aa3");
		expectedMoves.add("Ah6");
		expectedMoves.add("Axe7");
		expectedMoves.add("Axd2");


		//pezzi catturati attesi

		expectedBlackPieceCaptured.add("\u265F");
		expectedWhitePieceCaptured.add("\u2659");

		//array mosse da confrontare con il risultato

		String[] mossa1 = { "\u2659", null, "b3" };
		String[] mossa2 = { "\u265F", null, "g6" };
		String[] mossa3 = { "\u2657", null, "a3" };
		String[] mossa4 = { "\u265D", null, "h6" };
		String[] mossa5 = { "\u2657", "\u265F", "e7" };
		String[] mossa6 = { "\u265D", "\u2659", "d2" };


		//test cattura semplice per entrambi i colori
		assertAll("Capture from, Bishops", () -> {
			assertArrayEquals(mossa1, menu.getMove("b3"));
			assertArrayEquals(mossa2, menu.getMove("g6"));
			assertArrayEquals(mossa3, menu.getMove("Aa3"));
			assertArrayEquals(mossa4, menu.getMove("Ah6"));
			assertArrayEquals(mossa5, menu.getMove("Axe7"));
			assertArrayEquals(mossa6, menu.getMove("Axd2"));
			assertEquals(expectedBlackPieceCaptured,menu.blackCaptured());
			assertEquals(expectedWhitePieceCaptured,menu.whiteCaptured());
			assertEquals(expectedMoves,menu.moves());
		});
	}


	//test cattura su cella vuota
	@Test
	void testCaptureOnEmptyCellFromBishop() {
		String[] mossa1 = { "\u2659", null, "b3" };
		String[] mossa2 = { "\u265F", null, "g6" };
		String[] mossa3 = { "\u2657", null, "a3" };
		String[] mossa4 = { "\u265D", null, "h6" };
		assertAll("capture fail on empty cell from Bishop", () -> {
			assertArrayEquals(mossa1, menu.getMove("b3"));
			assertArrayEquals(mossa2, menu.getMove("g6"));
			assertArrayEquals(mossa3, menu.getMove("Aa3"));
			assertArrayEquals(mossa4, menu.getMove("Ah6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Axc5");
			});

		});
	}
	//test cattura su pezzo alleato
	@Test
	void testCaptureOnAlliedPieceFromBishop() {

		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("Axb2");
		});
	}

	//test cattura che lascerebbe il re sotto scacco
	@Test
	void testCaptureThreatenedKingFromBishop() {
		String[] mossa1 = { "\u2659", null, "e3" };
		String[] mossa2 = { "\u265F", null, "d6" };
		String[] mossa3 = { "\u2657", null, "e2" };
		String[] mossa4 = { "\u265D", null, "g4" };
		String[] mossa5 = { "\u2659", null, "c3" };
		String[] mossa6 = { "\u265F", null, "a6" };
		String[] mossa7 = { "\u2655", null, "c2" };
		String[] mossa8 = { "\u265F", null, "b6" };
		String[] mossa9 = { "\u2654", null, "d1" };
		String[] mossa10 = { "\u265F", null, "c6" };

		assertAll("Capture from Bishop that will leave King threatened", () -> {
			assertArrayEquals(mossa1, menu.getMove("e3"));
			assertArrayEquals(mossa2, menu.getMove("d6"));
			assertArrayEquals(mossa3, menu.getMove("Ae2"));
			assertArrayEquals(mossa4, menu.getMove("Ag4"));
			assertArrayEquals(mossa5, menu.getMove("c3"));
			assertArrayEquals(mossa6, menu.getMove("a6"));
			assertArrayEquals(mossa7, menu.getMove("Dc2"));
			assertArrayEquals(mossa8, menu.getMove("b6"));
			assertArrayEquals(mossa9, menu.getMove("Rd1"));
			assertArrayEquals(mossa10, menu.getMove("c6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Axa6");
			});

		});
	}



	//test cattura illegale 
	@Test
	void testCaptureOnIllegalCellFromBishop() {
		String[] mossa1 = { "\u2659", null, "d3" };
		String[] mossa2 = { "\u265F", null, "e6" };
		String[] mossa3 = { "\u2657", null, "f4" };
		String[] mossa4 = { "\u265D", null, "d6" };
		assertAll("Test on Illegal Cell", () -> {
			assertArrayEquals(mossa1, menu.getMove("d3"));
			assertArrayEquals(mossa2, menu.getMove("e6"));
			assertArrayEquals(mossa3, menu.getMove("Af4"));
			assertArrayEquals(mossa4, menu.getMove("Ad6"));
			//test cattura con notazione errata ma cella corretta
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Acd6");
			});
			//test cattura su cella non consentita
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Axb3");
			});
			//test cattura con notazione cella oltre limiti scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Axq9");
			});
			//test cattura su pezzo di colore diverso, ma coperto da altro pezzo
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Axc7");
			});
		});


	}

	//test spostamento Torre lecito
	@Test
	void testMoveRook() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "h5" };
		String[] mossa3 = { "\u2656", null, "a3" };
		String[] mossa4 = { "\u265C", null, "h6" };
		String[] mossa5 = { "\u2656", null, "h3" };
		String[] mossa6 = { "\u265C", null, "a6" };
		expectedMoves.add("a4");
		expectedMoves.add("h5");
		expectedMoves.add("Ta3");
		expectedMoves.add("Th6");
		expectedMoves.add("Th3");
		expectedMoves.add("Ta6");

		assertAll("Moving Rook on lecit cells", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("h5"));
			assertArrayEquals(mossa3, menu.getMove("Ta3"));
			assertArrayEquals(mossa4, menu.getMove("Th6"));
			assertArrayEquals(mossa5, menu.getMove("Th3"));
			assertArrayEquals(mossa6, menu.getMove("Ta6"));
			assertEquals(expectedMoves,menu.moves());

		});

	}

	//test mossa torre lecita ma ostacolata da un altro pezzo 
	@Test
	void testObstacledMoveRook() {
		assertAll("Test lecit move but obstacled, with and without ambiguous notation", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ta5");
			});
			//notazione disambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Taa5");
			});

		});

	}

	//test mossa torre su casella occupata da alleato
	@Test
	void testOccupiedCellFromAlliedRook() {
		assertAll("Test move on allied piece cell, with and without ambiguous notation", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tb1");
			});
			//notazione disambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Thg2");
			});

		});
	}

	//test mossa ambigua
	@Test
	void testAmbiguousMoveRook() {
		String[] mossa1 = { "\u2659", null, "a3" };
		String[] mossa2 = { "\u265F", null, "a5" };
		String[] mossa3 = { "\u2659", null, "b3" };
		String[] mossa4 = { "\u265F", null, "h5" };
		String[] mossa5 = { "\u2659", null, "c3" };
		String[] mossa6 = { "\u265C", null, "a6" };
		String[] mossa7 = { "\u2659", null, "d3" };
		String[] mossa8 = { "\u265C", null, "h6" };

		assertAll("Moving Rook with and without ambiguous notation", () -> {
			assertArrayEquals(mossa1, menu.getMove("a3"));
			assertArrayEquals(mossa2, menu.getMove("a5"));
			assertArrayEquals(mossa3, menu.getMove("b3"));
			assertArrayEquals(mossa4, menu.getMove("h5"));
			assertArrayEquals(mossa5, menu.getMove("c3"));
			assertArrayEquals(mossa6, menu.getMove("Ta6"));
			assertArrayEquals(mossa7, menu.getMove("d3"));
			//solleva eccezione, mossa ambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Th6");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tbh6");
			});
			//notazione disambigua
			assertArrayEquals(mossa8, menu.getMove("Tah6"));
		});
	}	
	//test mossa ambigua, e disambiguazione con riga o colonna sbagliata
	@Test
	void testAmbiguousMoveRookWrongRowAndColumns() {
		String[] mossa1 = { "\u2659", null, "a3" };
		String[] mossa2 = { "\u265F", null, "a5" };
		String[] mossa3 = { "\u2659", null, "b3" };
		String[] mossa4 = { "\u265F", null, "h5" };
		String[] mossa5 = { "\u2659", null, "c3" };
		String[] mossa6 = { "\u265C", null, "a6" };
		String[] mossa7 = { "\u2659", null, "d3" };

		assertAll("Throwing exception for wrong row or columns for disambiguation", () -> {
			assertArrayEquals(mossa1, menu.getMove("a3"));
			assertArrayEquals(mossa2, menu.getMove("a5"));
			assertArrayEquals(mossa3, menu.getMove("b3"));
			assertArrayEquals(mossa4, menu.getMove("h5"));
			assertArrayEquals(mossa5, menu.getMove("c3"));
			assertArrayEquals(mossa6, menu.getMove("Ta6"));
			assertArrayEquals(mossa7, menu.getMove("d3"));
			//solleva eccezione, mossa disambigua con riga sbagliata
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("T3h6");
			});
			//solleva eccezione, mossa disambigua con colonna sbagliata
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("T3h6");
			});
			//solleva eccezione, mossa disambigua con colonna fuori dai limiti
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tyh6");
			});
		});
	}


	//test mossa ambigua stessa riga
	@Test
	void testAmbiguousOnRowMoveRook() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "a5" };
		String[] mossa3 = { "\u2659", null, "h4" };
		String[] mossa4 = { "\u265F", null, "h5" };
		String[] mossa5 = { "\u2656", null, "a3" };
		String[] mossa6 = { "\u265C", null, "a6" };
		String[] mossa7 = { "\u2656", null, "h3" };
		String[] mossa8 = { "\u265C", null, "h6" };
		String[] mossa9 = { "\u2656", null, "c3" };

		assertAll("Moving Rook with and without ambiguous notation", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("a5"));
			assertArrayEquals(mossa3, menu.getMove("h4"));
			assertArrayEquals(mossa4, menu.getMove("h5"));
			assertArrayEquals(mossa5, menu.getMove("Ta3"));
			assertArrayEquals(mossa6, menu.getMove("Ta6"));
			assertArrayEquals(mossa7, menu.getMove("Thh3"));
			assertArrayEquals(mossa8, menu.getMove("Thh6"));
			//solleva eccezione, mossa ambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("T3c3");
			});
			//notazione disambigua
			assertArrayEquals(mossa9, menu.getMove("Tac3"));
		});
	}	
	//test mossa ambigua stessa colonna muovendo la prima torre
	@Test
	void testAmbiguousOnColumnMoveRook() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "a5" };
		String[] mossa3 = { "\u2659", null, "h4" };
		String[] mossa4 = { "\u265F", null, "h5" };
		String[] mossa5 = { "\u2656", null, "a3" };
		String[] mossa6 = { "\u265C", null, "a6" };
		String[] mossa7 = { "\u2656", null, "h3" };
		String[] mossa8 = { "\u265F", null, "b6" };
		String[] mossa9 = { "\u2656", null, "h2" };

		assertAll("Moving Rook with and without ambiguous notation", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("a5"));
			assertArrayEquals(mossa3, menu.getMove("h4"));
			assertArrayEquals(mossa4, menu.getMove("h5"));
			assertArrayEquals(mossa5, menu.getMove("Ta3"));
			assertArrayEquals(mossa6, menu.getMove("Ta6"));
			assertArrayEquals(mossa7, menu.getMove("Tah3"));
			assertArrayEquals(mossa8, menu.getMove("b6"));
			//solleva eccezione, mossa ambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Thh2");
			});
			assertArrayEquals(mossa9, menu.getMove("T3h2"));
		});
	}	
	//test mossa ambigua stessa colonna muovendo la seconda torre 
	@Test
	void testAmbiguousOnColumnMoveRook2() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "a5" };
		String[] mossa3 = { "\u2659", null, "h4" };
		String[] mossa4 = { "\u265F", null, "h5" };
		String[] mossa5 = { "\u2656", null, "a3" };
		String[] mossa6 = { "\u265C", null, "a6" };
		String[] mossa7 = { "\u2656", null, "h3" };
		String[] mossa8 = { "\u265F", null, "b6" };
		String[] mossa9 = { "\u2656", null, "h2" };

		assertAll("Moving Rook with and without ambiguous notation", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("a5"));
			assertArrayEquals(mossa3, menu.getMove("h4"));
			assertArrayEquals(mossa4, menu.getMove("h5"));
			assertArrayEquals(mossa5, menu.getMove("Ta3"));
			assertArrayEquals(mossa6, menu.getMove("Ta6"));
			assertArrayEquals(mossa7, menu.getMove("Tah3"));
			assertArrayEquals(mossa8, menu.getMove("b6"));
			//solleva eccezione, mossa ambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Thh2");
			});
			assertArrayEquals(mossa9, menu.getMove("T1h2"));
		});
	}	


	//test mossa torre casella occupata da pezzo nemico
	@Test
	void testOccupiedCellMoveRook() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "a5" };
		String[] mossa3 = { "\u2656", null, "a3" };
		String[] mossa4 = { "\u265F", null, "b6" };
		String[] mossa5 = { "\u2656", null, "b3" };
		String[] mossa6 = { "\u265F", null, "b5" };

		assertAll("Moving Rook on lecit cell, but occupied from an enemy piece", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("a5"));
			assertArrayEquals(mossa3, menu.getMove("Ta3"));
			assertArrayEquals(mossa4, menu.getMove("b6"));
			assertArrayEquals(mossa5, menu.getMove("Tb3"));
			assertArrayEquals(mossa6, menu.getMove("b5"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tb5");
			});

		});
	}

	//test mossa torre illegale
	@Test
	void testIllegalMoveRook() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "a5" };
		String[] mossa3 = { "\u2656", null, "a3" };
		String[] mossa4 = { "\u265F", null, "b6" };
		String[] mossa5 = { "\u2659", null, "c3" };
		String[] mossa6 = { "\u265F", null, "b5" };
		assertAll("Testing illegal moves for Rook", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("a5"));
			assertArrayEquals(mossa3, menu.getMove("Ta3"));
			assertArrayEquals(mossa4, menu.getMove("b6"));
			assertArrayEquals(mossa5, menu.getMove("c3"));
			assertArrayEquals(mossa6, menu.getMove("b5"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tc4");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tgh");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("T88");
			});
			//con notazione disambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tac4");
			});
			//cella di destinazione invalida
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Thgh");
			});
			//cella di destinazione oltre i limiti
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ta88");
			});
			//nessuna torre può muoversi da riga di partenza
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("T5c5");
			});
			//nessuna torre può muoversi da colonna di partenza
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tdd4");
			});
			//riga di partenza oltre limiti scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("T9f5");
			});
			//colonna di partenza oltre limiti scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tyf5");
			});
			//Test mossa su se stesso
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("T3a3");
			});
			//Test mossa percorso ostacolato
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Td3");
			});
		});
	}

	//test mossa torre che lascerebbe il re sotto scacco
	@Test
	void testMoveRookThreatenedKing() {
		String[] mossa1 = { "\u2659", null, "e4" };
		String[] mossa2 = { "\u265F", null, "e5" };
		String[] mossa3 = { "\u2659", null, "d4" };
		String[] mossa4 = { "\u265F", null, "d5" };
		String[] mossa5 = { "\u2659", "\u265F", "d5" };
		String[] mossa6 = { "\u265F", "\u2659", "d4" };
		String[] mossa7 = { "\u2659", null, "a3" };
		String[] mossa8 = { "\u265F", null, "h5" };
		String[] mossa9 = { "\u2659", null, "b3" };
		String[] mossa10 = { "\u265C", null, "h6" };
		String[] mossa11 = { "\u2659", null, "b4" };
		String[] mossa12 = { "\u265C", null, "e6" };
		String[] mossa13 = { "\u2654", null, "d2" };
		String[] mossa14 = { "\u265F", null, "a6" };
		String[] mossa15 = { "\u2655", null, "e1" };

		assertAll("Moving Rook that will leave King threatened", () -> {
			assertArrayEquals(mossa1, menu.getMove("e4"));
			assertArrayEquals(mossa2, menu.getMove("e5"));
			assertArrayEquals(mossa3, menu.getMove("d4"));
			assertArrayEquals(mossa4, menu.getMove("d5"));
			assertArrayEquals(mossa5, menu.getMove("exd5"));
			assertArrayEquals(mossa6, menu.getMove("exd4"));
			assertArrayEquals(mossa7, menu.getMove("a3"));
			assertArrayEquals(mossa8, menu.getMove("h5"));
			assertArrayEquals(mossa9, menu.getMove("b3"));
			assertArrayEquals(mossa10, menu.getMove("Th6"));
			assertArrayEquals(mossa11, menu.getMove("b4"));
			assertArrayEquals(mossa12, menu.getMove("Te6"));
			assertArrayEquals(mossa13, menu.getMove("Rd2"));
			assertArrayEquals(mossa14, menu.getMove("a6"));
			assertArrayEquals(mossa15, menu.getMove("De1"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tc6");
			});

		});
	}

	//test cattura semplice Torre

	@Test
	void testCapturefromARook() {

		//avvaloro mosse attese

		expectedMoves.add("a4");
		expectedMoves.add("h5");
		expectedMoves.add("Ta3");
		expectedMoves.add("Th6");
		expectedMoves.add("Tb3");
		expectedMoves.add("Tg6");
		expectedMoves.add("Txb7");
		expectedMoves.add("Txg2");


		//pezzi catturati attesi

		expectedBlackPieceCaptured.add("\u265F");
		expectedWhitePieceCaptured.add("\u2659");

		//array mosse da confrontare con il risultato

		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "h5" };
		String[] mossa3 = { "\u2656", null, "a3" };
		String[] mossa4 = { "\u265C", null, "h6" };
		String[] mossa5 = { "\u2656", null, "b3" };
		String[] mossa6 = { "\u265C", null, "g6" };
		String[] mossa7 = { "\u2656", "\u265F", "b7" };
		String[] mossa8 = { "\u265C", "\u2659", "g2" };


		//test cattura semplice per entrambi i colori
		assertAll("Capture from Rooks", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("h5"));
			assertArrayEquals(mossa3, menu.getMove("Ta3"));
			assertArrayEquals(mossa4, menu.getMove("Th6"));
			assertArrayEquals(mossa5, menu.getMove("Tb3"));
			assertArrayEquals(mossa6, menu.getMove("Tg6"));
			assertArrayEquals(mossa7, menu.getMove("Txb7"));
			assertArrayEquals(mossa8, menu.getMove("Txg2"));
			assertEquals(expectedBlackPieceCaptured,menu.blackCaptured());
			assertEquals(expectedWhitePieceCaptured,menu.whiteCaptured());
			assertEquals(expectedMoves,menu.moves());
		});
	}


	//test cattura su cella vuota
	@Test
	void testCaptureOnEmptyCellFromRook() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "h5" };
		String[] mossa3 = { "\u2656", null, "a3" };
		String[] mossa4 = { "\u265C", null, "h6" };
		assertAll("capture fail on empty cell from Rook", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("h5"));
			assertArrayEquals(mossa3, menu.getMove("Ta3"));
			assertArrayEquals(mossa4, menu.getMove("Th6"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Txd3");
			});

		});
	}
	//test cattura su pezzo alleato
	@Test
	void testCaptureOnAlliedPieceFromRook() {

		assertThrows(IllegalMoveException.class, () -> {
			menu.getMove("Txb1");
		});
	}

	//test cattura che lascerebbe il re sotto scacco
	@Test
	void testCaptureThreatenedKingFromRook() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "h5" };
		String[] mossa3 = { "\u2656", null, "a3" };
		String[] mossa4 = { "\u265C", null, "h6" };
		String[] mossa5 = { "\u2659", null, "e4" };
		String[] mossa6 = { "\u265F", null, "e5" };
		String[] mossa7 = { "\u2659", null, "d4" };
		String[] mossa8 = { "\u265F", null, "d5" };
		String[] mossa9 = { "\u2659", "\u265F", "d5" };
		String[] mossa10 = { "\u265F", "\u2659", "d4" };
		String[] mossa11 = { "\u2656", null, "e3" };
		String[] mossa12 = { "\u265C", null, "e6" };
		String[] mossa13 = { "\u2659", null, "a5" };
		String[] mossa14 = { "\u265F", null, "d3" };

		assertAll("Capture from Rook that will leave King threatened", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("h5"));
			assertArrayEquals(mossa3, menu.getMove("Ta3"));
			assertArrayEquals(mossa4, menu.getMove("Th6"));
			assertArrayEquals(mossa5, menu.getMove("e4"));
			assertArrayEquals(mossa6, menu.getMove("e5"));
			assertArrayEquals(mossa7, menu.getMove("d4"));
			assertArrayEquals(mossa8, menu.getMove("d5"));
			assertArrayEquals(mossa9, menu.getMove("exd5"));
			assertArrayEquals(mossa10, menu.getMove("exd4"));
			assertArrayEquals(mossa11, menu.getMove("Te3"));
			assertArrayEquals(mossa12, menu.getMove("Te6"));
			assertArrayEquals(mossa13, menu.getMove("a5"));
			assertArrayEquals(mossa14, menu.getMove("d3"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Txd3");
			});

		});
	}



	//test cattura illegale 
	@Test
	void testCaptureOnIllegalCellFromRook() {
		String[] mossa1 = { "\u2659", null, "a4" };
		String[] mossa2 = { "\u265F", null, "h5" };
		String[] mossa3 = { "\u2656", null, "a3" };
		String[] mossa4 = { "\u265C", null, "h6" };
		String[] mossa5 = { "\u2656", null, "b3" };
		String[] mossa6 = { "\u265C", null, "g6" };
		assertAll("Test on Illegal Cell", () -> {
			assertArrayEquals(mossa1, menu.getMove("a4"));
			assertArrayEquals(mossa2, menu.getMove("h5"));
			assertArrayEquals(mossa3, menu.getMove("Ta3"));
			assertArrayEquals(mossa4, menu.getMove("Th6"));
			assertArrayEquals(mossa5, menu.getMove("Tb3"));
			assertArrayEquals(mossa6, menu.getMove("Tg6"));
			//test cattura con notazione errata ma cella corretta
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Tcb7");
			});
			//test cattura su cella non consentita
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Txc4");
			});
			//test cattura con notazione cella oltre limiti scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Txq9");
			});
			//test cattura su pezzo di colore diverso, ma coperto da altro pezzo
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Txb8");
			});
			//test cattura su se stesso
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Txb3");
			});
		});


	}

	//test spostamento Cavallo lecito
	@Test
	void testMoveKnight() {
		String[] mossa1 = { "\u2658", null, "c3" };
		String[] mossa2 = { "\u265E", null, "a6" };
		String[] mossa3 = { "\u2658", null, "f3" };
		String[] mossa4 = { "\u265E", null, "h6" };
		String[] mossa5 = { "\u2658", null, "e4" };
		String[] mossa6 = { "\u265E", null, "c5" };
		String[] mossa7 = { "\u2658", null, "d4" };
		String[] mossa8 = { "\u265E", null, "f5" };
		expectedMoves.add("Cc3");
		expectedMoves.add("Ca6");
		expectedMoves.add("Cf3");
		expectedMoves.add("Ch6");
		expectedMoves.add("Ce4");
		expectedMoves.add("Cc5");
		expectedMoves.add("Cd4");
		expectedMoves.add("Cf5");

		assertAll("Moving Knight on lecit cells", () -> {
			assertArrayEquals(mossa1, menu.getMove("Cc3"));
			assertArrayEquals(mossa2, menu.getMove("Ca6"));
			assertArrayEquals(mossa3, menu.getMove("Cf3"));
			assertArrayEquals(mossa4, menu.getMove("Ch6"));
			assertArrayEquals(mossa5, menu.getMove("Ce4"));
			assertArrayEquals(mossa6, menu.getMove("Cc5"));
			assertArrayEquals(mossa7, menu.getMove("Cd4"));
			assertArrayEquals(mossa8, menu.getMove("Cf5"));
			assertEquals(expectedMoves,menu.moves());

		});

	}


	//test mossa torre su casella occupata da alleato
	@Test
	void testOccupiedCellFromAlliedKnight() {
		assertAll("Test move on allied piece cell", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cd2");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ce2");
			});

		});
	}

	//test mossa ambigua stessa colonna, muovendo primo cavallo
	@Test
	void testAmbiguousRowMoveFirstKnight() {
		String[] mossa1 = { "\u2658", null, "c3" };
		String[] mossa2 = { "\u265F", null, "a6" };
		String[] mossa3 = { "\u2658", null, "f3" };
		String[] mossa4 = { "\u265F", null, "a5" };
		String[] mossa5 = { "\u2658", null, "e5" };
		String[] mossa6 = { "\u265F", null, "a4" };
		String[] mossa7 = { "\u2658", null, "d5" };
		String[] mossa8 = { "\u265F", null, "a3" };
		String[] mossa9 = { "\u2658", null, "e3" };
		String[] mossa10 = { "\u265F", null, "b6" };
		String[] mossa11 = { "\u2658", null, "c4" };


		assertAll("Moving Knight with and without ambiguous notation", () -> {
			assertArrayEquals(mossa1, menu.getMove("Cc3"));
			assertArrayEquals(mossa2, menu.getMove("a6"));
			assertArrayEquals(mossa3, menu.getMove("Cf3"));
			assertArrayEquals(mossa4, menu.getMove("a5"));
			assertArrayEquals(mossa5, menu.getMove("Ce5"));
			assertArrayEquals(mossa6, menu.getMove("a4"));
			assertArrayEquals(mossa7, menu.getMove("Cd5"));
			assertArrayEquals(mossa8, menu.getMove("a3"));
			assertArrayEquals(mossa9, menu.getMove("Ce3"));
			assertArrayEquals(mossa10, menu.getMove("b6"));
			//solleva eccezione, mossa ambigua entrambe possibili celle
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cc4");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cg4");
			});
			//notazione disambigua con riga sbagliata
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C6g4");
			});
			//notazione disambigua dando colonna di partenza
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ceg4");
			});
			//notazione disambigua dando riga di partenza fuori dai limiti
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C9g4");
			});
			//notazione disambigua
			assertArrayEquals(mossa11, menu.getMove("C5c4"));
		});
	}
	//test mossa ambigua stessa colonna, muovendo secondo cavallo
	@Test
	void testAmbiguousRowMoveSecondKnight() {
		String[] mossa1 = { "\u2658", null, "c3" };
		String[] mossa2 = { "\u265F", null, "a6" };
		String[] mossa3 = { "\u2658", null, "f3" };
		String[] mossa4 = { "\u265F", null, "a5" };
		String[] mossa5 = { "\u2658", null, "e5" };
		String[] mossa6 = { "\u265F", null, "a4" };
		String[] mossa7 = { "\u2658", null, "d5" };
		String[] mossa8 = { "\u265F", null, "a3" };
		String[] mossa9 = { "\u2658", null, "e3" };
		String[] mossa10 = { "\u265F", null, "b6" };
		String[] mossa11 = { "\u2658", null, "g4" };


		assertAll("Moving Knight with and without ambiguous notation", () -> {
			assertArrayEquals(mossa1, menu.getMove("Cc3"));
			assertArrayEquals(mossa2, menu.getMove("a6"));
			assertArrayEquals(mossa3, menu.getMove("Cf3"));
			assertArrayEquals(mossa4, menu.getMove("a5"));
			assertArrayEquals(mossa5, menu.getMove("Ce5"));
			assertArrayEquals(mossa6, menu.getMove("a4"));
			assertArrayEquals(mossa7, menu.getMove("Cd5"));
			assertArrayEquals(mossa8, menu.getMove("a3"));
			assertArrayEquals(mossa9, menu.getMove("Ce3"));
			assertArrayEquals(mossa10, menu.getMove("b6"));
			//solleva eccezione, mossa ambigua entrambe possibili celle
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cc4");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cg4");
			});
			//notazione disambigua con riga sbagliata
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C4g4");
			});
			//notazione disambigua dando colonna di partenza
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ceg4");
			});
			//notazione disambigua dando riga di partenza fuori dai limiti
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C9g4");
			});
			//notazione disambigua
			assertArrayEquals(mossa11, menu.getMove("C3g4"));
		});
	}	

	//test mossa ambigua stessa riga, muovendo primo cavallo
	@Test
	void testAmbiguousColumnMoveFirstKnight() {
		String[] mossa1 = { "\u2658", null, "c3" };
		String[] mossa2 = { "\u265F", null, "a6" };
		String[] mossa3 = { "\u2658", null, "d5" };
		String[] mossa4 = { "\u265F", null, "b6" };
		String[] mossa5 = { "\u2658", null, "f3" };
		String[] mossa6 = { "\u265F", null, "c6" };
		String[] mossa7 = { "\u2658", null, "f4" };
		String[] mossa8 = { "\u265F", null, "d6" };
		String[] mossa9 = { "\u2658", null, "d3" };
		String[] mossa10 = { "\u265F", null, "e6" };
		String[] mossa11 = { "\u2658", null, "e5" };


		assertAll("Moving Knight with and without ambiguous notation", () -> {
			assertArrayEquals(mossa1, menu.getMove("Cc3"));
			assertArrayEquals(mossa2, menu.getMove("a6"));
			assertArrayEquals(mossa3, menu.getMove("Cd5"));
			assertArrayEquals(mossa4, menu.getMove("b6"));
			assertArrayEquals(mossa5, menu.getMove("Cf3"));
			assertArrayEquals(mossa6, menu.getMove("c6"));
			assertArrayEquals(mossa7, menu.getMove("Cf4"));
			assertArrayEquals(mossa8, menu.getMove("d6"));
			assertArrayEquals(mossa9, menu.getMove("Cd3"));
			assertArrayEquals(mossa10, menu.getMove("e6"));
			//solleva eccezione, mossa ambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ce5");
			});
			//notazione disambigua con colonna sbagliata
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Che5");
			});
			//notazione disambigua dando riga di partenza
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C3e5");
			});
			//notazione disambigua dando colonna di partenza fuori dai limiti
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cte5");
			});

			//notazione disambigua
			assertArrayEquals(mossa11, menu.getMove("Cde5"));
		});
	}
	//test mossa ambigua stessa riga, muovendo secondo cavallo
	@Test
	void testAmbiguousColumnMoveSecondKnight() {
		String[] mossa1 = { "\u2658", null, "c3" };
		String[] mossa2 = { "\u265F", null, "a6" };
		String[] mossa3 = { "\u2658", null, "d5" };
		String[] mossa4 = { "\u265F", null, "b6" };
		String[] mossa5 = { "\u2658", null, "f3" };
		String[] mossa6 = { "\u265F", null, "c6" };
		String[] mossa7 = { "\u2658", null, "f4" };
		String[] mossa8 = { "\u265F", null, "d6" };
		String[] mossa9 = { "\u2658", null, "d3" };
		String[] mossa10 = { "\u265F", null, "e6" };
		String[] mossa11 = { "\u2658", null, "e5" };


		assertAll("Moving Knight with and without ambiguous notation", () -> {
			assertArrayEquals(mossa1, menu.getMove("Cc3"));
			assertArrayEquals(mossa2, menu.getMove("a6"));
			assertArrayEquals(mossa3, menu.getMove("Cd5"));
			assertArrayEquals(mossa4, menu.getMove("b6"));
			assertArrayEquals(mossa5, menu.getMove("Cf3"));
			assertArrayEquals(mossa6, menu.getMove("c6"));
			assertArrayEquals(mossa7, menu.getMove("Cf4"));
			assertArrayEquals(mossa8, menu.getMove("d6"));
			assertArrayEquals(mossa9, menu.getMove("Cd3"));
			assertArrayEquals(mossa10, menu.getMove("e6"));
			//solleva eccezione, mossa ambigua
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ce5");
			});
			//notazione disambigua con colonna sbagliata
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cbe5");
			});
			//notazione disambigua dando riga di partenza
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C3e5");
			});
			//notazione disambigua dando colonna di partenza fuori dai limiti
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cte5");
			});

			//notazione disambigua corretta
			assertArrayEquals(mossa11, menu.getMove("Cfe5"));
		});
	}

	//test mossa cavallo casella occupata da pezzo nemico
	@Test
	void testOccupiedCellMoveKnight() {
		String[] mossa1 = { "\u2658", null, "c3" };
		String[] mossa2 = { "\u265E", null, "c6" };
		String[] mossa3 = { "\u2658", null, "b5" };
		String[] mossa4 = { "\u265E", null, "b4" };


		assertAll("Moving Knight on lecit cell, but occupied from an enemy piece", () -> {
			assertArrayEquals(mossa1, menu.getMove("Cc3"));
			assertArrayEquals(mossa2, menu.getMove("Cc6"));
			assertArrayEquals(mossa3, menu.getMove("Cb5"));
			assertArrayEquals(mossa4, menu.getMove("Cb4"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cc7");
			});

		});
	}

	//test mossa cavallo illegale
	@Test
	void testIllegalMoveKnight() {
		assertAll("Testing illegal moves for Knight", () -> {
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cd4");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cgh");
			});
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C88");
			});
			//con notazione disambigua colonna sbagliata
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cac3");
			});
			//con notazione disambigua riga sbagliata
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C5h3");
			});
			//cella di destinazione invalida
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Chgh");
			});
			//cella di destinazione oltre i limiti
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Ca88");
			});
			//nessun Cavallo può muoversi da riga di partenza
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C5c5");
			});
			//nessun Cavallopuò muoversi da colonna di partenza
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cdd4");
			});
			//riga di partenza oltre limiti scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("C9f5");
			});
			//colonna di partenza oltre limiti scacchiera
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cyf5");
			});
		});
	}

	//test mossa Cavallo che lascerebbe il re sotto scacco
	@Test
	void testMoveKnightThreatenedKing() {
		String[] mossa1 = { "\u2658", null, "c3" };
		String[] mossa2 = { "\u265F", null, "d5" };
		String[] mossa3 = { "\u2658", null, "e4" };
		String[] mossa4 = { "\u265F", null, "d4" };
		String[] mossa5 = { "\u2659", null, "f4" };
		String[] mossa6 = { "\u265F", null, "d3" };
		String[] mossa7 = { "\u2659", "\u265F", "d3" };
		String[] mossa8 = { "\u265F", null, "a6" };
		String[] mossa9 = { "\u2659", null, "f5" };
		String[] mossa10 = { "\u265F", null, "b6" };
		String[] mossa11 = { "\u2659", null, "f6" };
		String[] mossa12 = { "\u265F", "\u2659", "f6" };
		String[] mossa13 = { "\u2659", null, "a3" };
		String[] mossa14 = { "\u265B", null, "e7" };


		assertAll("Moving Knight that will leave King threatened", () -> {
			assertArrayEquals(mossa1, menu.getMove("Cc3"));
			assertArrayEquals(mossa2, menu.getMove("d5"));
			assertArrayEquals(mossa3, menu.getMove("Ce4"));
			assertArrayEquals(mossa4, menu.getMove("d4"));
			assertArrayEquals(mossa5, menu.getMove("f4"));
			assertArrayEquals(mossa6, menu.getMove("d3"));
			assertArrayEquals(mossa7, menu.getMove("exd3"));
			assertArrayEquals(mossa8, menu.getMove("a6"));
			assertArrayEquals(mossa9, menu.getMove("f5"));
			assertArrayEquals(mossa10, menu.getMove("b6"));
			assertArrayEquals(mossa11, menu.getMove("f6"));
			assertArrayEquals(mossa12, menu.getMove("exf6"));
			assertArrayEquals(mossa13, menu.getMove("a3"));
			assertArrayEquals(mossa14, menu.getMove("De7"));
			assertThrows(IllegalMoveException.class, () -> {
				menu.getMove("Cc5");
			});

		});
	}

//	//test cattura semplice Cavallo
//
//	@Test
//	void testCapturefromARook() {
//
//		//avvaloro mosse attese
//
//		expectedMoves.add("a4");
//		expectedMoves.add("h5");
//		expectedMoves.add("Ta3");
//		expectedMoves.add("Th6");
//		expectedMoves.add("Tb3");
//		expectedMoves.add("Tg6");
//		expectedMoves.add("Txb7");
//		expectedMoves.add("Txg2");
//
//
//		//pezzi catturati attesi
//
//		expectedBlackPieceCaptured.add("\u265F");
//		expectedWhitePieceCaptured.add("\u2659");
//
//		//array mosse da confrontare con il risultato
//
//		String[] mossa1 = { "\u2659", null, "a4" };
//		String[] mossa2 = { "\u265F", null, "h5" };
//		String[] mossa3 = { "\u2656", null, "a3" };
//		String[] mossa4 = { "\u265C", null, "h6" };
//		String[] mossa5 = { "\u2656", null, "b3" };
//		String[] mossa6 = { "\u265C", null, "g6" };
//		String[] mossa7 = { "\u2656", "\u265F", "b7" };
//		String[] mossa8 = { "\u265C", "\u2659", "g2" };
//
//
//		//test cattura semplice per entrambi i colori
//		assertAll("Capture from Rooks", () -> {
//			assertArrayEquals(mossa1, menu.getMove("a4"));
//			assertArrayEquals(mossa2, menu.getMove("h5"));
//			assertArrayEquals(mossa3, menu.getMove("Ta3"));
//			assertArrayEquals(mossa4, menu.getMove("Th6"));
//			assertArrayEquals(mossa5, menu.getMove("Tb3"));
//			assertArrayEquals(mossa6, menu.getMove("Tg6"));
//			assertArrayEquals(mossa7, menu.getMove("Txb7"));
//			assertArrayEquals(mossa8, menu.getMove("Txg2"));
//			assertEquals(expectedBlackPieceCaptured,menu.blackCaptured());
//			assertEquals(expectedWhitePieceCaptured,menu.whiteCaptured());
//			assertEquals(expectedMoves,menu.moves());
//		});
//	}
//
	//					
	//									//test cattura su cella vuota
	//									@Test
	//									void testCaptureOnEmptyCellFromRook() {
	//										String[] mossa1 = { "\u2659", null, "a4" };
	//										String[] mossa2 = { "\u265F", null, "h5" };
	//										String[] mossa3 = { "\u2656", null, "a3" };
	//										String[] mossa4 = { "\u265C", null, "h6" };
	//										assertAll("capture fail on empty cell from Rook", () -> {
	//											assertArrayEquals(mossa1, menu.getMove("a4"));
	//											assertArrayEquals(mossa2, menu.getMove("h5"));
	//											assertArrayEquals(mossa3, menu.getMove("Ta3"));
	//											assertArrayEquals(mossa4, menu.getMove("Th6"));
	//											assertThrows(IllegalMoveException.class, () -> {
	//												menu.getMove("Txd3");
	//											});
	//					
	//										});
	//									}
	//									//test cattura su pezzo alleato
	//									@Test
	//									void testCaptureOnAlliedPieceFromRook() {
	//										
	//											assertThrows(IllegalMoveException.class, () -> {
	//												menu.getMove("Txb1");
	//											});
	//									}
	//					
	//									//test cattura che lascerebbe il re sotto scacco
	//									@Test
	//									void testCaptureThreatenedKingFromRook() {
	//										String[] mossa1 = { "\u2659", null, "a4" };
	//										String[] mossa2 = { "\u265F", null, "h5" };
	//										String[] mossa3 = { "\u2656", null, "a3" };
	//										String[] mossa4 = { "\u265C", null, "h6" };
	//										String[] mossa5 = { "\u2659", null, "e4" };
	//										String[] mossa6 = { "\u265F", null, "e5" };
	//										String[] mossa7 = { "\u2659", null, "d4" };
	//										String[] mossa8 = { "\u265F", null, "d5" };
	//										String[] mossa9 = { "\u2659", "\u265F", "d5" };
	//										String[] mossa10 = { "\u265F", "\u2659", "d4" };
	//										String[] mossa11 = { "\u2656", null, "e3" };
	//										String[] mossa12 = { "\u265C", null, "e6" };
	//										String[] mossa13 = { "\u2659", null, "a5" };
	//										String[] mossa14 = { "\u265F", null, "d3" };
	//					
	//										assertAll("Capture from Rook that will leave King threatened", () -> {
	//											assertArrayEquals(mossa1, menu.getMove("a4"));
	//											assertArrayEquals(mossa2, menu.getMove("h5"));
	//											assertArrayEquals(mossa3, menu.getMove("Ta3"));
	//											assertArrayEquals(mossa4, menu.getMove("Th6"));
	//											assertArrayEquals(mossa5, menu.getMove("e4"));
	//											assertArrayEquals(mossa6, menu.getMove("e5"));
	//											assertArrayEquals(mossa7, menu.getMove("d4"));
	//											assertArrayEquals(mossa8, menu.getMove("d5"));
	//											assertArrayEquals(mossa9, menu.getMove("exd5"));
	//											assertArrayEquals(mossa10, menu.getMove("exd4"));
	//											assertArrayEquals(mossa11, menu.getMove("Te3"));
	//											assertArrayEquals(mossa12, menu.getMove("Te6"));
	//											assertArrayEquals(mossa13, menu.getMove("a5"));
	//											assertArrayEquals(mossa14, menu.getMove("d3"));
	//											assertThrows(IllegalMoveException.class, () -> {
	//												menu.getMove("Txd3");
	//											});
	//					
	//										});
	//									}
	//					
	//					

	//									//test cattura illegale 
	//									@Test
	//									void testCaptureOnIllegalCellFromRook() {
	//										String[] mossa1 = { "\u2659", null, "a4" };
	//										String[] mossa2 = { "\u265F", null, "h5" };
	//										String[] mossa3 = { "\u2656", null, "a3" };
	//										String[] mossa4 = { "\u265C", null, "h6" };
	//										String[] mossa5 = { "\u2656", null, "b3" };
	//										String[] mossa6 = { "\u265C", null, "g6" };
	//										assertAll("Test on Illegal Cell", () -> {
	//											assertArrayEquals(mossa1, menu.getMove("a4"));
	//											assertArrayEquals(mossa2, menu.getMove("h5"));
	//											assertArrayEquals(mossa3, menu.getMove("Ta3"));
	//											assertArrayEquals(mossa4, menu.getMove("Th6"));
	//											assertArrayEquals(mossa5, menu.getMove("Tb3"));
	//											assertArrayEquals(mossa6, menu.getMove("Tg6"));
	//											//test cattura con notazione errata ma cella corretta
	//											assertThrows(IllegalMoveException.class, () -> {
	//												menu.getMove("Tcb7");
	//											});
	//											//test cattura su cella non consentita
	//											assertThrows(IllegalMoveException.class, () -> {
	//												menu.getMove("Txc4");
	//											});
	//											//test cattura con notazione cella oltre limiti scacchiera
	//											assertThrows(IllegalMoveException.class, () -> {
	//												menu.getMove("Txq9");
	//											});
	//											//test cattura su pezzo di colore diverso, ma coperto da altro pezzo
	//											assertThrows(IllegalMoveException.class, () -> {
	//												menu.getMove("Txb8");
	//											});
	//											//test cattura su se stesso
	//											assertThrows(IllegalMoveException.class, () -> {
	//												menu.getMove("Txb3");
	//											});
	//										});
	//	
	//	
	//									}



}
