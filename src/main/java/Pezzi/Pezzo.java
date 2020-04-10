package Pezzi;

import java.util.ArrayList;
import Partita.Cella;

public abstract class Pezzo {		//classe astratta che fattorizza proprietà comuni dei pezzi del gioco degli scacchi

	private int colore;
	private String tipoPezzo;
	protected ArrayList<Cella> mossePossibili = new ArrayList<Cella>();
	public abstract ArrayList<Cella> MosseDisponibili(Cella statoGioco[][],int x, int y); 



	public void SetTipo(String tipo) {
		this.tipoPezzo = tipo;
	}

	public void SetColore (int col) {
		this.colore = col;
	}

	public String GetTipo() {
		return this.tipoPezzo;
	}

	public int GetColore() {
		return this.colore;
	}







}

