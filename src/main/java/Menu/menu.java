package Menu;
import java.util.Scanner;
public class menu {
	public void mostrareelencocomandi() {
		System.out.print("Se la partita non � in corso compariranno questi comandi:\n help \n play \n quit\n");
        System.out.print("Se la partita � in corso compariranno questi comandi:\n help \n quit \n board \n moves \n captures");
	}
 
	public void comandoShow() {
		Scanner scanner = new Scanner(System.in); // prende il comando scritto in input
        String comando = scanner.next();
        if(comando.equals("show")) // verifica il comando scritto in input e lo confronta con il comando 'show'
        {
        	//da inserire la funzione di stampa della matrice
        }
	}
	
	
	
}
