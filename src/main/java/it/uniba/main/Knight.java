package it.uniba.main;

/**
* �entity�<br>
* Knight class, implementing the abstract class {@link Piece}<br>
* 
* @author Donato Lucente
* 
*/
class Knight extends Piece {

	Knight(int col) {

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265E"; // Cavallo nero

		} else if (col == 1) {
			this.pieceType = "\u2658"; // Cavallo bianco

		} else
			throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}

}