package it.uniba.main;

/**
 * {@literal <<entity>>}<br>
 *
 * <p><I>Titolo</I>: Piece
 *
 * <p><I>Descrizione</I>: La classe Piece e' la classe astratta per tutti i pezzi. Imposta i colori
 * dei pezzi, tiene conto delle mosse effettuate, imposta e restituisce la stringa corrispondente al pezzo.
 *
 * @author Donato Lucente
 */
abstract class Piece {
  /** Colore del pezzo. */
  private int color; // 0 black, 1 white
  /** Tipo di pezzo. */
  private String pieceType;
  /** Numero di mosse effettuate. */
  private int nMoves;

  /** @return colore del pezzo: 0 se e' nero, 1 se e' bianco. */
  int getColor() {
    return this.color;
  }

  /** @param col colore del pezzo: 0 se e' nero, 1 se e' bianco. */
  public void setColor(final int col) {
    this.color = col;
  }

  /** @param pType tipo di pezzo. */
  public void setPieceType(final String pType) {
    this.pieceType = pType;
  }

  /** @return pezzo convertito a stringa. */
  public String toString() {
    return pieceType;
  }

  /** @return numero di mosse effettuate dal pezzo. */
  public int getnMoves() {
    return this.nMoves;
  }

  /** Incrementa il contatore dell mosse effettuate dal pezzo. */
  public void incrementMoves() {
    nMoves++;
  }

  /** @param nMov numero di mosse effettuate dal pezzo. */
  public void setnMoves(final int nMov) {
    this.nMoves = nMov;
  }
}
