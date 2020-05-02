package it.uniba.main;

/**
* «entity»<br>
* Bishop class, implementing the abstract class {@link Piece}
* 
* @author Donato Lucente
* 
*/
class Bishop extends Piece {

	Bishop(int col) {

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265D"; // Alfiere nero

		} else if (col == 1) {
			this.pieceType = "\u2657"; // Alfiere bianco

		} else
			throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}

}