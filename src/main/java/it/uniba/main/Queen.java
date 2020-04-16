package it.uniba.main;


import java.util.ArrayList;


public class Queen extends Piece {

	
	public Queen(int col, int x, int y) {
		this.x = x;
		this.y = y;
		this.colore = col;
		if (col == 0) {
			this.tipoPezzo = "U"+"2655"; //Regina bianca
			
		}
		else if (col == 1) {
			this.tipoPezzo = "U"+"265B"; //Regina nera
			
			
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

		
	}

	@Override
	public ArrayList<Cell> availableMoves(Cell[][] statoGioco) {
		// TODO Auto-generated method stub
		return null;
	}

}