package it.uniba.main;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	private static Cell board[][] = new Cell[8][8];
	private enum Colonna {a,b,c,d,e,f,g,h};
	private ArrayList<String> movesDone = new ArrayList<String>();
	private ArrayList<Piece> BlacksCaptured= new ArrayList<Piece>();
	private ArrayList<Piece> WhitesCaptured= new ArrayList<Piece>();
	
	public ArrayList<Piece> getBlacks(){
		return BlacksCaptured;
	}
	public ArrayList<Piece> getWhites(){
		return WhitesCaptured;
	}

	void newGame() {
		System.out.println("Creating game...");
		for(int j=0;j<8;j++) {
			//initialize pawns a2-h2 (white side)
			board[1][j]= new Cell(new Pawn(1,1,j));

			//initialize pawns a7-h7 (black side)
			board[6][j]= new Cell(new Pawn(0,6,j));
		};
		//initialize pieces a1-h1 (white side)
		board[0][0]= new Cell(new Rook(1,0,0));
		board[0][1]= new Cell(new Knight(1,0,1));
		board[0][2]= new Cell(new Bishop(1,0,2));
		board[0][3]= new Cell(new Queen(1,0,3));
		board[0][4]= new Cell(new King(1,0,4));
		board[0][5]= new Cell(new Bishop(1,0,5));
		board[0][6]= new Cell(new Knight(1,0,6));
		board[0][7]= new Cell(new Rook(1,0,7));


		//initialize pieces a8-h8 (black side)
		board[7][0]= new Cell(new Rook(1,7,0));
		board[7][1]= new Cell(new Knight(1,7,1));
		board[7][2]= new Cell(new Bishop(1,7,2));
		board[7][3]= new Cell(new Queen(1,7,3));
		board[7][4]= new Cell(new King(1,7,4));
		board[7][5]= new Cell(new Bishop(1,7,5));
		board[7][6]= new Cell(new Knight(1,7,6));
		board[7][7]= new Cell(new Rook(1,7,7));
		System.out.println("Game created...");
	};

	void move(String move) {
		int x; //ascissa
		int y; //ordinata
		//chiedi a Donato, se legge Donato chiedi a Filippo di chiederti
		if (move.length()==2){
			x = move.charAt(1);
			y = Colonna.valueOf(move.substring(0,1)).ordinal();
			Pawn p;
			ArrayList<Cell> possMoves;

			if ((board[x-1][y].getPiece() instanceof Pawn )&& (board[x-1][y].getPiece().getColor() == 0)) { //neri
				p = (Pawn) board[x-1][y].getPiece();
				possMoves = p.availableMoves(board);
				if (possMoves.contains(board[x][y])) {
					board[x-1][y].setEmpty();
					board[x][y].setPiece(p);
					movesDone.add(move);
					p.incrementMoves();
				}
			}
			else if ((board[x-2][y].getPiece() instanceof Pawn )&& (board[x-2][y].getPiece().getColor() == 0)) { 
				p = (Pawn) board[x-2][y].getPiece();
				possMoves = p.availableMoves(board);
				if (possMoves.contains(board[x][y])) {
					board[x-2][y].setEmpty();
					board[x][y].setPiece(p);
					movesDone.add(move);
					p.incrementMoves();
				}
			}
			else if ((board[x+1][y].getPiece() instanceof Pawn )&& (board[x+1][y].getPiece().getColor() == 1)) { 
				p = (Pawn) board[x+1][y].getPiece();
				possMoves = p.availableMoves(board);
				if (possMoves.contains(board[x][y])) {
					board[x+1][y].setEmpty();
					board[x][y].setPiece(p);
					movesDone.add(move);
					p.incrementMoves();
				}
			}
			else if ((board[x+2][y].getPiece() instanceof Pawn )&& (board[x+2][y].getPiece().getColor() == 0)) { 
				p = (Pawn) board[x+2][y].getPiece();
				possMoves = p.availableMoves(board);
				if (possMoves.contains(board[x][y])) {
					board[x+2][y].setEmpty();
					board[x][y].setPiece(p);
					movesDone.add(move);
					p.incrementMoves();
				}
			}
		}
		else {
			char chosenPiece=move.charAt(0);
			switch(chosenPiece) {
			case 'R':
				System.err.println("Pezzo non ancora implementato");//muovi Torre
			case 'N':
				System.err.println("Pezzo non ancora implementato");//muovi Cavallo
			case 'B':
				System.err.println("Pezzo non ancora implementato");//muovi Alfiere
			case 'Q':
				System.err.println("Pezzo non ancora implementato");//muovi Donna
			case 'K':
				System.err.println("Pezzo non ancora implementato");//muovi Re
			}
			//mossa.substring(1);
		}

		//parsing della colonna in cui muovere
		//Colonna colonnaMossa= Colonna.valueOf(mossa.substring(0,1));
		//accesso con scacchiera[mossa.charAt(1)-1][colonnaMossa.ordinal()]
	}

	public void Capture(int player,String move) {
		int x; //ascissa
		int y; //ordinata
		int z; //colonna del pezzo di provenienza

		Piece p,caught;
		ArrayList<Cell> possMoves;
		x = move.charAt(3);
		z = Colonna.valueOf(move.substring(0,1)).ordinal();
		y = Colonna.valueOf(move.substring(2,3)).ordinal();

		if (player == 0) {		//neri
			if (z == y-1) {
				if (board[x][y] != null) {
					if (board[x-1][y-1].getPiece() instanceof Pawn) {       //cattura in diagonale da sinistra
						p = (Pawn)board[x-1][y-1].getPiece();
						possMoves = p.availableMoves(board);
						if (possMoves.contains(board[x][y])) {
							caught = board[x][y].getPiece();
							board[x][y].setPiece(p);
							board[x-1][y-1].setEmpty();
							movesDone.add(move);
							this.WhitesCaptured.add(caught);


						}
					}
				} 
				else {
					if (board[x-1][y-1].getPiece() instanceof Pawn) {       //cattura en Passant in diagonale da sinistra
						if (board[x-1][y].getPiece() instanceof Pawn) {
							p = (Pawn)board[x-1][y-1].getPiece();
							caught = (Pawn)board[x-1][y].getPiece();
							possMoves = p.availableMoves(board);
							if (possMoves.contains(board[x][y])&& ((Pawn) caught).enPassantCatturable()) {
								board[x][y].setPiece(p);
								board[x-1][y-1].setEmpty();
								board[x-1][y].setEmpty();
								movesDone.add(move);
								this.WhitesCaptured.add(caught);

							}
						}
					}
				}
			}
			else if ( z == y+1) { 
				if (board[x][y] != null) {
					if (board[x-1][y+1].getPiece() instanceof Pawn) { //cattura in diagonale da destra
						p = (Pawn)board[x-1][y+1].getPiece();
						possMoves = p.availableMoves(board);
						if (possMoves.contains(board[x][y])) {
							caught = board[x][y].getPiece();
							board[x][y].setPiece(p);
							board[x-1][y+1].setEmpty();
							movesDone.add(move);
							this.WhitesCaptured.add(caught);

						}
					}
				}
				else {
					if (board[x-1][y+1].getPiece() instanceof Pawn) {       //cattura en Passant in diagonale da destra
						if (board[x-1][y].getPiece() instanceof Pawn) {
							p = (Pawn)board[x-1][y+1].getPiece();
							caught = (Pawn)board[x-1][y].getPiece();
							possMoves = p.availableMoves(board);
							if (possMoves.contains(board[x][y])&& ((Pawn) caught).enPassantCatturable()) {
								board[x][y].setPiece(p);
								board[x-1][y+1].setEmpty();
								board[x-1][y].setEmpty();
								movesDone.add(move);
								this.WhitesCaptured.add(caught);

							}
						}
					}
				}

			}
			else {
				System.err.println("Moves not allowed");
				return;
			}

		}
		else {															//bianchi
			
			if (z == y-1) {
				if (board[x][y] != null) {
					if (board[x+1][y-1].getPiece() instanceof Pawn) {       //cattura in diagonale da sinistra
						p = (Pawn)board[x+1][y-1].getPiece();
						possMoves = p.availableMoves(board);
						if (possMoves.contains(board[x][y])) {
							caught = board[x][y].getPiece();
							board[x][y].setPiece(p);
							board[x+1][y-1].setEmpty();
							movesDone.add(move);
							this.BlacksCaptured.add(caught);


						}
					}
				} 
				else {
					if (board[x+1][y-1].getPiece() instanceof Pawn) {       //cattura en Passant in diagonale da sinistra
						if (board[x+1][y].getPiece() instanceof Pawn) {
							p = (Pawn)board[x+1][y-1].getPiece();
							caught = (Pawn)board[x+1][y].getPiece();
							possMoves = p.availableMoves(board);
							if (possMoves.contains(board[x][y])&& ((Pawn) caught).enPassantCatturable()) {
								board[x][y].setPiece(p);
								board[x+1][y-1].setEmpty();
								board[x+1][y].setEmpty();
								movesDone.add(move);
								this.BlacksCaptured.add(caught);

							}
						}
					}
				}
			}
			else if ( z == y+1) { 
				if (board[x][y] != null) {
					if (board[x+1][y+1].getPiece() instanceof Pawn) { //cattura in diagonale da destra
						p = (Pawn)board[x+1][y+1].getPiece();
						possMoves = p.availableMoves(board);
						if (possMoves.contains(board[x][y])) {
							caught = board[x][y].getPiece();
							board[x][y].setPiece(p);
							board[x+1][y+1].setEmpty();
							movesDone.add(move);
							this.BlacksCaptured.add(caught);

						}
					}
				}
				else {
					if (board[x+1][y+1].getPiece() instanceof Pawn) {       //cattura en Passant in diagonale da destra
						if (board[x+1][y].getPiece() instanceof Pawn) {
							p = (Pawn)board[x+1][y+1].getPiece();
							caught = (Pawn)board[x+1][y].getPiece();
							possMoves = p.availableMoves(board);
							if (possMoves.contains(board[x][y])&& ((Pawn) caught).enPassantCatturable()) {
								board[x][y].setPiece(p);
								board[x+1][y+1].setEmpty();
								board[x+1][y].setEmpty();
								movesDone.add(move);
								this.BlacksCaptured.add(caught);

							}
						}
					}
				}

			}
			else {
				System.err.println("Moves not allowed");
				return;
			}
			}
	}



	public static Cell[][] getBoard() {
		return board;
	}
	
	public ArrayList<String> getMoves(){
		return movesDone;
	}
		
}