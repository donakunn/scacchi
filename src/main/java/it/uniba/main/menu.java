package it.uniba.main;
import java.util.Scanner;
public class menu {
	public void showCommands() {
		System.out.print("If not in-game this commands will appear:\n help \n play \n quit\n");
        System.out.print("If in-game this commands will appear:\n help \n quit \n board \n moves \n captures");
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
