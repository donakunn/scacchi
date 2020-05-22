package it.uniba.main;

/**
 * <<entity>><br>
 * Piece is the abstract class for all pieces. <br>
 * Contains the color of the piece, its unicode string and a toString method for printing the
 * symbol.
 *
 * @author Donato Lucente
 */
abstract class Piece {
    private int color; // 0 black, 1 white
    private String pieceType;
    private int nMoves;

    int getColor() {
        return this.color;
    }
    
    public void setColor(int color) {
    	this.color=color;
    }
    
    public void setPieceType(String pieceType) {
    	this.pieceType=pieceType;
    }

    /**
     * @return Piece unicode string
     */
    public String toString() {
        return pieceType;
    }

    public int getnMoves() {
        return this.nMoves;
    }
    
    public void incrementMoves() {
        nMoves++;
    }
    
    public void setnMoves(int nMoves) {
    	this.nMoves=nMoves;
    }
}
