package it.uniba.main;

import java.util.ArrayList;

public class Cavallo extends Pezzo {

	
	public Cavallo(int col, int x, int y) {
		this.x = x;
		this.y = y;
		this.colore = col;
		if (col == 0) {
			this.tipoPezzo = "U"+"2658"; //Cavallo bianco
			
		}
		else if (col == 1) {
			this.tipoPezzo = "U"+"265E"; //Cavallo nero
			
			
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

		
	}

	@Override
	public ArrayList<Cella> MosseDisponibili(Cella[][] statoGioco) {
		// TODO Auto-generated method stub
		return null;
	}

}