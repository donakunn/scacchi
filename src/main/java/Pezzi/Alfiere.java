package Pezzi;

import java.util.ArrayList;

import Partita.Cella;

public class Alfiere extends Pezzo {

	
	public Alfiere(int col, int x, int y) {
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
	public ArrayList<Cella> MosseDisponibili(Cella[][] statoGioco) {
		// TODO Auto-generated method stub
		return null;
	}

}