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
		menu.play();
		expectedMoves.clear();
		expectedBlackPieceCaptured.clear();
		expectedWhitePieceCaptured.clear();
	}

	
	@Test
	@DisplayName("Testing help menu print")
	void testhelp() {
		String help = "Lista di comandi utilizzabili:\n" + "help\n" + "play\n" + "quit\n"
				+ "Lista di comandi utilizzabili solo se in partita:\n" + "board\n" + "captures\n" + "moves\n"
				+ "Per effettuare una mossa e' necessario specificarla in notazione algebrica; \nPer la cattura en passant si puo' specificare 'e.p.' o 'ep' alla fine della mossa in notazione algebrica";
		assertEquals(menu.help(), help);
	}

	 @Test
	 @DisplayName("Testing new game board print")
	 void newGameBoardTest() {
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
		 assertFalse(menu.getBlackTurn());
	 }
	 
	 @Test
	 @DisplayName("Testing move a Pawn")
	 void printMovesTest() {
		 ArrayList<String> expectedMoves = new ArrayList<String>();
		 expectedMoves.add("a4");
		 expectedMoves.add("b5");
		 expectedMoves.add("c3");
		 expectedMoves.add("d6");
		 String[] mossa1 = { "\u2659", null, "a4" };
		 String[] mossa2 = { "\u265F", null, "b5" };
		 String[] mossa3 = { "\u2659", null, "c3" };
		 String[] mossa4 = { "\u265F", null, "d6" };
		 assertAll("Moving pawns on lecit cells", () -> {
			 assertArrayEquals(mossa1, menu.getMove("a4"));
			 assertArrayEquals(mossa2, menu.getMove("b5"));
			 assertArrayEquals(mossa3, menu.getMove("c3"));
			 assertArrayEquals(mossa4, menu.getMove("d6"));
		 });
		 assertAll("Try a move on not lecit cell", () -> {
			 assertThrows(IllegalMoveException.class, () -> {
					menu.getMove("f5");
				});
				assertThrows(IndexOutOfBoundsException.class, () -> {
					menu.getMove("t9");
				});
		 });
		 
		 assertEquals(expectedMoves,menu.moves());
		 
	 }
	 
	

	
	@Test
	void testCapturefromAPawn() {
		expectedMoves.add("b4");
		expectedMoves.add("c5");
		expectedMoves.add("bxc5");
		expectedBlackPieceCaptured.add("\u265F");
		expectedWhitePieceCaptured.add("\u2659");
		 
		 String[] mossa1 = { "\u2659", null, "b4" };
		 String[] mossa2 = { "\u265F", null, "c5" };
		 String[] mossa3 = { "\u2659", "\u265F", "c5" };
		 String[] mossa4 = { "\u265F", null, "d6" };
		 String[] mossa5 = { "\u2659", null, "e4" };
		 String[] mossa6 = { "\u265F", "\u2659", "c5" };
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
		 
		
	}
	@Test
	void testCapturefromAPawnEP() {
		expectedMoves.add("b4");
		expectedMoves.add("c5");
		expectedMoves.add("bxc5");
		expectedBlackPieceCaptured.add("\u265F");
		 
		 String[] mossa1 = { "\u2659", null, "b4" };
		 String[] mossa2 = { "\u265F", null, "c5" };
		 String[] mossa3 = { "\u2659", "\u265F", "c5" };
		 assertAll("Moving pawns and capture", () -> {
			 assertArrayEquals(mossa1, menu.getMove("b4"));
			 assertArrayEquals(mossa2, menu.getMove("c5"));
			 assertArrayEquals(mossa3, menu.getMove("bxc5"));
			 assertEquals(expectedBlackPieceCaptured,menu.Blackcaptured());
			 assertThrows(IllegalMoveException.class, () -> {
					menu.getMove("d4");
				});
				assertThrows(IndexOutOfBoundsException.class, () -> {
					menu.getMove("t9");
				});
		 });
		 
		
	}
	
}
