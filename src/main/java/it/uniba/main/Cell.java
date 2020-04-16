package it.uniba.main;
public class Cell {
	boolean vuota=true;
	Piece pezzo;
	Cell() {};
	Cell(Piece pezzo) {
		this.pezzo=pezzo;
		vuota=false;
	};
	void place(Piece pezzo) {
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
	
	public Piece getPiece() {			//da implementare
		return pezzo;
	
	}
}