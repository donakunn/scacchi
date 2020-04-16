package it.uniba.main;
public class Cell {
	boolean vuota=true;
	Piece piece;
	
	Cell(Piece piece) {
		this.piece = piece;
	
	};

	
	public Piece getPiece() {		
		return this.piece;
	
	}
	
	public void setEmpty() {
		this.piece = null;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
		
	}
}