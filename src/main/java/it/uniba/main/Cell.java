package it.uniba.main;

/**
 * {@literal <<entity>}><br>
 * <p><I>Titolo</I>: Cell</p>
 * <p><I>Descrizione</I>: La classe Cell viene utilizzata per gestire le celle della scacchiera all'interno del gioco. Ogni cella contiene un 
 * pezzo e puo' essere svuotata o riempita a seconda delle necessita'. Contiene inoltre un metodo che permette di convertire
 * il pezzo in stringa per poterlo stampare.
 *
 * @author Filippo Iacobellis
 */
class Cell {

	/**
	 * Si riferisce al pezzo da inserire o eliminare nella cella.
	 */
    private Piece piece;

    /**
     * E' il costruttore della classe.
     * 
     * @param piece pezzo contenuto nella cella.
     */
    Cell(final Piece piece) {
        this.piece = piece;
    }

    /**
     * 
     * @return pezzo contenuto nella cella.
     */
    Piece getPiece() {
        return this.piece;
    }

    /**
     * Svuota la cella assegnando al pezzo il valore null.
     */
    void setEmpty() {
        this.piece = null;
    }

    /**
     * Crea il pezzo all'interno della cella.
     * 
     * @param piece pezzo contenuto nella cella.
     */
    void setPiece(final Piece piece) {
        this.piece = piece;
    }

    /**
     * Converte il pezzo a stringa.
     */
    public String toString() {
        if (piece == null) {
            return "[ ]";
        } else {
            return "[" + piece.toString() + "]";
        }
    }
}
