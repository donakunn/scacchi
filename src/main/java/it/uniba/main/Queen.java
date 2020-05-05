package it.uniba.main;

/**
 * <<entity>><br>
 * Queen class, implementing the abstract class {@link Piece}<br>
 * 
 * @author Donato Lucente
 * 
 */
class Queen extends Piece {

	Queen(int col) {

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265B"; // Regina nera

		} else if (col == 1) {
			this.pieceType = "\u2655"; // Regina bianca

		} else
			throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}

}