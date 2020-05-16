package it.uniba.main;

// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.net.URISyntaxException;
// import java.security.GeneralSecurityException;
import java.util.Scanner;

// import it.uniba.sotorrent.GoogleDocsUtils;

/**
 * <<boundary>><br>
 * The main class for the project. It must be customized to meet the project assignment
 * specifications.
 *
 * <p><b>DO NOT RENAME</b>
 */
public final class AppMain {
  /** Private constructor. Change if needed. */
  private AppMain() {}

  /**
   * This is the main entry of the application. It executes the chess app.
   *
   * @param args The command-line arguments.
   */
  public static void main(final String[] args) {
    Menu menu = new Menu();
    String turn = "white";
    boolean inGame = false;
    boolean exit = false;
    Scanner in = new Scanner(System.in);
    System.out.println("Benvenuto nel gioco degli scacchi!");
    System.out.println(
        "Inserire comando da eseguire oppure inserire help per la lista dei comandi.");

    while (!exit) {

      if (Game.getTurn() == false) {
        turn = "neri";
      } else {
        turn = "bianchi";
      }
      if (inGame) {
        System.out.println("Inserire comando (turno dei " + turn + ")");
      } else {
        System.out.println("Inserire comando:");
      }
      String input = in.nextLine();
      switch (input) {
        case "board":
          if (inGame) {
            menu.board();
            break;
          } else {
            System.err.println("Devi essere in gioco per usare questo comando.");
            break;
          }
        case "captures":
          if (inGame) {
            menu.captures();
            break;
          } else {
            System.err.println("Devi essere in gioco per usare questo comando.");
            break;
          }
        case "help":
          menu.help();
          break;
        case "moves":
          if (inGame) {
            menu.moves();
            break;
          } else {
            System.err.println("Devi essere in gioco per usare questo comando.");
            break;
          }
        case "play":
          if (!inGame) {
            System.out.println("Creo nuova partita..");
            inGame = true;
            menu.play();
            System.out.println("Partita creata.");
          } else {
            System.out.println("Partita gia'� avviata. Vuoi cancellarla ed iniziare un nuova?");
            while (true) {
              String answer = in.nextLine();
              if (answer.toUpperCase().equals("SI") || answer.toUpperCase().equals("YES")) {
                System.out.println("Cancello la partita in corso e ne avvio una nuova");
                menu.play();
                menu.resetTurn();
                break;
              } else if (answer.toUpperCase().equals("NO")) {
                break;
              } else System.out.println("Risposta non valida; inserire si (yes) o no");
            }
          }
          break;
        case "quit":
          String answer;
          System.out.println("Sei sicuro di voler uscire? ");
          answer = in.nextLine();
          answer = answer.toUpperCase();

          if (answer.equals("YES") || answer.equals("SI") || answer.equals("SÌ")) {
            in.close();
            exit = true;
            break;
          } else if (answer.equals("NO")) {
            exit = false;
            break;
          } else {
            System.out.println("Risposta non valida, inserisci si (yes) o no");
          }

        default:
          if (inGame) {
            try {
              menu.getMove(input);
            } catch (StringIndexOutOfBoundsException e) {
              System.err.println("Comando non valido.");
            }

            break;
          } else {
            System.err.println("Comando o mossa non riconosciuta");
            break;
          }
      }
    }
    in.close();
    System.exit(0);
  }
}
