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
				throw new IllegalMoveException(
						"Mossa illegale, la donna non può muoversi qui");

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
				throw new IllegalMoveException(
						"Mossa illegale, la donna non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione non è vuota");

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
						System.out.println(b.getType() + "spostato su" + move.substring(1,3));
						return;
					  } else if(board[xB][yB].getPiece() != null){
						  break;	  
					  } else {
						  xB --;
						  yB --;
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
						System.out.println(b.getType() + "spostato su" + move.substring(1,3));
						return;
					  } else if(board[xB][yB].getPiece() != null){
						  break;	  
					  } else {
						  xB --;
						  yB ++;
				
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
						System.out.println(b.getType() + "spostato su" + move.substring(1,3));
						return;
					  } else if(board[xB][yB].getPiece() != null){
						  break;	  
					  } else {
						  xB ++;
						  yB --;
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
						System.out.println(b.getType() + "spostato su" + move.substring(1,3));
						return;
					  } else if(board[xB][yB].getPiece() != null){
						  break;	  
					  } else {
						  xB ++;
						  yB ++;
					  }
				}
				throw new IllegalMoveException(
						"Mossa illegale, l'alfiere non può muoversi qui");
			 } else throw new IllegalMoveException(
					    "Mossa illegale, la casella di destinazione non è vuota");
			
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
							System.out.println(b.getType() + "spostato su" + move.substring(1,3));
							return;
						  } else if(board[xB][yB].getPiece() != null){
							  break;	  
						  } else {
							  xB --;
							  yB --;
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
							System.out.println(b.getType() + "spostato su" + move.substring(1,3));
							return;
						  } else if(board[xB][yB].getPiece() != null){
							  break;	  
						  } else {
							  xB --;
							  yB ++;
					
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
							System.out.println(b.getType() + "spostato su" + move.substring(1,3));
							return;
						  } else if(board[xB][yB].getPiece() != null){
							  break;	  
						  } else {
							  xB ++;
							  yB --;
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
							System.out.println(b.getType() + "spostato su" + move.substring(1,3));
							return;
						  } else if(board[xB][yB].getPiece() != null){
							  break;	  
						  } else {
							  xB ++;
							  yB ++;
						  }
					}
					throw new IllegalMoveException(
							"Mossa illegale, l'alfiere non può muoversi qui");
				 } else throw new IllegalMoveException(
						    "Mossa illegale, la casella di destinazione non è vuota");
				
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
				throw new IllegalMoveException(
						"Mossa illegale, la donna non può muoversi qui");
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
				throw new IllegalMoveException(
						"Mossa illegale, la donna non può muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la casella di destinazione è vuota");

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