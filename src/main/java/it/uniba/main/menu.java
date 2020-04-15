package it.uniba.main;
import java.util.Scanner;
public class menu {
	public void mostrareComandi() {
		System.out.print("Se la partita non � in corso compariranno questi comandi:\n help \n play \n quit\n");
        System.out.print("Se la partita � in corso compariranno questi comandi:\n help \n quit \n board \n moves \n captures");
	}
 
	public void comandoBoard() {
		Scanner scanner = new Scanner(System.in); // prende il comando scritto in input
        String comando = scanner.next();
        if(comando.equals("board")) // verifica il comando scritto in input e lo confronta con il comando 'board'
        {
        	Partita.stampaScacchiera();
        }
	}
	
	public void comandoMoves(){
		Scanner input = new Scanner(System.in);
		String comando = input.next(); //prende in input il comando
		if(comando.equals("moves")) {
			//inserire la funzione che mostra le mosse giocte durante la partita
		}
		
	}
	
	public void chiudilGioco() {
		try (Scanner oggetto = new Scanner(System.in)) {
			System.out.print("Sei sicuro di uscire?:");
			String risposta= oggetto.nextLine();
			risposta.toUpperCase();
			if(risposta.equals("SI") || risposta.equals("YES")  ) {
				System.exit(0);
			}
		}
	
}
	
}
