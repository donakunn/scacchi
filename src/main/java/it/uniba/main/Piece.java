package it.uniba.main;

abstract class Piece {
	protected int color; // 0 black, 1 white
	protected String pieceType;

	String getType() {
		return this.pieceType;
	}

	int getColor() {
		return this.color;
	}

	public String toString() {
		return pieceType;

	}

}
