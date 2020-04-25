package it.uniba.main;

abstract class Piece { // classe astratta che fattorizza proprietà comuni dei pezzi del gioco degli
						// scacchi

	protected int color; // 0 nero, 1 bianco
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
