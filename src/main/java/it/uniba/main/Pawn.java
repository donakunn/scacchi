package it.uniba.main;

/**
* �entity�<br>
* Pawn class, implementing the abstract class {@link Piece}<br>
* Includes a counter for moves done and a method to verify the en-passant capturable condition
* 
* @author Donato Lucente
* 
*/
class Pawn extends Piece {


	Pawn(int col) { // costruttore classe Pedone
		nMoves = 0;

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265F"; // pedone nero

		} else if (col == 1) {
			this.pieceType = "\u2659"; // pedone bianco

		} else
			throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}

	void incrementMoves() {
		this.nMoves++;
	}

	Boolean enPassantCatturable(int x) { // restituisce true se il pedone ha effettuato una sola mossa con salto di 2,
											// false altrimenti
		if ((getColor() == 0) && (nMoves == 1) && (x == 4)) {
			return true;
		} else if ((getColor() == 1) && (nMoves == 1) && (x == 3)) {
			return true;
		} else
			return false;
	}

}
