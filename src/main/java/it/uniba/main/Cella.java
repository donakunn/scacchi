package it.uniba.main;
public class Cella {
	boolean vuota=true;
	Pezzo pezzo;
	Cella() {};
	Cella(Pezzo pezzo) {
		this.pezzo=pezzo;
		vuota=false;
	};
	void piazza(Pezzo pezzo) {
		if(vuota==true) vuota=false;
		/* else {
		 * 	se la cella � occupata da un pezzo dello stesso giocatore che ha effettuato la mossa
		 * 	allora throw new cellaOccupataException();
		 * 	altrimenti se � occupata da un pezzo dell'altro giocatore
		 * 	allora lancia cattura();
		 * 	altrimenti procedi;
		 * }
		 */
		this.pezzo=pezzo;
		// return true; funzione Void
	};
	
	public Pezzo leggiPezzo() {			//da implementare
		return pezzo;
	
	}
}