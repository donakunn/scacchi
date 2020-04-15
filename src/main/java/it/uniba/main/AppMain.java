package it.uniba.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import it.uniba.sotorrent.GoogleDocsUtils;

enum Colonna {a,b,c,d,e,f,g,h};

/**
 * The main class for the project. It must be customized to meet the project
 * assignment specifications.
 * 
 * <b>DO NOT RENAME</b>
 */
public final class AppMain {

	/**
	 * Private constructor. Change if needed.
	 */
	private AppMain() {

	}

	/**
	 * 	 * This is the main entry of the application.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(final String[] args) {
		Cella[][] scacchiera= new Cella[8][8];
		System.out.println("Current working dir: " + System.getProperty("user.dir"));

		if (args.length > 0) {
			switch (args[0]) {
			case "it":
				System.out.println("Applicazione avviata.");
				break;

			case "en":
				System.out.println("Application started.");
				break;

			default:
				System.out.println("Specify the language. "
								   + "Languages supported: 'it' or 'en'");
				break;
			}
		} else {
			System.out.println("Using default language 'en'");
			System.out.println("Application started.");
		}

	}
	
	void nuovaPartita() {
		System.out.println("Creazione partita in corso...");
		for(int j=0;j<8;j++) {
			//inizializzazione pedoni a2-h2 (lato bianco)
			//scacchiera[1][j]= new Cella(new Pedone());
			
			//inizializzazione pedoni a7-h7 (lato nero)
			//scacchiera[6][j]= new Cella(new Pedone());
		};
		//inizializzazione case a1-h1 (lato bianco)
		/* scacchiera[0][0]= new Cella(new Torre()); scacchiera[0][1]= new Cella(new
		 * Cavallo()); scacchiera[0][2]= new Cella(new Alfiere()); scacchiera[0][3]= new
		 * Cella(new Donna()); scacchiera[0][4]= new Cella(new Re()); scacchiera[0][5]=
		 * new Cella(new Alfiere()); scacchiera[0][6]= new Cella(new Cavallo());
		 * scacchiera[0][7]= new Cella(new Torre());
		 */
		
		//inizializzazione case a8-h8 (lato nero)
		/*
		 * scacchiera[7][0]= new Cella(new Torre()); scacchiera[7][1]= new Cella(new
		 * Cavallo()); scacchiera[7][2]= new Cella(new Alfiere()); scacchiera[7][3]= new
		 * Cella(new Donna()); scacchiera[7][4]= new Cella(new Re()); scacchiera[7][5]=
		 * new Cella(new Alfiere()); scacchiera[7][6]= new Cella(new Cavallo());
		 * scacchiera[7][7]= new Cella(new Torre());
		 */	
	};
	
	void riceviMossa() {
		Scanner in = new Scanner(System.in);
		String mossa= in.nextLine();
		if (mossa.length()==2){
			//muovi pedone
		}else {
			char pezzoScelto=mossa.charAt(0);
			switch(pezzoScelto) {
			case 'T':
				//muovi Torre
			case 'C':
				//muovi Cavallo
			case 'A':
				//muovi Alfiere
			case 'D':
				//muovi Donna
			case 'R':
				//muovi Re
			}
			mossa.substring(1);
		}
		
		//parsing della colonna in cui muovere
		Colonna colonnaMossa= Colonna.valueOf(mossa.substring(0,1));
		//accesso con scacchiera[mossa.charAt(1)-1][colonnaMossa.ordinal()]
		
		in.close();
	};

}
