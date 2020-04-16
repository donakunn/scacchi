package it.uniba.main;

import java.util.ArrayList;

public class Bishop extends Piece {

	
	public Bishop(int col, int x, int y) {
		this.x = x;
		this.y = y;
		this.color = col;
		if (col == 0) {
			this.pieceType = "U"+"265D";//Alfiere nero
			
		}
		else if (col == 1) {
			this.pieceType = "U"+"2657";  //Alfiere bianco
			
			
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

		
	}

	@Override
	public ArrayList<Cell> availableMoves(Cell[][] statoGioco) {
		// TODO Auto-generated method stub
		return null;
	}

}