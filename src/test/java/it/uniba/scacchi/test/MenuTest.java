//package it.uniba.scacchi.test;
//
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertArrayEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import it.uniba.main.IllegalMoveException;
//import it.uniba.main.Menu;
//
//public class MenuTest {
//
//	private static InputStream in;
//	private static Menu menu = new Menu();
//
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
//
//	@AfterAll
//	static void tearDownAll() {
//		System.out.println("teadDownAll");
//
//	}
//
//	@Test
//	@DisplayName("Testing help menu print")
//	void testhelp() {
//		String help = "Lista di comandi utilizzabili:\n" + "help\n" + "play\n" + "quit\n"
//				+ "Lista di comandi utilizzabili solo se in partita:\n" + "board\n" + "captures\n" + "moves\n"
//				+ "Per effettuare una mossa e' necessario specificarla in notazione algebrica; \nPer la cattura en passant si puo' specificare 'e.p.' o 'ep' alla fine della mossa in notazione algebrica";
//		assertEquals(menu.help(), help);
//	}
//
//	 @Test
//	 @DisplayName("Testing new game board print")
//	 void newGameBoardTest() {
//		 menu.play();
//	 String[][] board = {{"[\u2656]","[\u2658]","[\u2657]","[\u2655]","[\u2654]","[\u2657]","[\u2658]","[\u2656]"},
//			 {"[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]"},
//	 		 {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
//			 			{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
//			 			{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
//			 			{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
//			 			 {"[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]"},
//					 				{"[\u265C]","[\u265E]","[\u265D]","[\u265B]","[\u265A]","[\u265D]","[\u265E]","[\u265C]"},	 
//									
//	 	};
//	  	assertArrayEquals(menu.board(),board);
//	 }
//	 
//	 @Test
//	 void printMovesTest() {
//		 ArrayList<String> expectedMoves = new ArrayList<String>();
//		 expectedMoves.add("e4");
//		 expectedMoves.add("e5");
//		 String[] mossa1 = { "\u265F", null, "e4" };
//		 String[] mossa2 = { "\u2659", null, "e5" };
//		 menu.play();
//		 assertAll("Moving pawns", () -> {
//			 assertArrayEquals(mossa1, menu.getMove("e4"));
//			 assertArrayEquals(mossa2, menu.getMove("e5"));
//		 });
//		 
//		 assertEquals(expectedMoves,menu.moves());
//		 
//	 }
//	 
//	
//	@Test
//	void testMovePawn() {
//		menu.play();
//		String[] mossa = { "\u265F", null, "e4" };
//		assertAll("Test on move a Pawn", () -> {
//				assertArrayEquals(mossa, menu.getMove("e4"));
//				assertThrows(IllegalMoveException.class, () -> {
//					menu.getMove("e3");
//				});
//				assertThrows(IndexOutOfBoundsException.class, () -> {
//					menu.getMove("t9");
//				});
//		});
//			
//		
//	}
//}
