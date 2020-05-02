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
								System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
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
								System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
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
								System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
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
								System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
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
							System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
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
							System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
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
							System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
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
							System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
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

	void moveKing(String move) throws IllegalMoveException {
		int x = 2;
		int y = 1;

		if (move.length() == 4) {
			x = 3;
			y = 2;
		}

		y = Colonna.valueOf(move.substring(y, y + 1)).ordinal();
		x = 8 - Integer.parseInt(move.substring(x, x + 1));

		if (board[x][y].getPiece() != null && board[x][y].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
			throw new IllegalMoveException("There is another allied piece in that position, please try again");
		}
		int xK = -1;
		int yK = -1;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getPiece() instanceof King
						&& board[i][j].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
					xK = i;
					yK = j;
					break;
				}
			}
		}
		if (Math.abs(x - xK) > 1 || Math.abs(y - yK) > 1) {
			throw new IllegalMoveException("Your King cannot move there");
		}
		if (King.isThreatened(board, whiteTurn, x, y)) {
			throw new IllegalMoveException("Invalid move, it would put your King in check");
		}

		if (board[x][y].getPiece() == null) {
			if (move.charAt(1) == 'x') {
				throw new IllegalMoveException("Invalid move, there is no piece you can capture in that position.");
			}
		} else {
			if (move.charAt(1) != 'x') {
				throw new IllegalMoveException(
						"Invalid move, you must specify the capture as denoted in algebraic notation.");
			}
			if (whiteTurn) {
				BlacksCaptured.add(board[x][y].getPiece());
			} else {
				WhitesCaptured.add(board[x][y].getPiece());
			}
			System.out.println(
					board[xK][yK].getPiece().getType() + " captured " + board[x][y].getPiece().getType() + "!");
		}
		board[x][y].setPiece(board[xK][yK].getPiece());
		((King) board[x][y].getPiece()).incrementMoves(); //da controllare
		board[xK][yK].setEmpty();
		movesDone.add(move);
		whiteTurn = !whiteTurn;
		System.out.println(board[x][y].getPiece().getType() + " Moved to " + (char) (y + 97) + (8 - x));
	}

	void moveQueen(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata
		int vCheck; // sentinella dell'ascissa
		int hCheck; // sentinella dell'ordinata
		Queen q;

		y = Colonna.valueOf(move.substring(1, 2)).ordinal();
		x = 8 - Integer.parseInt(move.substring(2, 3));
		if (whiteTurn == true) {
			if (board[x][y].getPiece() == null) {
				vCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (vCheck < 8) {
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][y].getPiece();
						board[vCheck][y].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;

					} else if (board[vCheck][y].getPiece() != null) {
						break;
					} else {
						vCheck++;
					}
				}
				vCheck = x - 1;
				while (vCheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][y].getPiece();
						board[vCheck][y].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][y].getPiece() != null) {
						break;
					} else {
						vCheck--;
					}
				}
				hCheck = y + 1; // controllo in orizzontale a destra
				while (hCheck < 8) {
					if ((board[x][hCheck].getPiece() instanceof Queen)
							&& (board[x][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[x][hCheck].getPiece();
						board[x][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[x][hCheck].getPiece() != null) {
						break;
					} else {
						hCheck++;
					}
				}
				hCheck = y - 1;
				while (hCheck >= 0) { // controllo in orizzontale a sinistra
					if ((board[x][hCheck].getPiece() instanceof Queen)
							&& (board[x][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[x][hCheck].getPiece();
						board[x][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[x][hCheck].getPiece() != null) {
						break;
					} else {
						hCheck--;
					}
				}
				vCheck = x - 1;
				hCheck = y - 1;
				while (vCheck >= 0 && hCheck >= 0) { // controllo diagonale alta sinistra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck--;
						hCheck--;
					}
				}
				vCheck = x - 1;
				hCheck = y + 1;
				while (vCheck >= 0 && hCheck < 8) { // controllo diagonale alta destra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck--;
						hCheck++;
					}
				}
				vCheck = x + 1;
				hCheck = y - 1;
				while (vCheck < 8 && hCheck >= 0) { // controllo diagonale bassa sinistra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck++;
						hCheck--;
					}
				}

				vCheck = x + 1;
				hCheck = y + 1;
				while (vCheck < 8 && hCheck < 8) { // controllo diagonale bassa destra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck++;
						hCheck++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, la donna non può muoversi qui");

			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non è vuota");

		} else { // neri
			if (board[x][y].getPiece() == null) {
				vCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (vCheck < 8) {
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][y].getPiece();
						board[vCheck][y].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;

					} else if (board[vCheck][y].getPiece() != null) {
						break;
					} else {
						vCheck++;
					}
				}
				vCheck = x - 1;
				while (vCheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][y].getPiece();
						board[vCheck][y].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][y].getPiece() != null) {
						break;
					} else {
						vCheck--;
					}
				}
				hCheck = y + 1; // controllo in orizzontale a destra
				while (hCheck < 8) {
					if ((board[x][hCheck].getPiece() instanceof Queen)
							&& (board[x][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[x][hCheck].getPiece();
						board[x][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[x][hCheck].getPiece() != null) {
						break;
					} else {
						hCheck++;
					}
				}
				hCheck = y - 1;
				while (hCheck >= 0) { // controllo in orizzontale a sinistra
					if ((board[x][hCheck].getPiece() instanceof Queen)
							&& (board[x][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[x][hCheck].getPiece();
						board[x][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[x][hCheck].getPiece() != null) {
						break;
					} else {
						hCheck--;
					}
				}
				vCheck = x - 1;
				hCheck = y - 1;
				while (vCheck >= 0 && hCheck >= 0) { // controllo diagonale alta sinistra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck--;
						hCheck--;
					}
				}
				vCheck = x - 1;
				hCheck = y + 1;
				while (vCheck >= 0 && hCheck < 8) { // controllo diagonale alta destra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck--;
						hCheck++;
					}
				}
				vCheck = x + 1;
				hCheck = y - 1;
				while (vCheck < 8 && hCheck >= 0) { // controllo diagonale bassa sinistra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck++;
						hCheck--;
					}
				}

				vCheck = x + 1;
				hCheck = y + 1;
				while (vCheck < 8 && hCheck < 8) { // controllo diagonale bassa destra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(1, 3));
						return;

					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck++;
						hCheck++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, la donna non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non è vuota");

		}
	}

	void captureQueen(String move) throws IllegalMoveException {

		int x; // ascissa
		int y; // ordinata
		int vCheck; // sentinella dell'ascissa
		int hCheck; // sentinella dell'ordinata
		Queen q;

		y = Colonna.valueOf(move.substring(2, 3)).ordinal();
		x = 8 - Integer.parseInt(move.substring(3, 4));
		if (whiteTurn == true) {
			if (board[x][y].getPiece() != null) {
				vCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (vCheck < 8) {
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[vCheck][y].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;

						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;

					} else if (board[vCheck][y].getPiece() != null) {
						break;
					} else {
						vCheck++;
					}
				}
				vCheck = x - 1;
				while (vCheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[vCheck][y].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][y].getPiece() != null) {
						break;
					} else {
						vCheck--;
					}
				}
				hCheck = y + 1; // controllo in orizzontale a destra
				while (hCheck < 8) {
					if ((board[x][hCheck].getPiece() instanceof Queen)
							&& (board[x][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[x][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[x][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[x][hCheck].getPiece() != null) {
						break;
					} else {
						hCheck++;
					}
				}
				hCheck = y - 1;
				while (hCheck >= 0) { // controllo in orizzontale a sinistra
					if ((board[x][hCheck].getPiece() instanceof Queen)
							&& (board[x][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[x][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[x][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[x][hCheck].getPiece() != null) {
						break;
					} else {
						hCheck--;
					}
				}
				vCheck = x - 1;
				hCheck = y - 1;
				while (vCheck >= 0 && hCheck >= 0) { // controllo diagonale alta sinistra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck--;
						hCheck--;
					}
				}
				vCheck = x - 1;
				hCheck = y + 1;
				while (vCheck >= 0 && hCheck < 8) { // controllo diagonale alta destra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck--;
						hCheck++;
					}
				}
				vCheck = x + 1;
				hCheck = y - 1;
				while (vCheck < 8 && hCheck >= 0) { // controllo diagonale bassa sinistra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck++;
						hCheck--;
					}
				}

				vCheck = x + 1;
				hCheck = y + 1;
				while (vCheck < 8 && hCheck < 8) { // controllo diagonale bassa destra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck++;
						hCheck++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, la donna non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione è vuota");

		} else { // neri
			if (board[x][y].getPiece() != null) {
				vCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (vCheck < 8) {
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[vCheck][y].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;

					} else if (board[vCheck][y].getPiece() != null) {
						break;
					} else {
						vCheck++;
					}
				}
				vCheck = x - 1;
				while (vCheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[vCheck][y].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][y].getPiece() != null) {
						break;
					} else {
						vCheck--;
					}
				}
				hCheck = y + 1; // controllo in orizzontale a destra
				while (hCheck < 8) {
					if ((board[x][hCheck].getPiece() instanceof Queen)
							&& (board[x][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[x][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[x][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[x][hCheck].getPiece() != null) {
						break;
					} else {
						hCheck++;
					}
				}
				hCheck = y - 1;
				while (hCheck >= 0) { // controllo in orizzontale a sinistra
					if ((board[x][hCheck].getPiece() instanceof Queen)
							&& (board[x][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[x][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[x][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[x][hCheck].getPiece() != null) {
						break;
					} else {
						hCheck--;
					}
				}
				vCheck = x - 1;
				hCheck = y - 1;
				while (vCheck >= 0 && hCheck >= 0) { // controllo diagonale alta sinistra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck--;
						hCheck--;
					}
				}
				vCheck = x - 1;
				hCheck = y + 1;
				while (vCheck >= 0 && hCheck < 8) { // controllo diagonale alta destra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck--;
						hCheck++;
					}
				}
				vCheck = x + 1;
				hCheck = y - 1;
				while (vCheck < 8 && hCheck >= 0) { // controllo diagonale bassa sinistra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck++;
						hCheck--;
					}
				}

				vCheck = x + 1;
				hCheck = y + 1;
				while (vCheck < 8 && hCheck < 8) { // controllo diagonale bassa destra
					if ((board[vCheck][hCheck].getPiece() instanceof Queen)
							&& (board[vCheck][hCheck].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][hCheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + q.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[vCheck][hCheck].setEmpty();
						board[x][y].setPiece(q);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(q.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[vCheck][hCheck].getPiece() != null) {
						break;
					} else {
						vCheck++;
						hCheck++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, la donna non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione è vuota");

		}

	}

	void moveBishop(String move) throws IllegalMoveException {
		int x;
		int y;
		int xB;
		int yB;
		Bishop b;

		y = Colonna.valueOf(move.substring(1, 2)).ordinal();
		x = 8 - Integer.parseInt(move.substring(2, 3));

		if (whiteTurn == true) { // tutti i controlli per i pezzi bianchi
			if (board[x][y].getPiece() == null) {
				xB = x - 1;
				yB = y - 1;
				while (xB >= 0 && yB >= 0) {
					if (board[xB][yB].getPiece() instanceof Bishop && board[xB][yB].getPiece().getColor() == 0) {
						b = (Bishop) board[xB][yB].getPiece();
						board[xB][yB].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(b.getType() + "spostato su" + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB--;
						yB--;
					}

				}
				xB = x - 1;
				yB = y + 1;
				while (xB >= 0 && yB < 8) {
					if (board[xB][yB].getPiece() instanceof Bishop && board[xB][yB].getPiece().getColor() == 0) {
						b = (Bishop) board[xB][yB].getPiece();
						board[xB][yB].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(b.getType() + "spostato su" + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB--;
						yB++;

					}
				}
				xB = x + 1;
				yB = y - 1;
				while (xB < 8 && yB >= 0) {
					if (board[xB][yB].getPiece() instanceof Bishop && board[xB][yB].getPiece().getColor() == 0) {
						b = (Bishop) board[xB][yB].getPiece();
						board[xB][yB].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(b.getType() + "spostato su" + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB++;
						yB--;
					}
				}
				xB = x + 1;
				yB = y + 1;
				while (xB < 8 && yB < 8) {
					if (board[xB][yB].getPiece() instanceof Bishop && board[xB][yB].getPiece().getColor() == 0) {
						b = (Bishop) board[xB][yB].getPiece();
						board[xB][yB].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(b.getType() + "spostato su" + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB++;
						yB++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, l'alfiere non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non è vuota");

		} else { // else per il caso di turno dei pezzi neri
			if (board[x][y].getPiece() == null) {
				xB = x - 1;
				yB = y - 1;
				while (xB >= 0 && yB >= 0) {
					if (board[xB][yB].getPiece() instanceof Bishop && board[xB][yB].getPiece().getColor() == 1) {
						b = (Bishop) board[xB][yB].getPiece();
						board[xB][yB].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(b.getType() + "spostato su" + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB--;
						yB--;
					}

				}
				xB = x - 1;
				yB = y + 1;
				while (xB >= 0 && yB < 8) {
					if (board[xB][yB].getPiece() instanceof Bishop && board[xB][yB].getPiece().getColor() == 1) {
						b = (Bishop) board[xB][yB].getPiece();
						board[xB][yB].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(b.getType() + "spostato su" + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB--;
						yB++;

					}
				}
				xB = x + 1;
				yB = y - 1;
				while (xB < 8 && yB >= 0) {
					if (board[xB][yB].getPiece() instanceof Bishop && board[xB][yB].getPiece().getColor() == 1) {
						b = (Bishop) board[xB][yB].getPiece();
						board[xB][yB].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(b.getType() + "spostato su" + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB++;
						yB--;
					}
				}
				xB = x + 1;
				yB = y + 1;
				while (xB < 8 && yB < 8) {
					if (board[xB][yB].getPiece() instanceof Bishop && board[xB][yB].getPiece().getColor() == 1) {
						b = (Bishop) board[xB][yB].getPiece();
						board[xB][yB].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(b.getType() + "spostato su" + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB++;
						yB++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, l'alfiere non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non è vuota");

		}

	}

	void captureBishop(String move) throws IllegalMoveException {
		int x;
		int y;
		int xb;
		int yb;
		Bishop b;

		y = Colonna.valueOf(move.substring(2, 3)).ordinal();
		x = 8 - Integer.parseInt(move.substring(3, 4));
		if (whiteTurn == true) { // controlli per bianchi
			if (board[x][y].getPiece() != null) {
				xb = x - 1;
				yb = y - 1;
				while (xb >= 0 && yb >= 0) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 0)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + b.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[xb][yb].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(b.getType() + " spostato su " + move.substring(2, 4));
						return;
					} else if (board[xb][yb].getPiece() != null) {
						break;
					} else {
						xb--;
						yb--;
					}
				}
				xb = x - 1;
				yb = y + 1;
				while (xb >= 0 && yb < 8) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 0)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + b.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[xb][yb].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(b.getType() + " spostato su " + move.substring(2, 4));
						return;
					} else if (board[xb][yb].getPiece() != null) {
						break;
					} else {
						xb--;
						yb++;
					}
				}
				xb = x + 1;
				yb = y - 1;
				while (xb < 8 && yb >= 0) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 0)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + b.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[xb][yb].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(b.getType() + " spostato su " + move.substring(2, 4));
						return;
					} else if (board[xb][yb].getPiece() != null) {
						break;
					} else {
						xb++;
						yb--;
					}
				}

				xb = x + 1;
				yb = y + 1;
				while (xb < 8 && yb < 8) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 0)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + b.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[xb][yb].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(b.getType() + " spostato su " + move.substring(2, 4));
						return;
					} else if (board[xb][yb].getPiece() != null) {
						break;
					} else {
						xb++;
						yb++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, l'alfiere non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione è vuota");

		} else { // controlli per i pezzi neri
			if (board[x][y].getPiece() != null) {
				xb = x - 1;
				yb = y - 1;
				while (xb >= 0 && yb >= 0) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 1)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + b.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[xb][yb].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(b.getType() + " spostato su " + move.substring(2, 4));
						return;
					} else if (board[xb][yb].getPiece() != null) {
						break;
					} else {
						xb--;
						yb--;
					}
				}
				xb = x - 1;
				yb = y + 1;
				while (xb >= 0 && yb < 8) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 1)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + b.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[xb][yb].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(b.getType() + " spostato su " + move.substring(2, 4));
						return;
					} else if (board[xb][yb].getPiece() != null) {
						break;
					} else {
						xb--;
						yb++;
					}
				}
				xb = x + 1;
				yb = y - 1;
				while (xb < 8 && yb >= 0) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 1)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + b.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[xb][yb].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(b.getType() + " spostato su " + move.substring(2, 4));
						return;
					} else if (board[xb][yb].getPiece() != null) {
						break;
					} else {
						xb++;
						yb--;
					}
				}

				xb = x + 1;
				yb = y + 1;
				while (xb < 8 && yb < 8) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 1)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + b.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[xb][yb].setEmpty();
						board[x][y].setPiece(b);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(b.getType() + " spostato su " + move.substring(2, 4));
						return;
					} else if (board[xb][yb].getPiece() != null) {
						break;
					} else {
						xb++;
						yb++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, l'alfiere non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione è vuota");

		}

	}

	void moveKnight(String move) throws IllegalMoveException {
		int x;
		int y;
		int lCk; // l sta per lenght, controllo sulle ascisse
		int hCk; // h sta per height, controllo sulle ordinate
		Knight kn;

		y = Colonna.valueOf(move.substring(1, 2)).ordinal();
		x = 8 - Integer.parseInt(move.substring(2, 3));

		if (whiteTurn == true) {
			if (board[x][y].getPiece() == null) {// controlli per i pezzi bianchi
				lCk = x - 1;
				hCk = y - 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x - 1;
				hCk = y + 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x + 1;
				hCk = y - 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x + 1;
				hCk = y + 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x - 2;
				hCk = y - 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x + 2;
				hCk = y - 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x - 2;
				hCk = y + 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x + 2;
				hCk = y + 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}
				throw new IllegalMoveException("Mossa illegale, il cavallo non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non e' vuota");
		} else {
			if (board[x][y].getPiece() == null) { // controllo per i neri
				lCk = x - 1;
				hCk = y - 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x - 1;
				hCk = y + 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x + 1;
				hCk = y - 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x + 1;
				hCk = y + 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x - 2;
				hCk = y - 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x + 2;
				hCk = y - 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x - 2;
				hCk = y + 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}

				lCk = x + 2;
				hCk = y + 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(1, 3));
						return;
					}
				}
				throw new IllegalMoveException("Mossa illegale, il cavallo non può muoversi qui");

			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non e' vuota");
		}
	}

	void captureKnight(String move) throws IllegalMoveException {
		int x;
		int y;
		int lCk; // l sta per lenght, controllo sulle ascisse
		int hCk; // h sta per height, controllo sulle ordinate
		Knight kn;

		y = Colonna.valueOf(move.substring(2, 3)).ordinal();
		x = 8 - Integer.parseInt(move.substring(3, 4));

		if (whiteTurn == true) {
			if (board[x][y].getPiece() != null) { // controlli per i pezzi bianchi
				lCk = x - 1;
				hCk = y - 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x - 1;
				hCk = y + 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x + 1;
				hCk = y - 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x + 1;
				hCk = y + 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x - 2;
				hCk = y - 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x + 2;
				hCk = y - 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x - 2;
				hCk = y + 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x + 2;
				hCk = y + 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 0) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}
				throw new IllegalMoveException("Mossa illegale, il cavallo non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non e' vuota");
		} else {
			if (board[x][y].getPiece() != null) { // controllo per i neri
				lCk = x - 1;
				hCk = y - 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x - 1;
				hCk = y + 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x + 1;
				hCk = y - 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x + 1;
				hCk = y + 2;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x - 2;
				hCk = y - 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x + 2;
				hCk = y - 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x - 2;
				hCk = y + 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}

				lCk = x + 2;
				hCk = y + 1;
				if (lCk >= 0 && lCk < 8 && hCk >= 0 && hCk < 8) {
					if (board[lCk][hCk].getPiece() instanceof Knight && board[lCk][hCk].getPiece().getColor() == 1) {
						kn = (Knight) board[lCk][hCk].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " è stato catturato da: " + kn.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[lCk][hCk].setEmpty();
						board[x][y].setPiece(kn);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(kn.getType() + " spostato su " + move.substring(2, 4));
						return;
					}
				}
				throw new IllegalMoveException("Mossa illegale, il cavallo non può muoversi qui");

			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non e' vuota");
		}
	}

	void moveRook(String move) throws IllegalMoveException {
		int x;
		int y;
		int mcheck; // vcheck
		int ncheck; // hcheck
		Rook r;

		y = Colonna.valueOf(move.substring(1, 2)).ordinal();
		x = 8 - Integer.parseInt(move.substring(2, 3));
		if (whiteTurn == true) {
			if (board[x][y].getPiece() == null) {
				mcheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (mcheck < 8) {
					if ((board[mcheck][y].getPiece() instanceof Rook)
							&& (board[mcheck][y].getPiece().getColor() == 0)) {
						r = (Rook) board[mcheck][y].getPiece();
						board[mcheck][y].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(r.getType() + " spostata su " + move.substring(1, 3));
						return;

					} else if (board[mcheck][y].getPiece() != null) {
						break;
					} else {
						mcheck++;
					}
				}
				mcheck = x - 1;
				while (mcheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
					if ((board[mcheck][y].getPiece() instanceof Rook)
							&& (board[mcheck][y].getPiece().getColor() == 0)) {
						r = (Rook) board[mcheck][y].getPiece();
						board[mcheck][y].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(r.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[mcheck][y].getPiece() != null) {
						break;
					} else {
						mcheck--;
					}
				}
				ncheck = y + 1; // controllo in orizzontale a destra
				while (ncheck < 8) {
					if ((board[x][ncheck].getPiece() instanceof Rook)
							&& (board[x][ncheck].getPiece().getColor() == 0)) {
						r = (Rook) board[x][ncheck].getPiece();
						board[x][ncheck].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(r.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[x][ncheck].getPiece() != null) {
						break;
					} else {
						ncheck++;
					}
				}
				ncheck = y - 1;
				while (ncheck >= 0) { // controllo in orizzontale a sinistra
					if ((board[x][ncheck].getPiece() instanceof Rook)
							&& (board[x][ncheck].getPiece().getColor() == 0)) {
						r = (Rook) board[x][ncheck].getPiece();
						board[x][ncheck].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(r.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[x][ncheck].getPiece() != null) {
						break;
					} else {
						ncheck--;
					}
				}

				throw new IllegalMoveException("Mossa illegale, la torre non puÃ² muoversi qui");

			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non Ã¨ vuota");

		} else { // neri
			if (board[x][y].getPiece() == null) {
				mcheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (mcheck < 8) {
					if ((board[mcheck][y].getPiece() instanceof Rook)
							&& (board[mcheck][y].getPiece().getColor() == 1)) {
						r = (Rook) board[mcheck][y].getPiece();
						board[mcheck][y].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(r.getType() + " spostata su " + move.substring(1, 3));
						return;

					} else if (board[mcheck][y].getPiece() != null) {
						break;
					} else {
						mcheck++;
					}
				}
				mcheck = x - 1;
				while (mcheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
					if ((board[mcheck][y].getPiece() instanceof Rook)
							&& (board[mcheck][y].getPiece().getColor() == 1)) {
						r = (Rook) board[mcheck][y].getPiece();
						board[mcheck][y].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(r.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[mcheck][y].getPiece() != null) {
						break;
					} else {
						mcheck--;
					}
				}
				ncheck = y + 1; // controllo in orizzontale a destra
				while (ncheck < 8) {
					if ((board[x][ncheck].getPiece() instanceof Rook)
							&& (board[x][ncheck].getPiece().getColor() == 1)) {
						r = (Rook) board[x][ncheck].getPiece();
						board[x][ncheck].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(r.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[x][ncheck].getPiece() != null) {
						break;
					} else {
						ncheck++;
					}
				}
				ncheck = y - 1;
				while (ncheck >= 0) { // controllo in orizzontale a sinistra
					if ((board[x][ncheck].getPiece() instanceof Rook)
							&& (board[x][ncheck].getPiece().getColor() == 1)) {
						r = (Rook) board[x][ncheck].getPiece();
						board[x][ncheck].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(r.getType() + " spostata su " + move.substring(1, 3));
						return;
					} else if (board[x][ncheck].getPiece() != null) {
						break;
					} else {
						ncheck--;
					}
				}
				throw new IllegalMoveException("Mossa illegale, la torre non puÃ² muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non Ã¨ vuota");

		}
	}

	void captureRook(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata
		int mcheck; // sentinella dell'ascissa
		int ncheck; // sentinella dell'ordinata
		Rook r;

		y = Colonna.valueOf(move.substring(2, 3)).ordinal();
		x = 8 - Integer.parseInt(move.substring(3, 4));
		if (whiteTurn == true) {
			if (board[x][y].getPiece() != null) {
				mcheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (mcheck < 8) {
					if ((board[mcheck][y].getPiece() instanceof Rook)
							&& (board[mcheck][y].getPiece().getColor() == 0)) {
						r = (Rook) board[mcheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " Ã¨ stato catturato da: " + r.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[mcheck][y].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = false;

						System.out.println(r.getType() + " spostata su " + move.substring(2, 4));
						return;

					} else if (board[mcheck][y].getPiece() != null) {
						break;
					} else {
						mcheck++;
					}
				}
				mcheck = x - 1;
				while (mcheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
					if ((board[mcheck][y].getPiece() instanceof Rook)
							&& (board[mcheck][y].getPiece().getColor() == 0)) {
						r = (Rook) board[mcheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " Ã¨ stato catturato da: " + r.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[mcheck][y].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(r.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[mcheck][y].getPiece() != null) {
						break;
					} else {
						mcheck--;
					}
				}
				ncheck = y + 1; // controllo in orizzontale a destra
				while (ncheck < 8) {
					if ((board[x][ncheck].getPiece() instanceof Rook)
							&& (board[x][ncheck].getPiece().getColor() == 0)) {
						r = (Rook) board[x][ncheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " Ã¨ stato catturato da: " + r.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[x][ncheck].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(r.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[x][ncheck].getPiece() != null) {
						break;
					} else {
						ncheck++;
					}
				}
				ncheck = y - 1;
				while (ncheck >= 0) { // controllo in orizzontale a sinistra
					if ((board[x][ncheck].getPiece() instanceof Rook)
							&& (board[x][ncheck].getPiece().getColor() == 0)) {
						r = (Rook) board[x][ncheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " Ã¨ stato catturato da: " + r.getType()
								+ " in " + move.substring(2, 4));
						BlacksCaptured.add(board[x][y].getPiece());
						board[x][ncheck].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = false;
						System.out.println(r.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[x][ncheck].getPiece() != null) {
						break;
					} else {
						ncheck--;
					}
				}
				throw new IllegalMoveException("Mossa illegale, la torre non puÃ² muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione Ã¨ vuota");

		} else { // neri
			if (board[x][y].getPiece() != null) {
				mcheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (mcheck < 8) {
					if ((board[mcheck][y].getPiece() instanceof Rook)
							&& (board[mcheck][y].getPiece().getColor() == 1)) {
						r = (Rook) board[mcheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " Ã¨ stato catturato da: " + r.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[mcheck][y].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(r.getType() + " spostata su " + move.substring(2, 4));
						return;

					} else if (board[mcheck][y].getPiece() != null) {
						break;
					} else {
						mcheck++;
					}
				}
				mcheck = x - 1;
				while (mcheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
					if ((board[mcheck][y].getPiece() instanceof Rook)
							&& (board[mcheck][y].getPiece().getColor() == 1)) {
						r = (Rook) board[mcheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " Ã¨ stato catturato da: " + r.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[mcheck][y].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(r.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[mcheck][y].getPiece() != null) {
						break;
					} else {
						mcheck--;
					}
				}
				ncheck = y + 1; // controllo in orizzontale a destra
				while (ncheck < 8) {
					if ((board[x][ncheck].getPiece() instanceof Rook)
							&& (board[x][ncheck].getPiece().getColor() == 1)) {
						r = (Rook) board[x][ncheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " Ã¨ stato catturato da: " + r.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[x][ncheck].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(r.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[x][ncheck].getPiece() != null) {
						break;
					} else {
						ncheck++;
					}
				}
				ncheck = y - 1;
				while (ncheck >= 0) { // controllo in orizzontale a sinistra
					if ((board[x][ncheck].getPiece() instanceof Rook)
							&& (board[x][ncheck].getPiece().getColor() == 1)) {
						r = (Rook) board[x][ncheck].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " Ã¨ stato catturato da: " + r.getType()
								+ " in " + move.substring(2, 4));
						WhitesCaptured.add(board[x][y].getPiece());
						board[x][ncheck].setEmpty();
						board[x][y].setPiece(r);
						movesDone.add(move);
						whiteTurn = true;
						System.out.println(r.getType() + " spostata su " + move.substring(2, 4));
						return;
					} else if (board[x][ncheck].getPiece() != null) {
						break;
					} else {
						ncheck--;
					}
				}
				throw new IllegalMoveException("Mossa illegale, la torre non puÃ² muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione Ã¨ vuota");

		}
	}

	void shortCastling() throws IllegalMoveException {
		if (whiteTurn == true) {
			if ((board[7][4].getPiece() instanceof King) && (board[7][7].getPiece() instanceof Rook)) {		//controllo che re e torre siano nella posizione corretta
				King k = (King) board[7][4].getPiece();
				Rook r = (Rook) board[7][7].getPiece();
				if ((k.getNumberOfMoves() == 0) && (r.getNumberOfMoves() == 0)) {		//controllo che non siano stati ancora mossi
					if ((King.isThreatened(board, whiteTurn, 7, 4)) || (King.isThreatened(board, whiteTurn, 7, 5)) //controllo che il re non è, e non finisce sotto scacco durante la mossa
							|| (King.isThreatened(board, whiteTurn, 7, 6))) {
						throw new IllegalMoveException(
								"mossa illegale; Il re è sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
					} else {
						if ((board[7][5].getPiece() == null) && (board[7][6].getPiece() == null)) {		//controllo se il percorso è libero
							k.incrementMoves();
							r.incrementMoves();
							board[7][6].setPiece(k);
							board[7][5].setPiece(r);
							board[7][4].setEmpty();
							board[7][7].setEmpty();
							System.out.println("Arrocco corto eseguito");
						} else {
							throw new IllegalMoveException("mossa illegale; il percorso non è libero");
						}
					}

				} else {
					throw new IllegalMoveException(
							"mossa illegale; Il re o la torre sono già stati mossi in precedenza");
				}

			} else {
				throw new IllegalMoveException(
						"mossa illegale; Impossibile effettuare arrocco corto, Re e torre non sono nella posizione iniziale");
			}
		} else {
			if ((board[0][4].getPiece() instanceof King) && (board[0][7].getPiece() instanceof Rook)) {		//controllo che re e torre siano nella posizione corretta
				King k = (King) board[0][4].getPiece();
				Rook r = (Rook) board[0][7].getPiece();
				if ((k.getNumberOfMoves() == 0) && (r.getNumberOfMoves() == 0)) {					//controllo che non siano stati ancora mossi
					if ((King.isThreatened(board, whiteTurn, 0, 4)) || (King.isThreatened(board, whiteTurn, 0, 5))
							|| (King.isThreatened(board, whiteTurn, 0, 6))) {						//controllo che il re non è, e non finisce sotto scacco durante la mossa
						throw new IllegalMoveException(
								"mossa illegale; Il re è sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
					} else {
						if ((board[0][5].getPiece() == null) && (board[0][6].getPiece() == null)) {		//controllo se il percorso è libero
							k.incrementMoves();
							r.incrementMoves();
							board[0][6].setPiece(k);
							board[0][5].setPiece(r);
							board[0][4].setEmpty();
							board[0][7].setEmpty();
							System.out.println("Arrocco corto eseguito");
						} else {
							throw new IllegalMoveException("mossa illegale; il percorso non è libero");
						}
					}

				} else {
					throw new IllegalMoveException(
							"mossa illegale; Il re o la torre sono già stati mossi in precedenza");
				}

			} else {
				throw new IllegalMoveException(
						"mossa illegale; Impossibile effettuare arrocco corto, Re e torre non sono nella posizione iniziale");
			}
		}

	}

	void longCastling() throws IllegalMoveException {
		if (whiteTurn == true) {
			if ((board[7][4].getPiece() instanceof King) && (board[7][0].getPiece() instanceof Rook)) {		//controllo che re e torre siano nella posizione corretta
				King k = (King) board[7][4].getPiece();
				Rook r = (Rook) board[7][0].getPiece();
				if ((k.getNumberOfMoves() == 0) && (r.getNumberOfMoves() == 0)) {		//controllo che non siano stati ancora mossi
					if ((King.isThreatened(board, whiteTurn, 7, 4)) || (King.isThreatened(board, whiteTurn, 7, 3)) //controllo che il re non è, e non finisce sotto scacco durante la mossa
							|| (King.isThreatened(board, whiteTurn, 7, 2))) {
						throw new IllegalMoveException(
								"mossa illegale; Il re è sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
					} else {
						if ((board[7][3].getPiece() == null) && (board[7][2].getPiece() == null)) {		//controllo se il percorso è libero
							k.incrementMoves();
							r.incrementMoves();
							board[7][2].setPiece(k);
							board[7][3].setPiece(r);
							board[7][4].setEmpty();
							board[7][0].setEmpty();
							System.out.println("Arrocco lungo eseguito");
						} else {
							throw new IllegalMoveException("mossa illegale; il percorso non è libero");
						}
					}

				} else {
					throw new IllegalMoveException(
							"mossa illegale; Il re o la torre sono già stati mossi in precedenza");
				}

			} else {
				throw new IllegalMoveException(
						"mossa illegale; Impossibile effettuare arrocco lungo, Re e torre non sono nella posizione iniziale");
			}
		} else {
			if ((board[0][4].getPiece() instanceof King) && (board[0][0].getPiece() instanceof Rook)) {		//controllo che re e torre siano nella posizione corretta
				King k = (King) board[0][4].getPiece();
				Rook r = (Rook) board[0][0].getPiece();
				if ((k.getNumberOfMoves() == 0) && (r.getNumberOfMoves() == 0)) {					//controllo che non siano stati ancora mossi
					if ((King.isThreatened(board, whiteTurn, 0, 4)) || (King.isThreatened(board, whiteTurn, 0, 3))
							|| (King.isThreatened(board, whiteTurn, 0, 2))) {						//controllo che il re non è, e non finisce sotto scacco durante la mossa
						throw new IllegalMoveException(
								"mossa illegale; Il re è sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
					} else {
						if ((board[0][3].getPiece() == null) && (board[0][2].getPiece() == null)) {		//controllo se il percorso è libero
							k.incrementMoves();
							r.incrementMoves();
							board[0][2].setPiece(k);
							board[0][3].setPiece(r);
							board[0][4].setEmpty();
							board[0][0].setEmpty();
							System.out.println("Arroco lungo eseguito");
						} else {
							throw new IllegalMoveException("mossa illegale; il percorso non è libero");
						}
					}

				} else {
					throw new IllegalMoveException(
							"mossa illegale; Il re o la torre sono già stati mossi in precedenza");
				}

			} else {
				throw new IllegalMoveException(
						"mossa illegale; Impossibile effettuare arrocco lungo, Re e torre non sono nella posizione iniziale");
			}
		}

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