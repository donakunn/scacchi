package it.uniba.main;


import java.util.Scanner;


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
    String turn = "bianchi";
    boolean inGame = false;
    boolean exit = false;
    Scanner in = new Scanner(System.in);
    System.out.println("Benvenuto nel gioco degli scacchi!");
    System.out.println(
        "Inserire comando da eseguire oppure inserire help per la lista dei comandi.");

    while (!exit) {

      if (Game.getBlackTurn() == true) {
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
            PrintMessage.printBoard(menu.board());
            break;
          } else {
            System.err.println("Devi essere in gioco per usare questo comando.");
            break;
          }
        case "captures":
          if (inGame) {
            PrintMessage.printCaptures(menu.Blackcaptured(), menu.Whitecaptures());
            break;
          } else {
            System.err.println("Devi essere in gioco per usare questo comando.");
            break;
          }
        case "help":
          System.out.println(menu.help());
          break;
        case "moves":
          if (inGame) {
            PrintMessage.printMoves(menu.moves());
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
            System.out.println("Partita gia' avviata. Vuoi cancellarla ed iniziare una nuova partita?");
            while (true) {
              String answer = in.nextLine();
              if (answer.toUpperCase().equals("SI") || answer.toUpperCase().equals("YES")) {
                System.out.println("Cancello la partita in corso e ne avvio una nuova");
                menu.play();
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
              String[] move= menu.getMove(input);
              if (move[0] == "0-0") {
                PrintMessage.printShortCastling();
                break;
              }
              else if (move[0] == "0-0-0") {
                PrintMessage.printLongCastling();
                break;
              }

              if (move[1] == null) {
              PrintMessage.printAMove(move);
              } else {
            	  PrintMessage.printACapture(move);
            	  PrintMessage.printAMove(move);
            	  
              }
              
            } catch (StringIndexOutOfBoundsException e) {
              System.err.println("Comando non valido.");
            } catch (IllegalArgumentException e) {
              System.err.println("Mossa non riconosciuta");
            } catch (IndexOutOfBoundsException e) {
              System.err.println(
                  "Mossa illegale; la mossa specificata non rispetta i limiti della scacchiera (a-g) (1-8)");
            } catch (IllegalMoveException e) {
              System.err.println(e.getMessage());
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
