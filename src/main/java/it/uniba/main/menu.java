package it.uniba.main;
import java.util.Scanner;
public class menu {
	public void commandHelp() {
        System.out.println("List of executable commands:");
        System.out.println("help");
        System.out.println("play");
        System.out.println("quit");
        System.out.println("\nList of commands that can only be executed in the game:");
        System.out.println("board");
        System.out.println("captures");
        System.out.println("moves");
        System.out.println("To perform a move it is sufficient to specify it in algebraic notation.");
    }
 
	public void commandBoard() {
		Scanner scanner = new Scanner(System.in); // prende il comando scritto in input
        String comando = scanner.next();
        if(comando.equals("board")) // verifica il comando scritto in input e lo confronta con il comando 'board'
        {
        	Game.printBoard();
        }
	}
	
	public void commandMoves(){
		Scanner input = new Scanner(System.in);
		String comando = input.next(); //prende in input il comando
		if(comando.equals("moves")) {
			//inserire la funzione che mostra le mosse giocte durante la partita
		}
		
	}
	
	public void closeGame() {
		try (Scanner oggetto = new Scanner(System.in)) {
			System.out.print("Are you sure you want to quit?:");
			String risposta= oggetto.nextLine();
			risposta.toUpperCase();
			if(risposta.equals("SI") || risposta.equals("YES")  ) {
				System.exit(0);
			}
		}
	
}
	
}
