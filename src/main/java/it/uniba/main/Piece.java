package it.uniba.main;

/**
 * {@literal <<entity>>}<br>
 * <p><I>Titolo</I>: Piece</p>
 * <p><I>Descrizione</I>: La classe Piece e' la classe astratta per tutti i pezzi. Imposta i colori dei pezzi, tiene conto delle mosse
 * e converte i pezzi a stringa per poterli stampare a video.
 *
 * @author Donato Lucente
 */
abstract class Piece {
	/**
	 * Colore del pezzo.
	 */
    private int color; // 0 black, 1 white
    /**
     * Tipo di pezzo.
     */
    private String pieceType;
    /**
     * Numero di mosse effettuate.
     */
    private int nMoves;

    /**
     * 
     * @return colore del pezzo: 0 se e' nero, 1 se e' bianco.
     */
    int getColor() {
        return this.color;
    }
    
    /**
     * 
     * @param color colore del pezzo: 0 se e' nero, 1 se e' bianco.
     */
    public void setColor(int color) {
    	this.color=color;
    }
    
    /**
     * 
     * @param pieceType tipo di pezzo.  
     */
    public void setPieceType(String pieceType) {
    	this.pieceType=pieceType;
    }

    /**
     * @return pezzo convertito a stringa.
     */
    public String toString() {
        return pieceType;
    }

    /**
     * 
     * @return numero di mosse effettuate.
     */
    public int getnMoves() {
        return this.nMoves;
    }
    
    /**
     * Incrementa il contatore dell mosse effettuate.
     */
    public void incrementMoves() {
        nMoves++;
    }
    
    /**
     * 
     * @param nMoves numero di mosse effettuate.
     */
    public void setnMoves(int nMoves) {
    	this.nMoves=nMoves;
    }
}
