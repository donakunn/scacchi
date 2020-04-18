package it.uniba.main;
import java.util.Scanner;
import java.util.ArrayList;
public class Menu {
	private Game game=new Game();
	public void help() {
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
	
	public void board() {
		System.out.println("\ta\tb\tc\td\te\tf\tg\th");
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i + ".\t");
			for (int j = 0; j < 8; j++) {
				Cell c = Game.getCell(i,j);
				System.out.print(c + ".\t");
			}
			System.out.print(8 - i + ".\t");
			System.out.println("\n");
		}
		System.out.println("\ta\tb\tc\td\te\tf\tg\th");
	}
	
	public void moves(){
		ArrayList<String> movesDone = game.getMoves();
		if (game.getMoves().size() == 0) {
			System.out.println("No moves done");
		}
		else {
		System.out.println(movesDone);
		}
	}
	
	public void play(){
		game.newGame();
	}
	
	public void getMove(String input){
		if (input.length()==2) {
			game.move(input);
		}else if(input.length()==4) {
			if(input.substring(0,2)=="Px");
				//game.Capture(player, move);
		}else if(input.length()==8) {
			if((input.substring(0,2)=="Px")&&(input.substring(4,8)=="e.p."));
			//AHAHAHSHSHUQDHGUWEHFUIWEFHI
			//game.CaptureEnPassant(player, move);
		}
	}
	
	public void captures() {
		if (game.getBlacks().size() == 0) {
			System.out.println("No black pieces captured");
		}
		else {
		System.out.println("Captured black pieces: " + game.getBlacks());
		}
		if (game.getWhites().size() == 0) {
			System.out.println("No white pieces captured");
		}
		else {
		System.out.println("Captured white pieces: " + game.getWhites());
		}
	}
	
	public boolean quit() {
		Scanner in = new Scanner(System.in);
		String answer;
		System.out.println("Are you sure you want to quit?:");
		while(true) {
			answer=in.nextLine();
			answer=answer.toUpperCase();
			if(answer.equals("YES")) {
				return true;
			}else if(answer.equals("NO")) {
				return false;
			}else {
				System.out.println("Invalid answer, please type again:");
			}
			in.close();
		}
	}
	
	public boolean getTurn() {
		return game.getWhiteTurn();
	}

	public void resetTurn() {
		game.setWhiteTurn();
	}
}
