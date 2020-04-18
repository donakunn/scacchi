package it.uniba.main;

public class Cell {
	
	Piece piece;
	
	Cell(Piece piece) {
		this.piece=piece;
	};

	public Piece getPiece() {
		return this.piece;
	}
	
	public void setEmpty() {
		this.piece = null;
	}
	void setPiece(Piece piece) {
		this.piece=piece;
	};
	
	public String toString() {
		if (piece==null) return "[ ]";
		else return "["+piece.toString()+"]";
	}
}