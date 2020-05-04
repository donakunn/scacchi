package it.uniba.main;

/**
* �entity�<br>
* Rook class, implementing the abstract class {@link Piece}<br>
*
* @author Donato Lucente
* 
*/
class Rook extends Piece {

	Rook(int col) {

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265C"; // Torre nera
			nMoves = 0;

		} else if (col == 1) {

			this.pieceType = "\u2656"; // Torre bianca
			nMoves = 0;

		} else
			throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}

	void incrementMoves() {
		nMoves++;
	}

	int getNumberOfMoves() {
		return this.nMoves;
	}

}