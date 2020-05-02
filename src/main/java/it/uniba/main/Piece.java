package it.uniba.main;

/**
* «entity»<br>
* Piece is the abstract class for all pieces. <br>
* Contains the color of the piece, its unicode string and a toString method for printing
* the symbol.
* 
* @author Donato Lucente
* 
*/
abstract class Piece {
	protected int color; // 0 black, 1 white
	protected String pieceType;

	String getType() {
		return this.pieceType;
	}

	int getColor() {
		return this.color;
	}
	/**
	 * @return Piece unicode string
	 */
	public String toString() {
		return pieceType;

	}

}
