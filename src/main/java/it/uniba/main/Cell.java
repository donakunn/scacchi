package it.uniba.main;

/**
* «entity»<br>
* Cell represent a cell in the board attribute of {@link Game}. <br>
* Each cell contains the piece (if any) and getters and setters for that piece along a toString method for printing the cell.
* 
* 
* @author Filippo Iacobellis
* 
*/
class Cell {

	Piece piece;

	Cell(Piece piece) {
		this.piece = piece;
	};

	Piece getPiece() {
		return this.piece;
	}

	void setEmpty() {
		this.piece = null;
	}

	void setPiece(Piece piece) {
		this.piece = piece;
	};

	public String toString() {
		if (piece == null)
			return "[ ]";
		else
			return "[" + piece.toString() + "]";
	}
}