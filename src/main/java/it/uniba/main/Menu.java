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
		System.out.println(movesDone);
	}
	
	public void play(){
		game.newGame();
	}
	
	public void getMove(String input){
		game.move(input);
	}
	
	public void captures() {
		ArrayList<Piece> BlacksCaptured = game.getBlacks();
		ArrayList<Piece> WhitesCaptured = game.getWhites();
		System.out.println(BlacksCaptured);
		System.out.println(WhitesCaptured);
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
}
