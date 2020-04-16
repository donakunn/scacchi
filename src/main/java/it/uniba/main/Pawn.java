package it.uniba.main;

import java.util.ArrayList;


public class Pawn extends Piece{

	private int MosseEffettuate; //numero di mosse effettuate
	
	public Pawn(int col, int x, int y) {		//costruttore classe Pedone
		MosseEffettuate = 0;
		this.x = x;
		this.y = y;
		this.colore = col;
		if (col == 0) {
			this.tipoPezzo = "U"+"2659"; //pedone bianco
			
		}
		else if (col == 1) {
			this.tipoPezzo = "U"+"265F"; //pedone nero
			
			
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}
	
	
	private Boolean CatturabileConEnPassant() {											//restituisce true se il pedone ha effettuato una sola mossa con salto di 2, false altrimenti
		if ((GetColor() == 0)&& (MosseEffettuate == 1) && (this.x == 3 )) {
			return true;
		}
		else if ((GetColor() == 1)&& (MosseEffettuate == 1) && (this.x == 4 )) {
			return true;
		} 
		else return false;
	}
	
	


	@Override
	public ArrayList<Cell> MosseDisponibili(Cell statoGioco[][]) { //restituisce lista di mosse disponibili per il Pedone

		mossePossibili.clear();
		if (GetColor() == 0) {											//bianchi, dall'alto verso il basso
			
			 /*  if (x == 7) 
				return this.mossePossibili; */					//da implementare in seguito cosa accade quando il pedone raggiunge la fine della scacchiera
			
			if (MosseEffettuate == 0) {								//se prima mossa è possibili sia andare avanti di 2 che di 1
				if (statoGioco[x+1][y].getPiece() == null) {

					this.mossePossibili.add(statoGioco[x+1][y]); 
				}
				if (statoGioco[x+2][y].getPiece() == null) {			

					this.mossePossibili.add(statoGioco[x+2][y]);		//da controllare il verso della scacchiera, sto assumendo che si trovi in alto
				}
				
			}
			
			else if (statoGioco[x+1][y].getPiece() == null) {				//altrimenti va avanti solo di 1
				this.mossePossibili.add(statoGioco[x+1][y]);
			}
			if ((y>0) && (statoGioco[x+1][y-1].getPiece() != null)&&(statoGioco[x+1][y-1].getPiece().GetColor()!=this.GetColor()))					//controlla se può catturare in diagonale a sinistra o destra, se possibile aggiunge la posizione alla cella
				mossePossibili.add(statoGioco[x+1][y-1]);
			else if ((y<7) && (statoGioco[x+1][y+1].getPiece() != null)&&(statoGioco[x+1][y-1].getPiece().GetColor()!=this.GetColor()))
				mossePossibili.add(statoGioco[x+1][y+1]);
			
			
			if ((y>0) && (statoGioco[x+1][y-1].getPiece() == null)&&(statoGioco[x][y-1].getPiece().GetColor()!=this.GetColor())&& ((statoGioco[x][y-1].getPiece() instanceof Pawn))) {		//controlla se può catturare in diagonale a sinistra o destra con en passant, se possibile aggiunge la posizione alla cella
				Pawn p = (Pawn) statoGioco[x][y-1].getPiece();
				if (p.CatturabileConEnPassant()) {
					mossePossibili.add(statoGioco[x][y-1]); 
				}
			}
			
			else if ((y<7) && (statoGioco[x+1][y+1].getPiece() == null)&&(statoGioco[x][y+1].getPiece().GetColor()!=this.GetColor())&& ((statoGioco[x][y+1].getPiece() instanceof Pawn))) {
				Pawn p = (Pawn) statoGioco[x][y+1].getPiece();
				if (p.CatturabileConEnPassant()) {
					mossePossibili.add(statoGioco[x][y+1]); 
				}
			}

			
			
			
					
			
		}
		
		else {															//neri, dal basso verso l'alto
			
			/*if (x == 0) {			//da implementare in seguito cosa accade quando il pedone raggiunge la fine della scacchiera
			
				}*/
			
			if (MosseEffettuate == 0) {									//se prima mossa è possibili sia andare avanti di 2 che di 1
				if (statoGioco[x-1][y].getPiece() == null) {

					this.mossePossibili.add(statoGioco[x-1][y]); 
				}
				if (statoGioco[x-2][y].getPiece() == null) {

					this.mossePossibili.add(statoGioco[x-2][y]);		//da controllare il verso della scacchiera, sto assumendo che si trovi in alto
				}
				
			}
			
			else if (statoGioco[x-1][y].getPiece() == null) {				//altrimenti va avanti solo di 1
				this.mossePossibili.add(statoGioco[x-1][y]);
			}
			
			
			if ((y>0) && (statoGioco[x-1][y-1].getPiece() != null)&&statoGioco[x+1][y-1].getPiece().GetColor()!=this.GetColor())				//controlla se può catturare in diagonale a sinistra o destra, se possibile aggiunge la posizione alla cella	
				mossePossibili.add(statoGioco[x-1][y-1]);
			else if ((y<7) && (statoGioco[x-1][y+1].getPiece() != null)&&statoGioco[x+1][y-1].getPiece().GetColor()!=this.GetColor())
				mossePossibili.add(statoGioco[x-1][y+1]);
			
			if ((y>0) && (statoGioco[x-1][y-1].getPiece() == null)&&(statoGioco[x][y-1].getPiece().GetColor()!=this.GetColor())&& ((statoGioco[x][y-1].getPiece() instanceof Pawn))) {			//controlla se può catturare in diagonale a sinistra o destra con en passant, se possibile aggiunge la posizione alla cella
				
				Pawn p = (Pawn) statoGioco[x][y-1].getPiece();
				if (p.CatturabileConEnPassant()) {
					mossePossibili.add(statoGioco[x][y-1]); 
				}
			}
			
			else if ((y<7) && (statoGioco[x-1][y+1].getPiece() == null)&&(statoGioco[x][y+1].getPiece().GetColor()!=this.GetColor())&& ((statoGioco[x][y+1].getPiece() instanceof Pawn))) {
				Pawn p = (Pawn) statoGioco[x][y+1].getPiece();
				if (p.CatturabileConEnPassant()) {
					mossePossibili.add(statoGioco[x][y+1]); 
				}
			}
		}
		return mossePossibili; 
	}








	
	}
