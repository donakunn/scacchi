package it.uniba.main;

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