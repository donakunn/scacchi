package it.uniba.main;

import java.util.ArrayList;

import static it.uniba.main.FinalPar.MAXCOL;
import static it.uniba.main.FinalPar.MAXROW;

/**
 * {@literal <<control>>}<br>
 *
 * <p><I>Titolo</I>: Menu
 *
 * <p><I>Descrizione</I>: La classe Menu crea una nuova partita. Tramite gli appositi comandi,
 * permette di visualizzare le mosse effettuate, le catture, la scacchiera e permette ai pezzi di
 * effettuare mosse e catture.
 *
 * @author Megi Gjata
 * @author Mario Giordano
 * @author Donato Lucente
 * @author Patrick Clark
 * @author Filippo Iacobellis
 */
public final class Menu {
  /** Crea una nuova partita. */
  private Game game = new Game();

  /**
   * Mostra tutti i comandi disponibili.
   *
   * @return lista di tutti i comandi disponibili
   */
  public String help() {
    return "Lista di comandi utilizzabili:\n"
        + "help\n"
        + "play\n"
        + "quit\n"
        + "Lista di comandi utilizzabili solo se in partita:\n"
        + "board\n"
        + "captures\n"
        + "moves\n"
        + "Per effettuare una mossa e' necessario specificarla in notazione algebrica; \n"
        + "Per la cattura en passant si puo' specificare 'e.p.' "
        + "o 'ep' alla fine della mossa in notazione algebrica";
  }

  /**
   * Mostra la scacchiera stampata a video.
   *
   * @return la scacchiera formata da celle convertite a stringa.
   */
  public String[][] board() {
    String[][] board = new String[MAXROW][MAXCOL];
    for (int i = 0; i < MAXROW; i++) {
      for (int j = 0; j < MAXCOL; j++) {

        board[i][j] = Game.getCell(i, j).toString();
      }
    }

    return board;
  }

  /**
   * Mostra le mosse effettuate durante il gioco.
   *
   * @return arraylist contenente tutte le mosse effettuate.
   */
  public ArrayList<String> moves() {
    return game.getMoves();
  }

  /**
   * Permette di inziare una nuova partita, svuotando gli arraylist e inizializzando la scacchiera.
   */
  public void play() {
    game.newGame();
  }

  /**
   * Permette di effettuare le mosse dei vari pezzi, le catture, gli arrocchi e cambia il turno
   * corrente.
   *
   * @param input mossa specificata dall'utente.
   * @return array contenente il pezzo che effettua la mossa o la cattura convertito a stringa, la
   *     cella di destinazione e, se si tratta di una cattura, contiene anche il pezzo catturato
   *     convertito a stringa. Se si tratta di arrocco, un array contenente la stringa che determina
   *     il tipo di arrocco.
   * @throws IllegalArgumentException eccezione che viene sollevata da una mossa illegale.
   * @throws IndexOutOfBoundsException eccezione che viene sollevata da una mossa illegale.
   * @throws IllegalMoveException eccezione che viene sollevata da una mossa illegale.
   */
  public String[] getMove(final String input)
      throws IllegalArgumentException, IndexOutOfBoundsException, IllegalMoveException {
    char chosenPiece = input.charAt(0);
    String[] pieces;
    switch (chosenPiece) {
      case 'T': // da sistemare
        pieces = game.moveRook(input);
        break;

      case 'C': // da sistemare
        pieces = game.moveKnight(input);
        break;

      case 'A':
        pieces = game.moveBishop(input);
        break;

      case 'D':
        pieces = game.moveQueen(input);
        break;

      case 'R':
        pieces = game.moveKing(input);
        break;

      case '0':
      case 'O':
        pieces = game.tryCastling(input);
        break;

      default:
        pieces = game.movePawn(input);
        break;
    }

    game.addMove(input);
    game.changeTurn();
    return pieces;
  }

  /** @return true, se e' il turno dei neri; false altrimenti. */
  public Boolean getBlackTurn() {
    return Game.getBlackTurn();
  }

  /**
   * Mostra i pezzi neri catturati.
   *
   * @return arraylist contenente i pezzi neri catturati.
   */
  public ArrayList<String> blackCaptured() {
    return game.getBlacks();
  }

  /**
   * Mostra i pezzi bianchi catturati.
   *
   * @return arraylist contenente i pezzi bianchi catturati.
   */
  public ArrayList<String> whiteCaptured() {
    return game.getWhites();
  }
}
