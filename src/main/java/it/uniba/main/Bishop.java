package it.uniba.main;

import java.util.ArrayList;

public class Bishop extends Piece {

	
	public Bishop(int col, int x, int y) {
		this.x = x;
		this.y = y;
		this.colore = col;
		if (col == 0) {
			this.tipoPezzo = "U"+"2657"; //Alfiere bianco
			
		}
		else if (col == 1) {
			this.tipoPezzo = "U"+"265D"; //Alfiere nero
			
			
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

		
	}

	@Override
	public ArrayList<Cell> MosseDisponibili(Cell[][] statoGioco) {
		// TODO Auto-generated method stub
		return null;
	}

}