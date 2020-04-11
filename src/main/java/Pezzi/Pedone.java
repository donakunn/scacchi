package Pezzi;

import java.util.ArrayList;

import Partita.Cella;

public class Pedone extends Pezzo{

	private int MosseEffettuate;
	private Boolean EnPassantCatturabile = false;
	@Override
	public void SetTipoColore(int x) {
		if (x == 0) {
			this.tipoPezzo = "U"+"2659"; //pedone bianco
			this.colore = x;
			MosseEffettuate = 0;
		}
		else if (x == 1) {
			this.tipoPezzo = "U"+"265F"; //pedone nero
			this.colore = x;
			MosseEffettuate =0;
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}
	
	
	private Boolean CatturabileConEnPassant() {
		return this.EnPassantCatturabile;
	}
	
	
	
	

	/* public Cella Avanza(Cella cella) {
		if (this.mossePossibili.contains(cella)) {
			cella = cella.piazza(this);
			
		}
	} */


	@Override
	public ArrayList<Cella> MosseDisponibili(Cella statoCella[][], int x, int y) { //da implementare possibili catture enPassant

		mossePossibili.clear();
		if (GetColore() == 0) {											//bianchi, dall'alto verso il basso
			
			 /*  if (x == 7) 
				return this.mossePossibili; */					//da implementare in seguito cosa accade quando il pedone raggiunge la fine della scacchiera
			
			if (MosseEffettuate == 0) {
				if (statoCella[x+1][y].leggiPezzo() == null) {

					this.mossePossibili.add(statoCella[x+1][y]); 
				}
				if (statoCella[x+2][y].leggiPezzo() == null) {

					this.mossePossibili.add(statoCella[x+2][y]);		//da controllare il verso della scacchiera, sto assumendo che si trovi in alto
				}
				
			}
			
			else if (statoCella[x+1][y].leggiPezzo() == null) {
				this.mossePossibili.add(statoCella[x+1][y]);
			}
			if ((y>0) && (statoCella[x+1][y-1].leggiPezzo() == null)&&statoCella[x+1][y-1].leggiPezzo().GetColore()!=this.GetColore())
				mossePossibili.add(statoCella[x+1][y-1]);
			else if ((y<7) && (statoCella[x+1][y+1].leggiPezzo() == null)&&statoCella[x+1][y-1].leggiPezzo().GetColore()!=this.GetColore())
				mossePossibili.add(statoCella[x+1][y+1]);
			
			
			
					
			
		}
		
		else {															//neri, dal basso verso l'alto
			
			/*if (x == 0) {			//da implementare in seguito cosa accade quando il pedone raggiunge la fine della scacchiera
			
				}*/
			
			if (MosseEffettuate == 0) {
				if (statoCella[x-1][y].leggiPezzo() == null) {

					this.mossePossibili.add(statoCella[x-1][y]); 
				}
				if (statoCella[x-2][y].leggiPezzo() == null) {

					this.mossePossibili.add(statoCella[x-2][y]);		//da controllare il verso della scacchiera, sto assumendo che si trovi in alto
				}
				
			}
			
			else if (statoCella[x-1][y].leggiPezzo() == null) {
				this.mossePossibili.add(statoCella[x-1][y]);
			}
			
			else if (statoCella[x+1][y].leggiPezzo() == null) {
				this.mossePossibili.add(statoCella[x+1][y]);
			}
			if ((y>0) && (statoCella[x-1][y-1].leggiPezzo() == null)&&statoCella[x+1][y-1].leggiPezzo().GetColore()!=this.GetColore())
				mossePossibili.add(statoCella[x-1][y-1]);
			else if ((y<7) && (statoCella[x-1][y+1].leggiPezzo() == null)&&statoCella[x+1][y-1].leggiPezzo().GetColore()!=this.GetColore())
				mossePossibili.add(statoCella[x-1][y+1]);
			
		}
		return mossePossibili; 
	}





}
