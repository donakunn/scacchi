package it.uniba.main;


import java.util.Scanner;


/**
 * {@literal <<boundary>>}<br>
 * <p><I>Titolo</I>: AppMain</p>
 * <p><I>Descrizione</I>: La classe AppMain e' la classe principale del progetto. Contiene tutti i comandi del gioco e permette
 * di effetuare diverse operazioni a seconda del comando.</p>
 *
 */
public final class AppMain {
    /**
     * E' il costruttore della classe.
     */
    private AppMain() {
    }

    /**
     * E' il metodo principale dell'applicazione. Permette di avviare il gioco degli scacchi, crea un nuovo menu e
     * assegna il turno iniziale ai bianchi. 
     *
     * @param args argomenti della riga di comando.
     */
    public static void main(final String[] args) {
        Menu menu = new Menu();
        String turn = "bianchi";
        boolean inGame = false;
        boolean exit = false;
        Scanner in = new Scanner(System.in, "UTF-8");
        PrintMessage.printMessage("Benvenuto nel gioco degli scacchi!");
        PrintMessage.printMessage(
                "Inserire comando da eseguire oppure inserire help per la lista dei comandi.");

        while (!exit) {

            if (menu.getBlackTurn()) {
                turn = "neri";
            } else {
                turn = "bianchi";
            }
            if (inGame) {
            	PrintMessage.printTurn(turn);
            } else {
            	PrintMessage.printMessage("Inserire comando:");
            }
            String input = in.nextLine();
            switch (input) {
                case "board":
                    if (inGame) {
                        PrintMessage.printBoard(menu.board());
                        break;
                    } else {
                    	PrintMessage.printError("Devi essere in gioco per usare questo comando.");
                        break;
                    }
                case "captures":
                    if (inGame) {
                        PrintMessage.printCaptures(menu.blackCaptured(), menu.whiteCaptured());

                        break;
                    } else {
                        PrintMessage.printError("Devi essere in gioco per usare questo comando.");
                        break;
                    }
                case "help":
                	PrintMessage.printMessage(menu.help());
                    break;
                case "moves":
                    if (inGame) {
                        PrintMessage.printMoves(menu.moves());
                        break;
                    } else {
                    	PrintMessage.printError("Devi essere in gioco per usare questo comando.");
                        break;
                    }
                case "play":
                    if (!inGame) {
                    	PrintMessage.printMessage("Creo nuova partita..");
                        inGame = true;
                        menu.play();
                        PrintMessage.printMessage("Partita creata.");
                    } else {
                    	PrintMessage.printMessage("Partita gia' avviata. Vuoi cancellarla ed iniziare una nuova partita?");
                        while (true) {
                            String answer = in.nextLine();
                            if (answer.toUpperCase().equals("SI") || answer.toUpperCase().equals("YES")) {
                            	PrintMessage.printMessage("Cancello la partita in corso e ne avvio una nuova");
                                menu.play();
                                break;
                            } else if (answer.toUpperCase().equals("NO")) {
                                break;
                            } else {
                            	PrintMessage.printMessage("Risposta non valida; inserire si (yes) o no");
                            }
                        }
                    }
                    break;
                case "quit":
                    String answer;
                    PrintMessage.printMessage("Sei sicuro di voler uscire? ");
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
                    	PrintMessage.printMessage("Risposta non valida, inserisci si (yes) o no");
                    }

                default:
                    if (inGame) {
                        try {
                            String[] move = menu.getMove(input);
                            if (move[0] == "0-0") {
                                PrintMessage.printShortCastling();
                                break;
                            } else if (move[0] == "0-0-0") {
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
                        	PrintMessage.printError("Comando non valido.");
                        } catch (IllegalArgumentException e) {
                        	PrintMessage.printError("Mossa non riconosciuta");
                        } catch (IndexOutOfBoundsException e) {
                        	PrintMessage.printError(
                                    "Mossa illegale; la mossa specificata non rispetta "
                                            + "i limiti della scacchiera (a-g) (1-8)");
                        } catch (IllegalMoveException e) {
                        	PrintMessage.printError(e.getMessage());
                        }

                        break;
                    } else {
                    	PrintMessage.printError("Comando o mossa non riconosciuti");
                        break;
                    }
            }
        }
        in.close();
        System.exit(0);
    }
}
