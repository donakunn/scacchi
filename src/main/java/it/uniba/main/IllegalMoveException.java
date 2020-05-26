package it.uniba.main;

/**
 * {@literal <<no-ECB>>}<br>
 *
 * <p><I>Titolo</I>: IllegalMoveException
 *
 * <p><I>Descrizione</I>: Gestisce le eccezioni nel
 * caso in cui la mossa specificata dall'utente sia una mossa illegale.
 *
 * @author Donato Lucente
 */
public class IllegalMoveException extends Exception {

  /**
   * E' usato per la serializzazione degli oggetti e rappresenta la versione della classe
   * serializzabile.
   */
  private static final long serialVersionUID = 1L;

  /**
   * E' il costruttore della classe.
   *
   * @param s stringa contenente messaggio di errore.
   */
  public IllegalMoveException(final String s) {
    super(s);
  }
}
