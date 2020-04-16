package it.uniba.main;

public class Game {
            private static Cell board[][] = new Cell[8][8];

            public static void printBoard() {
                System.out.println("\ta\tb\tc\td\te\tf\tg\th");
                for (int i = 0; i < 8; i++) {
                    System.out.print(8 - i + ".\t");
                for (int j = 0; j < 8; j++) {
                    System.out.print(board[i][j]);
                }
                }
                System.out.println("\ta\tb\tc\td\te\tf\tg\th");

            }
        }