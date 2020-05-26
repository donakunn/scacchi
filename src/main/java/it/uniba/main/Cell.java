package it.uniba.main;

/**
 * {@literal <<entity>>}<br>
 * <p><I>Titolo</I>: Cell</p>
 * <p><I>Descrizione</I>: La classe Cell viene utilizzata
 * per gestire le celle della scacchiera all'interno del gioco.
 * Ogni cella puo' essere vuota o contenere un pezzo e puo' essere svuotata o riempita a seconda delle necessita'.
 * @author Filippo Iacobellis
 */
class Cell {

	/**
	 * Si riferisce al pezzo da inserire o eliminare nella cella.
	 */
    private Piece piece;

    /**
     * E' il costruttore della classe. Crea la cella con l'eventuale pezzo contenuto.
     * 
     * @param p pezzo contenuto nella cella.
     */
    Cell(final Piece p) {
        this.piece = p;
    }

    /**
     * 
     * @return stringa dell'eventuale pezzo contenuto nella cella.
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
     * @param p pezzo contenuto nella cella.
     */
    void setPiece(final Piece p) {
        this.piece = p;
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
