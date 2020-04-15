package it.uniba.main;

import java.util.ArrayList;


public abstract class Pezzo {		//classe astratta che fattorizza proprietà comuni dei pezzi del gioco degli scacchi

	protected int colore; //0 bianco, 1 Nero
	protected String tipoPezzo;
	protected int x; //ascissa della posizione del pezzo
	protected int y; //ordinata della posizione del pezzo
	protected ArrayList<Cella> mossePossibili = new ArrayList<Cella>();
	public abstract ArrayList<Cella> MosseDisponibili(Cella statoGioco[][]); 



	//public abstract void SetTipoColore(int x);


	/*public String GetTipo() {
		return this.tipoPezzo;
	} */

	public int GetColore() {
		return this.colore;
	}

	public String toString() {
		return tipoPezzo;
		
	}





}

