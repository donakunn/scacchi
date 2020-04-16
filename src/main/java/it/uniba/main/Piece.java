package it.uniba.main;

import java.util.ArrayList;


public abstract class Piece {		//classe astratta che fattorizza proprietà comuni dei pezzi del gioco degli scacchi

	protected int colore; //0 bianco, 1 Nero
	protected String tipoPezzo;
	protected int x; //ascissa della posizione del pezzo
	protected int y; //ordinata della posizione del pezzo
	protected ArrayList<Cell> possibleMoves = new ArrayList<Cell>();
	public abstract ArrayList<Cell> availableMoves(Cell statoGioco[][]); 



	//public abstract void SetTipoColore(int x);


	/*public String GetTipo() {
		return this.tipoPezzo;
	} */

	public int GetColor() {
		return this.colore;
	}

	public String toString() {
		return tipoPezzo;
		
	}





}

