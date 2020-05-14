package it.uniba.main;

import java.util.ArrayList;

class PrintMessage {
	
	static void helpPrint() {
		System.out.println("Lista di comandi utilizzabili:");
		System.out.println("help");
		System.out.println("play");
		System.out.println("quit");
		System.out.println("\nLista di comandi utilizzabili solo se in partita:");
		System.out.println("board");
		System.out.println("captures");
		System.out.println("moves");
		System.out.println(
				"Per effettuare una mossa e' necessario specificarla in notazione algebrica; \nPer la cattura en passant si puo' specificare 'e.p.' o 'ep' alla fine della mossa in notazione algebrica");
	
	}
	
	static void printBoard(String [][]board) {
		System.out.println("    a    b    c    d    e    f    g    h");
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i + "  ");
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + "  ");
			}
			System.out.print(8 - i + "  ");
			System.out.println("\n");
		}
		System.out.println("    a    b    c    d    e    f    g    h");
	}
	
	static void printMoves(ArrayList<String> moves) {
		if (moves.size() == 0) {
			System.out.println("Non sono ancora state effettuate mosse");
		} else {
			int j; // secondo indice
			int k = 1; // numero mossa
			for (int i = 0; i < moves.size(); i++) {
				j = i + 1;
				if (j < moves.size()) {

					System.out.println(k + ". " + moves.get(i) + " " + moves.get(j));
					i++;
					k++;

				} else if (i == moves.size() - 1) {
					System.out.println(k + ". " + moves.get(i));

				}
			}
		}

	}
	
	static void printCaptures(ArrayList<String> capturedBlacks, ArrayList<String> capturedWhites) {
		if (capturedBlacks.size() == 0) {
			System.out.println("Nessun pezzo nero catturato");
		} else {
			System.out.println("Pezzi neri catturati: " + capturedBlacks);
		}
		if (capturedWhites.size() == 0) {
			System.out.println("Nessun pezzo bianco catturato");
		} else {
			System.out.println("Pezzi bianchi catturati: " + capturedWhites);
		}
	}
	
	static void printAMove(String piece, String move) {
		System.out.println(piece + " spostato su " + move);
	}

	static void printShortCastling() {
		System.out.println("Arrocco corto eseguito");
	}
	static void printLongCastling() {
		System.out.println("Arrocco corto eseguito");
	}


	
	static void printACapture(String[] pieces, String move) {
		System.out.println(pieces[0] + " e' stato catturato da " + pieces[1] + " su "
				+ move);
	}

}
