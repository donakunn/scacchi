package Menu;
import java.util.Scanner;
public class menu {
	public void mostrareelencocomandi() {
		System.out.print("Se la partita non � in corso compariranno questi comandi:\n help \n play \n quit\n");
        System.out.print("Se la partita � in corso compariranno questi comandi:\n help \n quit \n board \n moves \n captures");
	}
 
	public void comandoBoard() {
		Scanner scanner = new Scanner(System.in); // prende il comando scritto in input
        String comando = scanner.next();
        if(comando.equals("board")) // verifica il comando scritto in input e lo confronta con il comando 'board'
        {
        	//da inserire la funzione di stampa della matrice
        }
	}
	
	public void comandoMoves(){
		Scanner input = new Scanner(System.in);
		String comando = input.next(); //prende in input il comando
		if(comando.equals("moves")) {
			//inserire la funzione che mostra le mosse giocte durante la partita
		}
		
	}
	
	
	
}
