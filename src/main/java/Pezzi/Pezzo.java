package Pezzi;

import java.util.ArrayList;
import Partita.Cella;

public abstract class Pezzo {		//classe astratta che fattorizza proprietà comuni dei pezzi del gioco degli scacchi

	protected int colore; //0 bianco, 1 Nero
	protected String tipoPezzo;
	protected ArrayList<Cella> mossePossibili = new ArrayList<Cella>();
	public abstract ArrayList<Cella> MosseDisponibili(Cella statoGioco[][],int x, int y); 



	public abstract void SetTipoColore(int x);


	public String GetTipo() {
		return this.tipoPezzo;
	}

	public int GetColore() {
		return this.colore;
	}







}

