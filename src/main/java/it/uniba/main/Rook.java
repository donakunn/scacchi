package it.uniba.main;


import java.util.ArrayList;

public class Rook extends Piece {

	
	public Rook(int col, int x, int y) {
		this.x = x;
		this.y = y;
		this.colore = col;
		if (col == 0) {
			this.tipoPezzo = "U"+"2656"; //Torre bianca
			
		}
		else if (col == 1) {
			this.tipoPezzo = "U"+"265C"; //Torre nera
			
			
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

		
	}

	@Override
	public ArrayList<Cell> availableMoves(Cell[][] statoGioco) {
		// TODO Auto-generated method stub
		return null;
	}

}