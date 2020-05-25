package it.uniba.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
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
		try {
		      System.setOut(new PrintStream(outContent, false, "UTF-8"));
		      System.setErr(new PrintStream(errContent, false, "UTF-8"));
		    } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		    }
		  }
	@BeforeEach
	void setUp() {			
			outContent.reset();	
			errContent.reset();
			
	}
	@Test
	void printMessageTest() {
		PrintMessage.printMessage("test");
		try {
	    String testString = outContent.toString("UTF-8");
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("test",testString);
		} catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	 
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
	    try {

		    String PrintedBoard = outContent.toString("UTF-8");
		    PrintedBoard = PrintedBoard.replace("\n", "").replace("\r", "");
		    assertEquals("    a    b    c    d    e    f    g    h8  [\u265C]  [\u265E]  [\u265D]  [\u265B]  [\u265A]  [\u265D]  [\u265E]  "
		    		+ "[\u265C]  8  7  [\u265F]  [\u265F]  [\u265F]  [\u265F]  [\u265F]  [\u265F]  [\u265F]  [\u265F]  7  6  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  6  5  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  "
		    		+ "5  4  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  4  3  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  [ ]  3  2  "
		    		+ "[\u2659]  [\u2659]  [\u2659]  [\u2659]  [\u2659]  [\u2659]  [\u2659]  [\u2659]  2  1  [\u2656]  [\u2658]  [\u2657]  [\u2655]  [\u2654]  [\u2657]  [\u2658]  [\u2656]  1      a    b    c    d    e    f    g    h", PrintedBoard);
		 
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	}
	
	
	@Test
	void printErrMessageTest() {
		PrintMessage.printError("test mossa illegale");
		try {
	    String testString = errContent.toString("UTF-8");
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("test mossa illegale",testString);
		} catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	 } 
	@Test
	void printEmptyMovesListTest() {
		PrintMessage.printMoves(new ArrayList<String>());
		try {
	    String testString = outContent.toString("UTF-8");
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Non sono ancora state effettuate mosse",testString);
		}  catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	 }
	@Test
	void printMovesListTest() {
		ArrayList<String> moves = new ArrayList<>(Arrays.asList("e4","e5","d4","d5","exd5","exd4","Cc3"));
		PrintMessage.printMoves(moves);
		try {
	    String testString = outContent.toString("UTF-8");
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("1. e4 e52. d4 d53. exd5 exd44. Cc3",testString);
		} catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	 }
	
	@Test
	void printEmptyCaptureListTest() {
		PrintMessage.printCaptures(new ArrayList<String>(),new ArrayList<String>());
		try {
			String testString = outContent.toString("UTF-8");
		    testString = testString.replace("\n", "").replace("\r", "");
		    assertEquals("Nessun pezzo nero catturatoNessun pezzo bianco catturato",testString);
		}catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	    
	 }
	@Test
	void printCapturedListTest() {
		ArrayList<String> blackCaptured = new ArrayList<>(Arrays.asList("\u265C","\u265D","\u265F"));
		ArrayList<String> whitesCaptured = new ArrayList<>(Arrays.asList("\u2657","\u2659","\u2656","\u2659"));
		PrintMessage.printCaptures(blackCaptured,whitesCaptured);
		try {
	    String testString = outContent.toString("UTF-8");
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Pezzi neri catturati: [\u265C, \u265D, \u265F]Pezzi bianchi catturati: [\u2657, \u2659, \u2656, \u2659]",testString);
	 } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	}
	@Test
	void printAMoveTest() {
		String[] move = {"\u2658", null, "e4"};
		PrintMessage.printAMove(move);
		try {
			String testString = outContent.toString("UTF-8");
		    testString = testString.replace("\n", "").replace("\r", "");
		    assertEquals("\u2658 spostato su e4",testString);
		} catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
		
	    
	 }
	@Test
	void printShortCastlingTest() {
		PrintMessage.printShortCastling();
		try {
	    String testString = outContent.toString("UTF-8");
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Arrocco corto eseguito",testString);
		} catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	 }
	@Test
	void printLongCastlingTest() {
		PrintMessage.printLongCastling();
		try {
	    String testString = outContent.toString("UTF-8");
	    testString = testString.replace("\n", "").replace("\r", "");
	    assertEquals("Arrocco lungo eseguito",testString,"UTF-8");
		} catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	 }
	@Test
	void printACaptureTest() {
		
		String[] move = {"\u265B", "\u2659", "d6"};
		PrintMessage.printACapture(move);
		try {
			String testString = outContent.toString("UTF-8");
		    testString = testString.replace("\n", "").replace("\r", "");
		    assertEquals("\u2659 e' stato catturato da \u265B su d6",testString);
		} catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
	    }
	    
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
