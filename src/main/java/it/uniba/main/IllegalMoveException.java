package it.uniba.main;

/**
 * <<no-ECB>><br>
 * <p><I>Titolo</I>: IllegalMoveException</p>
 * <p><I>Descrizione</I>: La classe IllegalMoveException e' utilizzata per sollevare eccezioni nel caso in cui la mossa specificata 
 * dall'utente sia una mossa illegale.</p>
 *
 * @author Donato Lucente
 */
public class IllegalMoveException extends Exception {

	/**
	 * E' usato per la serializzazione degli oggetti e rappresenta la versione della classe serializzabile.
	 */
    private static final long serialVersionUID = 1L;

    /**
     * E' il costruttore della classe.
     * @param s stringa corrispondente all'eccezione.
     */
    public IllegalMoveException(final String s) {
        super(s);
    }
}
