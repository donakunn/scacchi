package it.uniba.main;

import java.util.ArrayList;

class Game {
	private static boolean whiteTurn = true;
	private static Cell board[][] = new Cell[8][8];
	
	private enum Colonna {
		a, b, c, d, e, f, g, h
	};

	private ArrayList<String> movesDone = new ArrayList<String>();
	private ArrayList<Piece> BlacksCaptured = new ArrayList<Piece>();
	private ArrayList<Piece> WhitesCaptured = new ArrayList<Piece>();

	ArrayList<Piece> getBlacks() {
		return BlacksCaptured;
	}

	ArrayList<Piece> getWhites() {
		return WhitesCaptured;
	}

	void newGame() {
		movesDone.clear();
		BlacksCaptured.clear();
		WhitesCaptured.clear();
		System.out.println("Creating game...");
		for (int j = 0; j < 8; j++) {
			// initialize pawns a2-h2 (white side)
			board[1][j] = new Cell(new Pawn(1));

			// initialize pawns a7-h7 (black side)
			board[6][j] = new Cell(new Pawn(0));
		}
		;
		// initialize pieces a1-h1 (white side)
		board[0][0] = new Cell(new Rook(1));
		board[0][1] = new Cell(new Knight(1));
		board[0][2] = new Cell(new Bishop(1));
		board[0][3] = new Cell(new Queen(1));
		board[0][4] = new Cell(new King(1));
		board[0][5] = new Cell(new Bishop(1));
		board[0][6] = new Cell(new Knight(1));
		board[0][7] = new Cell(new Rook(1));

		// initialize empty cells
		for (int i = 2; i <= 5; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Cell(null);
			}
		}

		// initialize pieces a8-h8 (black side)
		board[7][0] = new Cell(new Rook(0));
		board[7][1] = new Cell(new Knight(0));
		board[7][2] = new Cell(new Bishop(0));
		board[7][3] = new Cell(new Queen(0));
		board[7][4] = new Cell(new King(0));
		board[7][5] = new Cell(new Bishop(0));
		board[7][6] = new Cell(new Knight(0));
		board[7][7] = new Cell(new Rook(0));
		System.out.println("Game created...");
	};

	void moveAPawn(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata

		
			y = Colonna.valueOf(move.substring(0, 1)).ordinal();
			x = 8 - Integer.parseInt(move.substring(1, 2));

			Pawn p;

			if ((board[x - 1][y].getPiece() instanceof Pawn) && (board[x - 1][y].getPiece().getColor() == 1)
					&& whiteTurn == false) { // neri
				p = (Pawn) board[x - 1][y].getPiece();

				if (board[x][y].getPiece() == null) {
					board[x - 1][y].setEmpty();
					board[x][y].setPiece(p);
					movesDone.add(move);
					p.incrementMoves();
					whiteTurn = true;
					System.out.println(p.getType() + " Moved on " + move);
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");
			} else if ((board[x - 2][y].getPiece() instanceof Pawn) && (board[x - 2][y].getPiece().getColor() == 1)
					&& whiteTurn == false) {
				p = (Pawn) board[x - 2][y].getPiece();

				if (board[x][y].getPiece() == null) {
					board[x - 2][y].setEmpty();
					board[x][y].setPiece(p);
					movesDone.add(move);
					p.incrementMoves();
					whiteTurn = true;
					System.out.println(p.getType() + " Moved on " + move);
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");
			} else if ((board[x + 1][y].getPiece() instanceof Pawn) && (board[x + 1][y].getPiece().getColor() == 0)
					&& whiteTurn == true) { // bianchi
				p = (Pawn) board[x + 1][y].getPiece();

				if (board[x][y].getPiece() == null) {
					board[x + 1][y].setEmpty();
					board[x][y].setPiece(p);
					movesDone.add(move);
					p.incrementMoves();
					whiteTurn = false;
					System.out.println(p.getType() + " Moved on " + move);
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");
			} else if ((board[x + 2][y].getPiece() instanceof Pawn) && (board[x + 2][y].getPiece().getColor() == 0)
					&& whiteTurn == true) {
				p = (Pawn) board[x + 2][y].getPiece();

				if (board[x][y].getPiece() == null) {
					board[x + 2][y].setEmpty();
					board[x][y].setPiece(p);
					movesDone.add(move);
					p.incrementMoves();
					whiteTurn = false;
					System.out.println(p.getType() + " Moved on " + move);
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");
			} else {
				throw new IllegalMoveException("Illegal move. Please try again.");
			}
		
	}

	void pawnCapture(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata
		int z; // colonna del pezzo di provenienza

		Piece p, caught;

		y = Colonna.valueOf(move.substring(2, 3)).ordinal();
		x = 8 - Integer.parseInt(move.substring(3, 4));
		z = Colonna.valueOf(move.substring(0, 1)).ordinal();

		if (board[x][y].getPiece() == null) {
			try {
				this.captureEnPassant(move);
			} catch (IllegalMoveException e) {
				System.err.println(e.getMessage());
			}
		} else {

			if (whiteTurn == false) { // neri
				if (z == y - 1) {
					if (board[x][y] != null) {
						if (board[x - 1][y - 1].getPiece() instanceof Pawn) { // cattura in diagonale da sinistra
							p = (Pawn) board[x - 1][y - 1].getPiece();

							if ((board[x][y].getPiece() instanceof Pawn)
									&& (board[x][y].getPiece().getColor() != p.getColor())) {
								caught = board[x][y].getPiece();
								board[x][y].setPiece(p);
								board[x - 1][y - 1].setEmpty();
								movesDone.add(move);
								this.WhitesCaptured.add(caught);
								whiteTurn = true;
								System.out.println(
										p.getType() + " captured " + caught.getType() + " on " + move.substring(2, 4));
							} else
								throw new IllegalMoveException("Illegal move. Please try again.");
						} else
							throw new IllegalMoveException("Illegal move. Please try again.");
					} else
						throw new IllegalMoveException("Illegal move. Please try again.");

				} else if (z == y + 1) {
					if (board[x][y] != null) {
						if (board[x - 1][y + 1].getPiece() instanceof Pawn) { // cattura in diagonale da destra
							p = (Pawn) board[x - 1][y + 1].getPiece();

							if ((board[x][y].getPiece() instanceof Pawn)
									&& (board[x][y].getPiece().getColor() != p.getColor())) {
								caught = board[x][y].getPiece();
								board[x][y].setPiece(p);
								board[x - 1][y + 1].setEmpty();
								movesDone.add(move);
								this.WhitesCaptured.add(caught);
								whiteTurn = true;
								System.out.println(
										p.getType() + " captured " + caught.getType() + " on " + move.substring(2, 4));
							} else
								throw new IllegalMoveException("Illegal move. Please try again.");
						} else
							throw new IllegalMoveException("Illegal move. Please try again.");
					} else
						throw new IllegalMoveException("Illegal move. Please try again.");
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");

			} else { // bianchi

				if (z == y - 1) {
					if (board[x][y] != null) {
						if (board[x + 1][y - 1].getPiece() instanceof Pawn) { // cattura in diagonale da sinistra
							p = (Pawn) board[x + 1][y - 1].getPiece();

							if ((board[x][y].getPiece() instanceof Pawn)
									&& (board[x][y].getPiece().getColor() != p.getColor())) {
								caught = board[x][y].getPiece();
								board[x][y].setPiece(p);
								board[x + 1][y - 1].setEmpty();
								movesDone.add(move);
								this.BlacksCaptured.add(caught);
								whiteTurn = false;
								System.out.println(
										p.getType() + " captured " + caught.getType() + " on " + move.substring(2, 4));
							} else
								throw new IllegalMoveException("Illegal move. Please try again.");
						} else
							throw new IllegalMoveException("Illegal move. Please try again.");
					} else
						throw new IllegalMoveException("Illegal move. Please try again.");

				} else if (z == y + 1) {
					if (board[x][y] != null) {
						if (board[x + 1][y + 1].getPiece() instanceof Pawn) { // cattura in diagonale da destra
							p = (Pawn) board[x + 1][y + 1].getPiece();

							if ((board[x][y].getPiece() instanceof Pawn)
									&& (board[x][y].getPiece().getColor() != p.getColor())) {
								caught = board[x][y].getPiece();
								board[x][y].setPiece(p);
								board[x + 1][y + 1].setEmpty();
								movesDone.add(move);
								this.BlacksCaptured.add(caught);
								whiteTurn = false;
								System.out.println(
										p.getType() + " captured " + caught.getType() + " on " + move.substring(2, 4));
							} else
								throw new IllegalMoveException("Illegal move. Please try again.");
						} else
							throw new IllegalMoveException("Illegal move. Please try again.");
					} else
						throw new IllegalMoveException("Illegal move. Please try again.");

				} else
					throw new IllegalMoveException("Illegal move. Please try again.");
			}
		}
	}

	void captureEnPassant(String move) throws IllegalMoveException {

		int x; // ascissa
		int y; // ordinata
		int z; // colonna del pezzo di provenienza

		Piece p;

		y = Colonna.valueOf(move.substring(2, 3)).ordinal();
		x = 8 - Integer.parseInt(move.substring(3, 4));
		z = Colonna.valueOf(move.substring(0, 1)).ordinal();
		if (whiteTurn == false) { // neri
			if (z == y - 1) {
				if (board[x - 1][y - 1].getPiece() instanceof Pawn) { // cattura en Passant in diagonale da sinistra
					if (board[x - 1][y].getPiece() instanceof Pawn) {
						p = (Pawn) board[x - 1][y - 1].getPiece();
						Pawn caught = (Pawn) board[x - 1][y].getPiece();
						if ((board[x][y].getPiece() == null) && (caught.enPassantCatturable(x - 1))) {
							board[x][y].setPiece(p);
							board[x - 1][y - 1].setEmpty();
							board[x - 1][y].setEmpty();
							movesDone.add(move);
							this.WhitesCaptured.add(caught);
							whiteTurn = true;
							System.out.println(p.getType() + " captured " + caught.getType() + " on "
									+ move.substring(2, 4) + " e.p.");
						} else
							throw new IllegalMoveException("Illegal move. Please try again.");
					} else
						throw new IllegalMoveException("Illegal move. Please try again.");
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");
			}

			else if (z == y + 1) {

				if (board[x - 1][y + 1].getPiece() instanceof Pawn) { // cattura en Passant in diagonale da destra
					if (board[x - 1][y].getPiece() instanceof Pawn) {
						p = (Pawn) board[x - 1][y + 1].getPiece();
						Pawn caught = (Pawn) board[x - 1][y].getPiece();

						if ((board[x][y].getPiece() == null) && (caught.enPassantCatturable(x - 1))) {
							board[x][y].setPiece(p);
							board[x - 1][y + 1].setEmpty();
							board[x - 1][y].setEmpty();
							movesDone.add(move);
							this.WhitesCaptured.add(caught);
							whiteTurn = true;
							System.out.println(p.getType() + " captured " + caught.getType() + " on "
									+ move.substring(2, 4) + " e.p.");
						} else
							throw new IllegalMoveException("Illegal move. Please try again.");
					} else
						throw new IllegalMoveException("Illegal move. Please try again.");
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");

			} else
				throw new IllegalMoveException("Illegal move. Please try again.");

		} else { // bianchi

			if (z == y - 1) {

				if (board[x + 1][y - 1].getPiece() instanceof Pawn) { // cattura en Passant in diagonale da sinistra
					if (board[x + 1][y].getPiece() instanceof Pawn) {
						p = (Pawn) board[x + 1][y - 1].getPiece();
						Pawn caught = (Pawn) board[x + 1][y].getPiece();

						if ((board[x][y].getPiece() == null) && (caught.enPassantCatturable(x + 1))) {
							board[x][y].setPiece(p);
							board[x + 1][y - 1].setEmpty();
							board[x + 1][y].setEmpty();
							movesDone.add(move);
							this.BlacksCaptured.add(caught);
							whiteTurn = false;
							System.out.println(p.getType() + " captured " + caught.getType() + " on "
									+ move.substring(2, 4) + " e.p.");
						} else
							throw new IllegalMoveException("Illegal move. Please try again.");
					} else
						throw new IllegalMoveException("Illegal move. Please try again.");
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");
			}

			else if (z == y + 1) {

				if (board[x + 1][y + 1].getPiece() instanceof Pawn) { // cattura en Passant in diagonale da destra
					if (board[x + 1][y].getPiece() instanceof Pawn) {
						p = (Pawn) board[x + 1][y + 1].getPiece();
						Pawn caught = (Pawn) board[x + 1][y].getPiece();

						if ((board[x][y].getPiece() == null) && (caught.enPassantCatturable(x + 1))) {
							board[x][y].setPiece(p);
							board[x + 1][y + 1].setEmpty();
							board[x + 1][y].setEmpty();
							movesDone.add(move);
							this.BlacksCaptured.add(caught);
							whiteTurn = false;
							System.out.println(p.getType() + " captured " + caught.getType() + " on "
									+ move.substring(2, 4) + " e.p.");
						} else
							throw new IllegalMoveException("Illegal move. Please try again.");
					} else
						throw new IllegalMoveException("Illegal move. Please try again.");
				} else
					throw new IllegalMoveException("Illegal move. Please try again.");

			} else
				throw new IllegalMoveException("Illegal move. Please try again.");
		}
	}
	
	void moveKing(String move) throws IllegalMoveException{
		int x=2;
		int y=1;
		
		if(move.length()==4) {
			x=3;
			y=2;
		}
		
		y = Colonna.valueOf(move.substring(y, y+1)).ordinal();
		x = 8 - Integer.parseInt(move.substring(x, x+1));
		
		if(board[x][y].getPiece() != null && board[x][y].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
			throw new IllegalMoveException("There is another allied piece in that position, please try again");
		}
		int xK=-1;
		int yK=-1;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(board[i][j].getPiece() instanceof King &&  board[i][j].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
					xK=i;
					yK=j;
					break;
				}
			}
		}
		if(Math.abs(x-xK)>1||Math.abs(y-yK)>1) {
			throw new IllegalMoveException("Your King cannot move there");
		}
		if(isThreatened(x,y)) {
			throw new IllegalMoveException("Invalid move, it would put your King in check");
		}
		
		if(board[x][y].getPiece() == null) {
			if(move.charAt(1)=='x') {
				throw new IllegalMoveException("Invalid move, there is no piece you can capture in that position.");
			}
		}else {
			if(move.charAt(1)!='x') {
				throw new IllegalMoveException("Invalid move, you must specify the capture as denoted in algebraic notation.");
			}
			if (whiteTurn) {
				BlacksCaptured.add(board[x][y].getPiece());
			}else {
				WhitesCaptured.add(board[x][y].getPiece());
			}
			System.out.println(board[xK][yK].getPiece().getType()+" captured "+ board[x][y].getPiece().getType() +"!");
		}
		board[x][y].setPiece(board[xK][yK].getPiece());
		board[xK][yK].setEmpty();
		movesDone.add(move);
		whiteTurn=!whiteTurn;
		System.out.println(board[x][y].getPiece().getType() + " Moved to " + (char) (y+97) + (8-x) );		
	}
	
	private boolean isThreatened(int x, int y) {
		int i,j;
		if(!whiteTurn) {
			if(x<7) {
				if(y>0 && board[x+1][y-1].getPiece() instanceof Pawn && board[x+1][y-1].getPiece().getColor() == 0) {
					return true;
				}
				if(y<7 && board[x+1][y+1].getPiece() instanceof Pawn && board[x+1][y+1].getPiece().getColor() == 0) {
					return true;
				}
			}
		}
		if(whiteTurn) {
			if(x>0) {
				if(y>0 && board[x-1][y-1].getPiece() instanceof Pawn && board[x-1][y-1].getPiece().getColor() == 1) {
					return true;
				}
				if(y<7 && board[x-1][y+1].getPiece() instanceof Pawn && board[x-1][y+1].getPiece().getColor() == 1) {
					return true;
				}
			}
		}
		//up right
		i=1;
		j=1;
		while(x-i>=0 && y+j<=7) {
			if(board[x-i][y+j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if(board[x-i][y+j].getPiece().getColor() != (whiteTurn ? 1 : 0)) break;
			if(board[x-i][y+j].getPiece() instanceof Bishop || board[x-i][y+j].getPiece() instanceof Queen) 
				return true;
			i++;
			j++;		
		}
		//down right
		i=1;
		j=1;
		while(x+i<=7 && y+j<=7) {
			if(board[x+i][y+j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if(board[x+i][y+j].getPiece().getColor() != (whiteTurn ? 1 : 0))
				break;
			if(board[x+i][y+j].getPiece() instanceof Bishop || board[x+i][y+j].getPiece() instanceof Queen) 
				return true;
			i++;
			j++;
		}
		//down left
		i=1;
		j=1;
		while(x+i<=7 && y-j>=0) {
			if(board[x+i][y-j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if(board[x+i][y-j].getPiece().getColor() != (whiteTurn ? 1 : 0)) break;
			if(board[x+i][y-j].getPiece() instanceof Bishop || board[x+i][y-j].getPiece() instanceof Queen) 
				return true;
			i++;
			j++;		
		}
		//up left
		i=1;
		j=1;
		while(x-i>=0 && y-j>=0) {
			if(board[x-i][y-j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if(board[x-i][y-j].getPiece().getColor() != (whiteTurn ? 1 : 0)) break;
			if(board[x-i][y-j].getPiece() instanceof Bishop || board[x-i][y-j].getPiece() instanceof Queen) 
				return true;
			i++;
			j++;		
		}
		
		
		//right
		j=1;
		while(y+j<=7) {
			if(board[x][y+j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if(board[x][y+j].getPiece().getColor() != (whiteTurn ? 1 : 0)) break;
			if(board[x][y+j].getPiece() instanceof Rook || board[x][y+j].getPiece() instanceof Queen) 
				return true;
			j++;		
		}
		//down
		i=1;
		while(x+i<=7) {
			if(board[x+i][y].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if(board[x+i][y].getPiece().getColor() != (whiteTurn ? 1 : 0)) break;
			if(board[x+i][y].getPiece() instanceof Rook || board[x+i][y].getPiece() instanceof Queen) 
				return true;
			i++;		
		}
		//left
		j=1;
		while(y-j>=0) {
			if(board[x][y-j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if(board[x][y-j].getPiece().getColor() != (whiteTurn ? 1 : 0)) break;
			if(board[x][y-j].getPiece() instanceof Rook || board[x][y-j].getPiece() instanceof Queen) 
				return true;
			j++;		
		}
		//up
		i=1;
		while(x-i>=0) {
			if(board[x-i][y].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if(board[x-i][y].getPiece().getColor() != (whiteTurn ? 1 : 0)) break;
			if(board[x-i][y].getPiece() instanceof Rook || board[x-i][y].getPiece() instanceof Queen) 
				return true;
			i++;		
		}
		//knight
		if(y+2<=7) {
			if(x-1>=0 && board[x-1][y+2].getPiece() instanceof Knight && board[x-1][y+2].getPiece().getColor() == (whiteTurn ? 1 : 0)) 
				return true;
			if(x+1<=7 && board[x+1][y+2].getPiece() instanceof Knight && board[x+1][y+2].getPiece().getColor() == (whiteTurn ? 1 : 0)) 
				return true;
		}
		if(y+1<=7) {
			if(x-2>=0 && board[x-2][y+1].getPiece() instanceof Knight && board[x-2][y+1].getPiece().getColor() == (whiteTurn ? 1 : 0)) 
				return true;
			if(x+2<=7 && board[x+2][y+1].getPiece() instanceof Knight && board[x+2][y+1].getPiece().getColor() == (whiteTurn ? 1 : 0)) 
				return true;
		}
		if(y-1>=0) {
			if(x-2>=0 && board[x-2][y-1].getPiece() instanceof Knight && board[x-2][y-1].getPiece().getColor() == (whiteTurn ? 1 : 0)) 
				return true;
			if(x+2<=7 && board[x+2][y-1].getPiece() instanceof Knight && board[x+2][y-1].getPiece().getColor() == (whiteTurn ? 1 : 0)) 
				return true;
		}
		if(y-2>=0) {
			if(x-1>=0 && board[x-1][y-2].getPiece() instanceof Knight && board[x-1][y-2].getPiece().getColor() == (whiteTurn ? 1 : 0)) 
				return true;
			if(x+1<=7 && board[x+1][y-2].getPiece() instanceof Knight && board[x+1][y-2].getPiece().getColor() == (whiteTurn ? 1 : 0)) 
				return true;
		}
		return false;
	}

	static boolean getTurn() {
		return whiteTurn;
	}

	void setWhiteTurn() {
		whiteTurn = true;
	}

	static Cell getCell(int x, int y) {
		return board[x][y];
	}

	ArrayList<String> getMoves() {
		return movesDone;
	}

}