package Pezzi;

import java.util.ArrayList;

import Partita.Cella;

public class Pedone extends Pezzo{

	private int MosseEffettuate; //numero di mosse effettuate
	private int x; //ascissa della posizione del pedone
	private int y; //ordinata della posizione del pedone
	
	public Pedone(int col, int x, int y) {		//costruttore classe Pedone
		if (col == 0) {
			this.tipoPezzo = "U"+"2659"; //pedone bianco
			this.colore = col;
			MosseEffettuate = 0;
			this.x = x;
			this.y = y;
		}
		else if (col == 1) {
			this.tipoPezzo = "U"+"265F"; //pedone nero
			this.colore = col;
			MosseEffettuate =0;
			this.x = x;
			this.y = y;
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}
	
	
	private Boolean CatturabileConEnPassant() {											//restituisce true se il pedone ha effettuato una sola mossa con salto di 2, false altrimenti
		if ((GetColore() == 0)&& (MosseEffettuate == 1) && (this.x == 3 )) {
			return true;
		}
		else if ((GetColore() == 1)&& (MosseEffettuate == 1) && (this.x == 4 )) {
			return true;
		} 
		else return false;
	}
	
	


	@Override
	public ArrayList<Cella> MosseDisponibili(Cella statoGioco[][]) { //restituisce lista di mosse disponibili per il Pedone

		mossePossibili.clear();
		if (GetColore() == 0) {											//bianchi, dall'alto verso il basso
			
			 /*  if (x == 7) 
				return this.mossePossibili; */					//da implementare in seguito cosa accade quando il pedone raggiunge la fine della scacchiera
			
			if (MosseEffettuate == 0) {								//se prima mossa è possibili sia andare avanti di 2 che di 1
				if (statoGioco[x+1][y].leggiPezzo() == null) {

					this.mossePossibili.add(statoGioco[x+1][y]); 
				}
				if (statoGioco[x+2][y].leggiPezzo() == null) {			

					this.mossePossibili.add(statoGioco[x+2][y]);		//da controllare il verso della scacchiera, sto assumendo che si trovi in alto
				}
				
			}
			
			else if (statoGioco[x+1][y].leggiPezzo() == null) {				//altrimenti va avanti solo di 1
				this.mossePossibili.add(statoGioco[x+1][y]);
			}
			if ((y>0) && (statoGioco[x+1][y-1].leggiPezzo() != null)&&(statoGioco[x+1][y-1].leggiPezzo().GetColore()!=this.GetColore()))					//controlla se può catturare in diagonale a sinistra o destra, se possibile aggiunge la posizione alla cella
				mossePossibili.add(statoGioco[x+1][y-1]);
			else if ((y<7) && (statoGioco[x+1][y+1].leggiPezzo() != null)&&(statoGioco[x+1][y-1].leggiPezzo().GetColore()!=this.GetColore()))
				mossePossibili.add(statoGioco[x+1][y+1]);
			
			
			if ((y>0) && (statoGioco[x+1][y-1].leggiPezzo() == null)&&(statoGioco[x][y-1].leggiPezzo().GetColore()!=this.GetColore())&& ((statoGioco[x][y-1].leggiPezzo() instanceof Pedone))) {		//controlla se può catturare in diagonale a sinistra o destra con en passant, se possibile aggiunge la posizione alla cella
				Pedone p = (Pedone) statoGioco[x][y-1].leggiPezzo();
				if (p.CatturabileConEnPassant()) {
					mossePossibili.add(statoGioco[x][y-1]); 
				}
			}
			
			else if ((y<7) && (statoGioco[x+1][y+1].leggiPezzo() == null)&&(statoGioco[x][y+1].leggiPezzo().GetColore()!=this.GetColore())&& ((statoGioco[x][y+1].leggiPezzo() instanceof Pedone))) {
				Pedone p = (Pedone) statoGioco[x][y+1].leggiPezzo();
				if (p.CatturabileConEnPassant()) {
					mossePossibili.add(statoGioco[x][y+1]); 
				}
			}

			
			
			
					
			
		}
		
		else {															//neri, dal basso verso l'alto
			
			/*if (x == 0) {			//da implementare in seguito cosa accade quando il pedone raggiunge la fine della scacchiera
			
				}*/
			
			if (MosseEffettuate == 0) {									//se prima mossa è possibili sia andare avanti di 2 che di 1
				if (statoGioco[x-1][y].leggiPezzo() == null) {

					this.mossePossibili.add(statoGioco[x-1][y]); 
				}
				if (statoGioco[x-2][y].leggiPezzo() == null) {

					this.mossePossibili.add(statoGioco[x-2][y]);		//da controllare il verso della scacchiera, sto assumendo che si trovi in alto
				}
				
			}
			
			else if (statoGioco[x-1][y].leggiPezzo() == null) {				//altrimenti va avanti solo di 1
				this.mossePossibili.add(statoGioco[x-1][y]);
			}
			
			
			if ((y>0) && (statoGioco[x-1][y-1].leggiPezzo() != null)&&statoGioco[x+1][y-1].leggiPezzo().GetColore()!=this.GetColore())				//controlla se può catturare in diagonale a sinistra o destra, se possibile aggiunge la posizione alla cella	
				mossePossibili.add(statoGioco[x-1][y-1]);
			else if ((y<7) && (statoGioco[x-1][y+1].leggiPezzo() != null)&&statoGioco[x+1][y-1].leggiPezzo().GetColore()!=this.GetColore())
				mossePossibili.add(statoGioco[x-1][y+1]);
			
			if ((y>0) && (statoGioco[x-1][y-1].leggiPezzo() == null)&&(statoGioco[x][y-1].leggiPezzo().GetColore()!=this.GetColore())&& ((statoGioco[x][y-1].leggiPezzo() instanceof Pedone))) {			//controlla se può catturare in diagonale a sinistra o destra con en passant, se possibile aggiunge la posizione alla cella
				
				Pedone p = (Pedone) statoGioco[x][y-1].leggiPezzo();
				if (p.CatturabileConEnPassant()) {
					mossePossibili.add(statoGioco[x][y-1]); 
				}
			}
			
			else if ((y<7) && (statoGioco[x-1][y+1].leggiPezzo() == null)&&(statoGioco[x][y+1].leggiPezzo().GetColore()!=this.GetColore())&& ((statoGioco[x][y+1].leggiPezzo() instanceof Pedone))) {
				Pedone p = (Pedone) statoGioco[x][y+1].leggiPezzo();
				if (p.CatturabileConEnPassant()) {
					mossePossibili.add(statoGioco[x][y+1]); 
				}
			}
		}
		return mossePossibili; 
	}








	
	}
