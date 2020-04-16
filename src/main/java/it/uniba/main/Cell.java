package it.uniba.main;

public class Cell {
	boolean empty=true;
	Piece piece;
	
	Cell(Piece piece) {
		this.piece=piece;
		empty=false;
	};

	public Piece getPiece() {
		return this.piece;
	}
	
	public void setEmpty() {
		this.piece = null;
	}
	void setPiece(Piece piece) {
		if(empty==true) empty=false;
		this.piece=piece;
	};
	
	public String toString() {
		return "["+piece.toString()+"]";
	}
}