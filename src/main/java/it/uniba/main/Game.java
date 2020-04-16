package it.uniba.main;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	private static Cell board[][] = new Cell[8][8];
	enum Colonna {a,b,c,d,e,f,g,h};
	ArrayList <Cell>movesDone = new ArrayList<Cell>();

	void nuovaPartita() {
		System.out.println("Creazione partita in corso...");
		for(int j=0;j<8;j++) {
			//inizializzazione pedoni a2-h2 (lato bianco)
			//scacchiera[1][j]= new Cella(new Pedone());

			//inizializzazione pedoni a7-h7 (lato nero)
			//scacchiera[6][j]= new Cella(new Pedone());
		};
		//inizializzazione case a1-h1 (lato bianco)
		/* scacchiera[0][0]= new Cella(new Torre()); scacchiera[0][1]= new Cella(new
		 * Cavallo()); scacchiera[0][2]= new Cella(new Alfiere()); scacchiera[0][3]= new
		 * Cella(new Donna()); scacchiera[0][4]= new Cella(new Re()); scacchiera[0][5]=
		 * new Cella(new Alfiere()); scacchiera[0][6]= new Cella(new Cavallo());
		 * scacchiera[0][7]= new Cella(new Torre());
		 */

		//inizializzazione case a8-h8 (lato nero)
		/*
		 * scacchiera[7][0]= new Cella(new Torre()); scacchiera[7][1]= new Cella(new
		 * Cavallo()); scacchiera[7][2]= new Cella(new Alfiere()); scacchiera[7][3]= new
		 * Cella(new Donna()); scacchiera[7][4]= new Cella(new Re()); scacchiera[7][5]=
		 * new Cella(new Alfiere()); scacchiera[7][6]= new Cella(new Cavallo());
		 * scacchiera[7][7]= new Cella(new Torre());
		 */	
	};

	void riceviMossa() {
		int x; //ascissa
		int y; //ordinata

		Scanner in = new Scanner(System.in);
		String mossa= in.nextLine();
		if (mossa.length()==2){
			x = mossa.charAt(1);
			y = Colonna.valueOf(mossa.substring(0,0)).ordinal();
			Pawn p;
			ArrayList<Cell> possMoves;

			if ((board[x-1][y].getPiece() instanceof Pawn )&& (board[x-1][y].getPiece().GetColor() == 0)) { //neri
				p = (Pawn) board[x-1][y].getPiece();
				possMoves = p.MosseDisponibili(board);
				if (possMoves.contains(board[x][y])) {
					board[x-1][y].setEmpty();
					board[x][y].setPiece(p);
					p.incrementMoves();
				}
			}
			else if ((board[x-2][y].getPiece() instanceof Pawn )&& (board[x-2][y].getPiece().GetColor() == 0)) { 
				p = (Pawn) board[x-2][y].getPiece();
				possMoves = p.mossePossibili;
				if (possMoves.contains(board[x][y])) {
					board[x-2][y].setEmpty();
					board[x][y].setPiece(p);
					p.incrementMoves();
				}
			}
			else if ((board[x+1][y].getPiece() instanceof Pawn )&& (board[x+1][y].getPiece().GetColor() == 1)) { 
				p = (Pawn) board[x+1][y].getPiece();
				possMoves = p.mossePossibili;
				if (possMoves.contains(board[x][y])) {
					board[x+1][y].setEmpty();
					board[x][y].setPiece(p);
					p.incrementMoves();
				}
			}
			else if ((board[x+2][y].getPiece() instanceof Pawn )&& (board[x+2][y].getPiece().GetColor() == 0)) { 
				p = (Pawn) board[x+2][y].getPiece();
				possMoves = p.mossePossibili;
				if (possMoves.contains(board[x][y])) {
					board[x+2][y].setEmpty();
					board[x][y].setPiece(p);
					p.incrementMoves();
				}
			}
			

		}
		else {
			char pezzoScelto=mossa.charAt(0);
			switch(pezzoScelto) {
			case 'T':
				System.err.println("Pezzo non ancora implementato");//muovi Torre
			case 'C':
				System.err.println("Pezzo non ancora implementato");//muovi Cavallo
			case 'A':
				System.err.println("Pezzo non ancora implementato");//muovi Alfiere
			case 'D':
				System.err.println("Pezzo non ancora implementato");//muovi Donna
			case 'R':
				System.err.println("Pezzo non ancora implementato");//muovi Re
			}
			//mossa.substring(1);
		}

		//parsing della colonna in cui muovere
		//Colonna colonnaMossa= Colonna.valueOf(mossa.substring(0,1));
		//accesso con scacchiera[mossa.charAt(1)-1][colonnaMossa.ordinal()]

		
			in.close();
	}

	public static void printBoard() {
		System.out.println("\ta\tb\tc\td\te\tf\tg\th");
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i + ".\t");
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j]);
			}
		}
		System.out.println("\ta\tb\tc\td\te\tf\tg\th");

	}
}