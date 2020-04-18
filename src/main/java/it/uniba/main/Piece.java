package it.uniba.main;

import java.util.ArrayList;


public abstract class Piece {		//classe astratta che fattorizza proprietà comuni dei pezzi del gioco degli scacchi

	protected int color; //0 nero, 1 bianco
	protected String pieceType;
	protected int x; //ascissa della posizione del pezzo
	protected int y; //ordinata della posizione del pezzo
	protected ArrayList<Cell> possibleMoves = new ArrayList<Cell>();
	public abstract ArrayList<Cell> availableMoves(Cell gameState[][]); 



	//public abstract void SetTipoColore(int x);


	public String getType() {
		return this.pieceType;
	}

	public int getColor() {
		return this.color;
	}

	public String toString() {
		return pieceType;
		
	}





}

