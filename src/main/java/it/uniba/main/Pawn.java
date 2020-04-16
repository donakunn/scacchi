package it.uniba.main;

import java.util.ArrayList;


public class Pawn extends Piece{

	private int movesDone; //numero di mosse effettuate
	
	public Pawn(int col, int x, int y) {		//costruttore classe Pedone
		movesDone = 0;
		this.x = x;
		this.y = y;
		this.color = col;
		if (col == 0) {
			this.pieceType = "U"+"265F"; //pedone nero
			
		}
		else if (col == 1) {
			this.pieceType = "U"+"2659"; //pedone bianco
			
			
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}
	
	public void incrementMoves() {
		this.movesDone ++;
	}
	
	
	private Boolean CatturabileConEnPassant() {											//restituisce true se il pedone ha effettuato una sola mossa con salto di 2, false altrimenti
		if ((getColor() == 0)&& (movesDone == 1) && (this.x == 3 )) {
			return true;
		}
		else if ((getColor() == 1)&& (movesDone == 1) && (this.x == 4 )) {
			return true;
		} 
		else return false;
	}
	
	


	@Override
	public ArrayList<Cell> availableMoves(Cell statoGioco[][]) { //restituisce lista di mosse disponibili per il Pedone

		possibleMoves.clear();
		if (getColor() == 0) {											//neri, dall'alto verso il basso
			
			 /*  if (x == 7) 
				return this.mossePossibili; */					//da implementare in seguito cosa accade quando il pedone raggiunge la fine della scacchiera
			
			if (movesDone == 0) {								//se prima mossa è possibili sia andare avanti di 2 che di 1
				if (statoGioco[x+1][y].getPiece() == null) {

					this.possibleMoves.add(statoGioco[x+1][y]); 
				}
				if (statoGioco[x+2][y].getPiece() == null) {			

					this.possibleMoves.add(statoGioco[x+2][y]);		//da controllare il verso della scacchiera, sto assumendo che si trovi in alto
				}
				
			}
			
			else if (statoGioco[x+1][y].getPiece() == null) {				//altrimenti va avanti solo di 1
				this.possibleMoves.add(statoGioco[x+1][y]);
			}
			if ((y>0) && (statoGioco[x+1][y-1].getPiece() != null)&&(statoGioco[x+1][y-1].getPiece().getColor()!=this.getColor()))					//controlla se può catturare in diagonale a sinistra o destra, se possibile aggiunge la posizione alla cella
				possibleMoves.add(statoGioco[x+1][y-1]);
			else if ((y<7) && (statoGioco[x+1][y+1].getPiece() != null)&&(statoGioco[x+1][y-1].getPiece().getColor()!=this.getColor()))
				possibleMoves.add(statoGioco[x+1][y+1]);
			
			
			if ((y>0) && (statoGioco[x+1][y-1].getPiece() == null)&&(statoGioco[x][y-1].getPiece().getColor()!=this.getColor())&& ((statoGioco[x][y-1].getPiece() instanceof Pawn))) {		//controlla se può catturare in diagonale a sinistra o destra con en passant, se possibile aggiunge la posizione alla cella
				Pawn p = (Pawn) statoGioco[x][y-1].getPiece();
				if (p.CatturabileConEnPassant()) {
					possibleMoves.add(statoGioco[x][y-1]); 
				}
			}
			
			else if ((y<7) && (statoGioco[x+1][y+1].getPiece() == null)&&(statoGioco[x][y+1].getPiece().getColor()!=this.getColor())&& ((statoGioco[x][y+1].getPiece() instanceof Pawn))) {
				Pawn p = (Pawn) statoGioco[x][y+1].getPiece();
				if (p.CatturabileConEnPassant()) {
					possibleMoves.add(statoGioco[x][y+1]); 
				}
			}

			
			
			
					
			
		}
		
		else {															//bianchi, dal basso verso l'alto
			
			/*if (x == 0) {			//da implementare in seguito cosa accade quando il pedone raggiunge la fine della scacchiera
			
				}*/
			
			if (movesDone == 0) {									//se prima mossa è possibili sia andare avanti di 2 che di 1
				if (statoGioco[x-1][y].getPiece() == null) {

					this.possibleMoves.add(statoGioco[x-1][y]); 
				}
				if (statoGioco[x-2][y].getPiece() == null) {

					this.possibleMoves.add(statoGioco[x-2][y]);		//da controllare il verso della scacchiera, sto assumendo che si trovi in alto
				}
				
			}
			
			else if (statoGioco[x-1][y].getPiece() == null) {				//altrimenti va avanti solo di 1
				this.possibleMoves.add(statoGioco[x-1][y]);
			}
			
			
			if ((y>0) && (statoGioco[x-1][y-1].getPiece() != null)&&statoGioco[x+1][y-1].getPiece().getColor()!=this.getColor())				//controlla se può catturare in diagonale a sinistra o destra, se possibile aggiunge la posizione alla cella	
				possibleMoves.add(statoGioco[x-1][y-1]);
			else if ((y<7) && (statoGioco[x-1][y+1].getPiece() != null)&&statoGioco[x+1][y-1].getPiece().getColor()!=this.getColor())
				possibleMoves.add(statoGioco[x-1][y+1]);
			
			if ((y>0) && (statoGioco[x-1][y-1].getPiece() == null)&&(statoGioco[x][y-1].getPiece().getColor()!=this.getColor())&& ((statoGioco[x][y-1].getPiece() instanceof Pawn))) {			//controlla se può catturare in diagonale a sinistra o destra con en passant, se possibile aggiunge la posizione alla cella
				
				Pawn p = (Pawn) statoGioco[x][y-1].getPiece();
				if (p.CatturabileConEnPassant()) {
					possibleMoves.add(statoGioco[x][y-1]); 
				}
			}
			
			else if ((y<7) && (statoGioco[x-1][y+1].getPiece() == null)&&(statoGioco[x][y+1].getPiece().getColor()!=this.getColor())&& ((statoGioco[x][y+1].getPiece() instanceof Pawn))) {
				Pawn p = (Pawn) statoGioco[x][y+1].getPiece();
				if (p.CatturabileConEnPassant()) {
					possibleMoves.add(statoGioco[x][y+1]); 
				}
			}
		}
		return possibleMoves; 
	}
}
