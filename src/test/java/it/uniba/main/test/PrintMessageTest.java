package it.uniba.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.main.PrintMessage;

public class PrintMessageTest {
	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	@BeforeAll
	static void setUpAll() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	@BeforeEach
	void setUp() {			
			outContent.reset();	
			errContent.reset();
	}
	@Test
	void printMessageTest() {
		PrintMessage.printMessage("test");
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("test",testString);
	 
	}
	@Test
	void printBoardTest() {
		String[][] boardToPrint = { {"[\u265C]","[\u265E]","[\u265D]","[\u265B]","[\u265A]","[\u265D]","[\u265E]","[\u265C]"},	 
				{"[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]","[\u265F]"},
				{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				{"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				{"[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]","[\u2659]"},
				{"[\u2656]","[\u2658]","[\u2657]","[\u2655]","[\u2654]","[\u2657]","[\u2658]","[\u2656]"},
		};
	    PrintMessage.printBoard(boardToPrint);
	    String PrintedBoard = outContent.toString();
	    PrintedBoard = PrintedBoard.replace("\n", "").replace("\r", "");
	    assertEquals("    a    b    c    d    e    f    g    h8  [\u265C]  [\u265E]  [\u265D]  [\u265B]  [\u265A]  [\u265D]  [\u265E]  "
	    		+ "[\u265C]  8  7  [\u265F]  [\u265F]  [\u265F]  [\u265F]  [\u265F]  [\u265F]  [\u265F]  [\u265F]  7  6  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  6  5  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  "
	    		+ "5  4  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  4  3  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  3  2  "
	    		+ "[\u2659]  [\u2659]  [\u2659]  [\u2659]  [\u2659]  [\u2659]  [\u2659]  [\u2659]  2  1  [\u2656]  [\u2658]  [\u2657]  [\u2655]  [\u2654]  [\u2657]  [\u2658]  [\u2656]  1      a    b    c    d    e    f    g    h", PrintedBoard);
	 }
	@Test
	void printTurnTest() {
		PrintMessage.printTurn("bianchi");
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Inserire comando (turno dei bianchi)",testString);
	}
	
	@Test
	void printErrMessageTest() {
		PrintMessage.printError("test mossa illegale");
	    String testString = errContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("test mossa illegale",testString);
	 } 
	@Test
	void printEmptyMovesListTest() {
		PrintMessage.printMoves(new ArrayList<String>());
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Non sono ancora state effettuate mosse",testString);
	 }
	@Test
	void printMovesListTest() {
		ArrayList<String> moves = new ArrayList<>(Arrays.asList("e4","e5","d4","d5","exd5","exd4","Cc3"));
		PrintMessage.printMoves(moves);
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("1. e4 e52. d4 d53. exd5 exd44. Cc3",testString);
	 }
	
	@Test
	void printEmptyCaptureListTest() {
		PrintMessage.printCaptures(new ArrayList<String>(),new ArrayList<String>());
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Nessun pezzo nero catturatoNessun pezzo bianco catturato",testString);
	 }
	@Test
	void printCapturedListTest() {
		ArrayList<String> blackCaptured = new ArrayList<>(Arrays.asList("\u265C","\u265D","\u265F"));
		ArrayList<String> whitesCaptured = new ArrayList<>(Arrays.asList("\u2657","\u2659","\u2656","\u2659"));
		PrintMessage.printCaptures(blackCaptured,whitesCaptured);
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Pezzi neri catturati: [\u265C, \u265D, \u265F]Pezzi bianchi catturati: [\u2657, \u2659, \u2656, \u2659]",testString);
	 }
	@Test
	void printAMoveTest() {
		String[] move = {"\u2658", null, "e4"};
		PrintMessage.printAMove(move);
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("\u2658 spostato su e4",testString);
	 }
	@Test
	void printShortCastlingTest() {
		PrintMessage.printShortCastling();
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Arrocco corto eseguito",testString);
	 }
	@Test
	void printLongCastlingTest() {
		PrintMessage.printLongCastling();
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Arrocco lungo eseguito",testString);
	 }
	@Test
	void printACaptureTest() {
		String[] move = {"\u265B", "\u2659", "d6"};
		PrintMessage.printACapture(move);
	    String testString = outContent.toString();
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("\u2659 e' stato catturato da \u265B su d6",testString);
	 }
	@AfterAll
	static void tearDownAll() {
		try {
			outContent.close();
			errContent.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
