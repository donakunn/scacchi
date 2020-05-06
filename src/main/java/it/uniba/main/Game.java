package it.uniba.main;

import java.util.ArrayList;

/**
 * <<entity>><br>
 * Game is the main entity of the application. It contains the chessboard, turn
 * of player currently playing, white and black pieces captured, moves done. It
 * also contains the main methods to activate a move or a capture event for each
 * piece.
 * 
 * @author Megi Gjata
 * @author Mario Giordano
 * @author Donato Lucente
 * @author Patrick Clark
 * @author Filippo Iacobellis
 * 
 */

class Game {
	private static boolean whiteTurn = true;
	private static Cell board[][] = new Cell[8][8];

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
		System.out.println("Creo nuova partita..");
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
		System.out.println("Partita creata.");
	};

	void moveAPawn(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata

		y = (int) (move.charAt(0)) - 97; // lettura x e y casella di destinazione
		x = 8 - Integer.parseInt(move.substring(1, 2));

		Pawn p;

		if ((x > 0) && (x < 8) && (board[x - 1][y].getPiece() instanceof Pawn)
				&& (board[x - 1][y].getPiece().getColor() == 1)
				// check se casella in x-1 c'e' pedone con colore 1
				&& whiteTurn == false) {
			p = (Pawn) board[x - 1][y].getPiece(); // se le condizioni sono rispettate fa la mossa

			if (board[x][y].getPiece() == null) {
				board[x - 1][y].setEmpty();
				board[x][y].setPiece(p);
				movesDone.add(move);
				p.incrementMoves();
				whiteTurn = true;
				System.out.println(p.getType() + " spostato su " + move);
			} else
				throw new IllegalMoveException("mossa illegale; la cella di destinazione non e' vuota.");
		} else if ((x > 1) && (x < 8) && (board[x - 2][y].getPiece() instanceof Pawn)
				&& (board[x - 2][y].getPiece().getColor() == 1)
				// check se casella in x-2 c'e' pedone con colore 1
				&& (whiteTurn == false) && (board[x - 2][y].getPiece().getMoves() == 0)) { // se le condizioni sono
																							// rispettate fa la mossa
			p = (Pawn) board[x - 2][y].getPiece();

			if (board[x][y].getPiece() == null) {
				board[x - 2][y].setEmpty();
				board[x][y].setPiece(p);
				movesDone.add(move);
				p.incrementMoves();
				whiteTurn = true;
				System.out.println(p.getType() + " spostato su " + move);
			} else
				throw new IllegalMoveException("mossa illegale; la cella di destinazione non e' vuota.");
		} else if ((x >= 0) && (x < 7) && (board[x + 1][y].getPiece() instanceof Pawn)
				&& (board[x + 1][y].getPiece().getColor() == 0)
				// check se casella in x+1 c'e' pedone con colore 0
				&& whiteTurn == true) { // se le condizioni sono rispettate fa la mossa
			p = (Pawn) board[x + 1][y].getPiece();

			if (board[x][y].getPiece() == null) {
				board[x + 1][y].setEmpty();
				board[x][y].setPiece(p);
				movesDone.add(move);
				p.incrementMoves();
				whiteTurn = false;
				System.out.println(p.getType() + " spostato su " + move);
			} else
				throw new IllegalMoveException("mossa illegale; la cella di destinazione non e' vuota.");
		} else if ((x >= 0) && (x < 6) && (board[x + 2][y].getPiece() instanceof Pawn)
				&& (board[x + 2][y].getPiece().getColor() == 0)
				// check se casella in x+2 c'e' pedone con colore 1
				&& (whiteTurn == true) && (board[x + 2][y].getPiece().getMoves() == 0)) { // se le condizioni sono
																							// rispettate fa la mossa
			p = (Pawn) board[x + 2][y].getPiece();

			if (board[x][y].getPiece() == null) {
				board[x + 2][y].setEmpty();
				board[x][y].setPiece(p);
				movesDone.add(move);
				p.incrementMoves();
				whiteTurn = false;
				System.out.println(p.getType() + " spostato su " + move);
			} else
				throw new IllegalMoveException("mossa illegale; la cella di destinazione non e' vuota.");
		} else {
			throw new IllegalMoveException("mossa illegale; nessun pedone puo' spostarsi qui");
		}

	}

	void pawnCapture(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata
		int z; // colonna del pezzo di provenienza

		Piece p, caught;

		y = (int) (move.charAt(2)) - 97;
		x = 8 - Integer.parseInt(move.substring(3, 4)); // calcolo x,y di cella di destinazione e z colonna di partenza
		z = (int) (move.charAt(0)) - 97;

		if (board[x][y].getPiece() == null) { // se cella di destinazione e' vuota prova a fare cattura en passant
			try {
				this.captureEnPassant(move);
			} catch (IllegalMoveException e) {
				System.err.println(e.getMessage());
			}
		} else {

			if (whiteTurn == false) {
				if (z == y - 1) {
					if (board[x][y] != null) {
						if (board[x - 1][y - 1].getPiece() instanceof Pawn) { // cattura in diagonale da sinistra
							p = (Pawn) board[x - 1][y - 1].getPiece();

							if (board[x][y].getPiece().getColor() != p.getColor()) {
								caught = board[x][y].getPiece();
								board[x][y].setPiece(p);
								board[x - 1][y - 1].setEmpty();
								movesDone.add(move);
								this.WhitesCaptured.add(caught);
								whiteTurn = true;
								System.out.println(caught.getType() + " e' stato catturato da " + p.getType() + " su "
										+ move.substring(2, 4));
								System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
							} else
								throw new IllegalMoveException(
										"mossa illegale; Impossibile catturare pezzo dello stesso colore.");
						} else
							throw new IllegalMoveException(
									"mossa illegale; Nessun pedone puo' catturare dalla colonna di partenza indicata.");
					} else
						throw new IllegalMoveException("mossa illegale. La cella e' vuota.");

				} else if (z == y + 1) {
					if (board[x][y] != null) {
						if (board[x - 1][y + 1].getPiece() instanceof Pawn) { // cattura in diagonale da destra
							p = (Pawn) board[x - 1][y + 1].getPiece();

							if (board[x][y].getPiece().getColor() != p.getColor()) {
								caught = board[x][y].getPiece();
								board[x][y].setPiece(p);
								board[x - 1][y + 1].setEmpty();
								movesDone.add(move);
								this.WhitesCaptured.add(caught);
								whiteTurn = true;
								System.out.println(caught.getType() + " e' stato catturato da " + p.getType() + " su "
										+ move.substring(2, 4));
								System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
							} else
								throw new IllegalMoveException(
										"mossa illegale; Impossibile catturare pezzo dello stesso colore.");
						} else
							throw new IllegalMoveException(
									"mossa illegale; Nessun pedone puo' catturare dalla colonna di partenza indicata.");
					} else
						throw new IllegalMoveException("mossa illegale. La cella e' vuota.");
				} else
					throw new IllegalMoveException(
							"mossa illegale; Nessuna possibile cattura da parte di un Pedone a partire dalla colonna indicata");

			} else {

				if (z == y - 1) {
					if (board[x][y] != null) {
						if (board[x + 1][y - 1].getPiece() instanceof Pawn) { // cattura in diagonale da sinistra
							p = (Pawn) board[x + 1][y - 1].getPiece();

							if (board[x][y].getPiece().getColor() != p.getColor()) {
								caught = board[x][y].getPiece();
								board[x][y].setPiece(p);
								board[x + 1][y - 1].setEmpty();
								movesDone.add(move);
								this.BlacksCaptured.add(caught);
								whiteTurn = false;
								System.out.println(caught.getType() + " e' stato catturato da " + p.getType() + " su "
										+ move.substring(2, 4));
								System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
							} else
								throw new IllegalMoveException(
										"mossa illegale; Impossibile catturare pezzo dello stesso colore.");
						} else
							throw new IllegalMoveException(
									"mossa illegale; Nessun pedone puo' catturare dalla colonna di partenza indicata.");
					} else
						throw new IllegalMoveException("mossa illegale. La cella e' vuota.");

				} else if (z == y + 1) {
					if (board[x][y] != null) {
						if (board[x + 1][y + 1].getPiece() instanceof Pawn) { // cattura in diagonale da destra
							p = (Pawn) board[x + 1][y + 1].getPiece();

							if (board[x][y].getPiece().getColor() != p.getColor()) {
								caught = board[x][y].getPiece();
								board[x][y].setPiece(p);
								board[x + 1][y + 1].setEmpty();
								movesDone.add(move);
								this.BlacksCaptured.add(caught);
								whiteTurn = false;
								System.out.println(caught.getType() + " e' stato catturato da " + p.getType() + " su "
										+ move.substring(2, 4));
								System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
							} else
								throw new IllegalMoveException(
										"mossa illegale; Impossibile catturare pezzo dello stesso colore.");
						} else
							throw new IllegalMoveException(
									"mossa illegale; Nessun pedone puo' catturare dalla colonna di partenza indicata.");
					} else
						throw new IllegalMoveException("mossa illegale. La cella e' vuota.");
				} else
					throw new IllegalMoveException(
							"mossa illegale; Nessuna possibile cattura da parte di un Pedone a partire dalla colonna indicata");
			}
		}
	}

	void captureEnPassant(String move) throws IllegalMoveException {

		int x; // ascissa
		int y; // ordinata
		int z; // colonna del pezzo di provenienza

		Piece p;

		y = (int) (move.charAt(2)) - 97;
		x = 8 - Integer.parseInt(move.substring(3, 4));
		z = (int) (move.charAt(0)) - 97;
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
							System.out.println(caught.getType() + " e' stato catturato da " + p.getType() + " su "
									+ move.substring(2, 4) + " e.p.");
							System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
						} else
							throw new IllegalMoveException(
									"mossa illegale; Pezzo non catturabile con e.p. o cella di destinazione occupata");
					} else
						throw new IllegalMoveException(
								"mossa illegale; Nessun pedone catturabile e.p. alla posizione indicata.");
				} else
					throw new IllegalMoveException(
							"mossa illegale; Nessun Pedone puo' effettuare cattura e.p. a partire dalla colonna inserita");
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
							System.out.println(caught.getType() + " e' stato catturato da " + p.getType() + " su "
									+ move.substring(2, 4) + " e.p.");
							System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
						} else
							throw new IllegalMoveException(
									"mossa illegale; Pezzo non catturabile con e.p. o cella di destinazione occupata");
					} else
						throw new IllegalMoveException(
								"mossa illegale; Nessun pedone catturabile e.p. alla posizione indicata.");
				} else
					throw new IllegalMoveException(
							"mossa illegale; Nessun Pedone puo' effettuare cattura e.p. a partire dalla colonna inserita");
			} else
				throw new IllegalMoveException(
						"mossa illegale; Nessuna possibile cattura da parte di un Pedone a partire dalla colonna indicata");

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
							System.out.println(caught.getType() + " e' stato catturato da " + p.getType() + " su "
									+ move.substring(2, 4) + " e.p.");
							System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
						} else
							throw new IllegalMoveException(
									"mossa illegale; Pezzo non catturabile con e.p. o cella di destinazione occupata");
					} else
						throw new IllegalMoveException(
								"mossa illegale; Nessun pedone catturabile e.p. alla posizione indicata.");
				} else
					throw new IllegalMoveException(
							"mossa illegale; Nessun Pedone puo' effettuare cattura e.p. a partire dalla colonna inserita");
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
							System.out.println(caught.getType() + " e' stato catturato da " + p.getType() + " su "
									+ move.substring(2, 4) + " e.p.");
							System.out.println(p.getType() + " spostato su " + move.substring(2, 4));
						} else
							throw new IllegalMoveException(
									"mossa illegale; Pezzo non catturabile con e.p. o cella di destinazione occupata");
					} else
						throw new IllegalMoveException(
								"mossa illegale; Nessun pedone catturabile e.p. alla posizione indicata.");
				} else
					throw new IllegalMoveException(
							"mossa illegale; Nessun Pedone puo' effettuare cattura e.p. a partire dalla colonna inserita");
			} else
				throw new IllegalMoveException(
						"mossa illegale; Nessuna possibile cattura da parte di un Pedone a partire dalla colonna indicata");
		}
	}

	void moveKing(String move) throws IllegalMoveException {
		int x = 2;
		int y = 1;

		if (move.length() == 4) {
			x = 3;
			y = 2;
		}

		y = (int) move.charAt(y) - 97;
		x = 8 - Integer.parseInt(move.substring(x, x + 1));

		if (board[x][y].getPiece() != null && board[x][y].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
			throw new IllegalMoveException("Non puoi spostarti sulla cella di un alleato.");
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
			throw new IllegalMoveException("Il Re non può muoversi in quella cella");
		}
		if (King.isThreatened(board, whiteTurn, x, y)) {
			throw new IllegalMoveException("Mossa illegale, metterebbe il Re sotto scacco");
		}

		if (board[x][y].getPiece() == null) {
			if (move.charAt(1) == 'x') {
				throw new IllegalMoveException(
						"Mossa illegale, non c'è nessun pezzo da catturare nella cella di arrivo");
			}
		} else {
			if (move.charAt(1) != 'x') {
				throw new IllegalMoveException(
						"Mossa illegale, devi specificare la cattura come da notazione algebrica");
			}
			if (whiteTurn) {
				BlacksCaptured.add(board[x][y].getPiece());
			} else {
				WhitesCaptured.add(board[x][y].getPiece());
			}
			System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: "
					+ board[xK][yK].getPiece().getType() + " in " + move.substring(2, 4));
		}
		board[x][y].setPiece(board[xK][yK].getPiece());
		((King) board[x][y].getPiece()).incrementMoves(); // da controllare
		board[xK][yK].setEmpty();
		movesDone.add(move);
		whiteTurn = !whiteTurn;
		System.out.println(board[x][y].getPiece().getType() + " spostato su " + (char) (y + 97) + (8 - x));
	}

	void moveQueen(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata
		int vCheck; // sentinella dell'ascissa
		int hCheck; // sentinella dell'ordinata
		Queen q;

		y = (int) move.charAt(1) - 97;
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
				throw new IllegalMoveException("mossa illegale; la donna non puo' muoversi qui");

			} else
				throw new IllegalMoveException("mossa illegale; la cella di destinazione non e' vuota");

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
				throw new IllegalMoveException("mossa illegale; la donna non puo' muoversi qui");
			} else
				throw new IllegalMoveException("mossa illegale; la cella di destinazione non e' vuota");

		}
	}

	void captureQueen(String move) throws IllegalMoveException {

		int x; // ascissa
		int y; // ordinata
		int vCheck; // sentinella dell'ascissa
		int hCheck; // sentinella dell'ordinata
		Queen q;

		y = (int) move.charAt(2) - 97;
		x = 8 - Integer.parseInt(move.substring(3, 4));
		if (whiteTurn == true) {
			if (board[x][y].getPiece() != null) {
				vCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (vCheck < 8) {
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 0)) {
						q = (Queen) board[vCheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						"mossa illegale; la donna non puo' effettuare la cattura nella cella di destinazione data");
			} else
				throw new IllegalMoveException("mossa illegale; la cella di destinazione e' vuota");

		} else { // neri
			if (board[x][y].getPiece() != null) {
				vCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
				while (vCheck < 8) {
					if ((board[vCheck][y].getPiece() instanceof Queen)
							&& (board[vCheck][y].getPiece().getColor() == 1)) {
						q = (Queen) board[vCheck][y].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + q.getType()
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
						"mossa illegale; la donna non puo' effettuare la cattura nella cella di destinazione data");
			} else
				throw new IllegalMoveException("mossa illegale; la cella di destinazione e' vuota");
		}

	}

	void moveBishop(String move) throws IllegalMoveException {
		int x;
		int y;
		int xB;
		int yB;
		Bishop b;

		y = (int) move.charAt(1) - 97;
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
						System.out.println(b.getType() + " spostato su " + move.substring(1, 3));
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
						System.out.println(b.getType() + " spostato su " + move.substring(1, 3));
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
						System.out.println(b.getType() + " spostato su " + move.substring(1, 3));
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
						System.out.println(b.getType() + " spostato su " + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB++;
						yB++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, l'alfiere non puo' muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la cella di destinazione non e' vuota");

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
						System.out.println(b.getType() + " spostato su " + move.substring(1, 3));
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
						System.out.println(b.getType() + " spostato su " + move.substring(1, 3));
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
						System.out.println(b.getType() + " spostato su " + move.substring(1, 3));
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
						System.out.println(b.getType() + " spostato su " + move.substring(1, 3));
						return;
					} else if (board[xB][yB].getPiece() != null) {
						break;
					} else {
						xB++;
						yB++;
					}
				}
				throw new IllegalMoveException("Mossa illegale, l'alfiere non puo' muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la cella di destinazione non e' vuota");

		}

	}

	void captureBishop(String move) throws IllegalMoveException {
		int x;
		int y;
		int xb;
		int yb;
		Bishop b;

		y = (int) move.charAt(2) - 97;
		x = 8 - Integer.parseInt(move.substring(3, 4));
		if (whiteTurn == true) { // controlli per bianchi
			if (board[x][y].getPiece() != null) {
				xb = x - 1;
				yb = y - 1;
				while (xb >= 0 && yb >= 0) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 0)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + b.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + b.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + b.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + b.getType()
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
				throw new IllegalMoveException("Mossa illegale, l'alfiere non puo' muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la cella di destinazione e' vuota");

		} else { // controlli per i pezzi neri
			if (board[x][y].getPiece() != null) {
				xb = x - 1;
				yb = y - 1;
				while (xb >= 0 && yb >= 0) {
					if ((board[xb][yb].getPiece() instanceof Bishop) && (board[xb][yb].getPiece().getColor() == 1)) {
						b = (Bishop) board[xb][yb].getPiece();
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + b.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + b.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + b.getType()
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
						System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: " + b.getType()
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
				throw new IllegalMoveException("Mossa illegale, l'alfiere non puo' muoversi qui");
			} else
				throw new IllegalMoveException("Mossa illegale, la cella di destinazione e' vuota");

		}

	}

	boolean isMovableKnight(int x, int y, int a, int b) {
		if ((Math.abs(x - a) == 1 && Math.abs(y - b) == 2) || (Math.abs(y - b) == 1 && Math.abs(x - a) == 2)) {
			if (board[a][b].getPiece() == null || board[a][b].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
		}
		return false;
	}

	void moveKnight(String move) throws IllegalMoveException {
		int count = 0;
		int xC1 = -1, yC1 = -1, xC2 = -1, yC2 = -1;
		int a = 8 - Integer.parseInt(move.substring(move.length() - 1));
		int b = (int) move.charAt(move.length() - 2) - 97;
		if (board[a][b].getPiece() != null && board[a][b].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
			throw new IllegalMoveException("Non puoi spostarti sulla cella di un alleato.");
		}
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (board[i][j].getPiece() instanceof Knight
						&& board[i][j].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
					if (xC1 == -1) {
						xC1 = i;
						yC1 = j;
					} else {
						xC2 = i;
						yC2 = j;
					}
				}
			}
		}
		if (xC1 != -1 && yC1 != -1) {
			if (isMovableKnight(xC1, yC1, a, b)) {
				count = count + 1;
			}
		}
		if (xC2 != -1 && yC2 != -1) {
			if (isMovableKnight(xC2, yC2, a, b)) {
				count = count + 2;
			}
		}

		if (count == 0) {
			throw new IllegalMoveException("Nessun cavallo puo' spostarsi in quella cella.");
		}

		if (count == 1) {
			if (move.charAt(1) == 'x') {
				captureKnight(xC1, yC1, a, b, move);
			} else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
				actualMoveKnight(xC1, yC1, a, b, move);
			} else {
				throw new IllegalMoveException("Mossa non riconosciuta.");
			}
		} else if (count == 2) {
			if (move.charAt(1) == 'x') {
				captureKnight(xC2, yC2, a, b, move);
			} else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
				actualMoveKnight(xC2, yC2, a, b, move);
			} else {
				throw new IllegalMoveException("Mossa non riconosciuta.");
			}
		} else if (count == 3) {
			if (move.charAt(1) == 'x') {
				throw new IllegalMoveException(
						"Mossa ambigua, devi specificare quale dei due cavalli muovere secondo la notazione algebrica.");
			}
			if (move.length() == 3) {
				throw new IllegalMoveException(
						"Mossa ambigua, devi specificare quale dei due cavalli muovere secondo la notazione algebrica.");
			}

			int x, y;
			if (move.charAt(1) >= '1' && move.charAt(1) <= '8') {
				if (xC1 == xC2) {
					throw new IllegalMoveException(
							"Quando i due cavalli si trovano sulla stessa riga e' necessario specificare la colonna!");
				}
				if (xC1 == (8 - Integer.parseInt(move.substring(1, 2)))) {
					x = xC1;
					y = yC1;
				} else if (xC2 == (8 - Integer.parseInt(move.substring(1, 2)))) {
					x = xC2;
					y = yC2;
				} else {
					throw new IllegalMoveException(
							"Nessun cavallo appartenente alla riga di disambiguazione specificata.");
				}
				if (move.length() == 4) {
					actualMoveKnight(x, y, a, b, move);
				} else if (move.length() == 5) {
					captureKnight(x, y, a, b, move);
				} else {
					throw new IllegalMoveException("Mossa non riconosciuta.");
				}
			} else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
				if (yC1 == yC2) {
					throw new IllegalMoveException(
							"Quando i due cavalli si trovano sulla stessa colonna e' necessario specificare la riga!");
				}
				if (yC1 == ((int) move.charAt(1) - 97)) {
					x = xC1;
					y = yC1;
				} else if (yC2 == ((int) move.charAt(1) - 97)) {
					x = xC2;
					y = yC2;
				} else {
					throw new IllegalMoveException(
							"Nessun cavallo appartenente alla colonna di disambiguazione specificata.");
				}
				if (move.length() == 4) {
					actualMoveKnight(x, y, a, b, move);
				} else if (move.length() == 5) {
					captureKnight(x, y, a, b, move);
				} else {
					throw new IllegalMoveException("Mossa non riconosciuta.");
				}
			} else {
				throw new IllegalMoveException("Mossa non riconosciuta.");
			}
		}
	}

	void actualMoveKnight(int xC, int yC, int x, int y, String move) throws IllegalMoveException {
		if (board[x][y].getPiece() != null && board[x][y].getPiece().getColor() == (whiteTurn ? 1 : 0)) {
			throw new IllegalMoveException(
					"Mossa non valida, devi specificare la cattura come da notazione algebrica.");
		}
		board[x][y].setPiece(board[xC][yC].getPiece());
		board[xC][yC].setEmpty();
		movesDone.add(move);
		whiteTurn = !whiteTurn;
		System.out.println(board[x][y].getPiece().getType() + " spostato su " + (char) (y + 97) + (8 - x));
	}

	void captureKnight(int xC, int yC, int x, int y, String move) throws IllegalMoveException {
		if (board[x][y].getPiece() == null) {
			throw new IllegalMoveException("Mossa non valida, non c'e' nessun pezzo da catturare.");
		}
		System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: "
				+ board[xC][yC].getPiece().getType() + " in " + move.substring(2, 4));
		if (board[x][y].getPiece().getColor() == 0) {
			WhitesCaptured.add(board[x][y].getPiece());
		} else {
			BlacksCaptured.add(board[x][y].getPiece());
		}
		board[x][y].setPiece(board[xC][yC].getPiece());
		board[xC][yC].setEmpty();
		movesDone.add(move);
		whiteTurn = !whiteTurn;
		System.out.println(board[x][y].getPiece().getType() + " spostato su " + (char) (y + 97) + (8 - x));
	}

	boolean isMovableRook(int x, int y, int a, int b) {
		int i;

		if (x == a && y == b)
			return false;

		if (x == a) { // controllo orizzontale
			int dx = (y < b) ? 1 : -1;

			for (i = y + dx; i != b; i += dx)
				if (board[x][i].getPiece() != null)
					return false;
		} else if (y == b) { // in verticale
			int dy = (x < a) ? 1 : -1;

			for (i = x + dy; i != a; i += dy)
				if (board[i][y].getPiece() != null)
					return false;
		} else { // Non valido
			return false;
		}

		// Return true
		return true;
	}

	void moveRook(String move) throws IllegalMoveException {
		int count = 0;
		int xC1 = -1, yC1 = -1, xC2 = -1, yC2 = -1;
		int a = 8 - Integer.parseInt(move.substring(move.length() - 1));
		int b = (int) move.charAt(move.length() - 2) - 97;
		if (board[a][b].getPiece() != null && board[a][b].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
			throw new IllegalMoveException("Non puoi spostarti sulla cella di un alleato.");
		}
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (board[i][j].getPiece() instanceof Rook
						&& board[i][j].getPiece().getColor() != (whiteTurn ? 1 : 0)) {
					if (xC1 == -1) {
						xC1 = i;
						yC1 = j;
					} else {
						xC2 = i;
						yC2 = j;
					}
				}
			}
		}
		if (xC1 != -1 && yC1 != -1) {
			if (isMovableRook(xC1, yC1, a, b)) {
				count = count + 1;
			}
		}
		if (xC2 != -1 && yC2 != -1) {
			if (isMovableRook(xC2, yC2, a, b)) {
				count = count + 2;
			}
		}

		if (count == 0) {
			throw new IllegalMoveException("Nessuna torre puo' spostarsi in quella cella.");
		}

		if (count == 1) {
			if (move.charAt(1) == 'x') {
				captureRook(xC1, yC1, a, b, move);
			} else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
				actualMoveRook(xC1, yC1, a, b, move);
			} else {
				throw new IllegalMoveException("Mossa non riconosciuta.");
			}
		} else if (count == 2) {
			if (move.charAt(1) == 'x') {
				captureRook(xC2, yC2, a, b, move);
			} else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
				actualMoveRook(xC2, yC2, a, b, move);
			} else {
				throw new IllegalMoveException("Mossa non riconosciuta.");
			}
		} else if (count == 3) {
			if (move.charAt(1) == 'x') {
				throw new IllegalMoveException(
						"Mossa ambigua, devi specificare quale delle due torri muovere secondo la notazione algebrica.");
			}
			if (move.length() == 3) {
				throw new IllegalMoveException(
						"Mossa ambigua, devi specificare quale delle due torri muovere secondo la notazione algebrica.");
			}

			int x, y;
			if (move.charAt(1) >= '1' && move.charAt(1) <= '8') {
				if (xC1 == xC2) {
					throw new IllegalMoveException(
							"Quando le due torri si trovano sulla stessa riga e' necessario specificare la colonna!");
				}
				if (xC1 == (8 - Integer.parseInt(move.substring(1, 2)))) {
					x = xC1;
					y = yC1;
				} else if (xC2 == (8 - Integer.parseInt(move.substring(1, 2)))) {
					x = xC2;
					y = yC2;
				} else {
					throw new IllegalMoveException(
							"Nessuna torre appartenente alla riga di disambiguazione specificata.");
				}
				if (move.length() == 4) {
					actualMoveRook(x, y, a, b, move);
				} else if (move.length() == 5) {
					captureRook(x, y, a, b, move);
				} else {
					throw new IllegalMoveException("Mossa non riconosciuta.");
				}
			} else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
				if (yC1 == yC2) {
					throw new IllegalMoveException(
							"Quando le due torri si trovano sulla stessa colonna e' necessario specificare la riga!");
				}
				if (yC1 == ((int) move.charAt(1) - 97)) {
					x = xC1;
					y = yC1;
				} else if (yC2 == ((int) move.charAt(1) - 97)) {
					x = xC2;
					y = yC2;
				} else {
					throw new IllegalMoveException(
							"Nessuna torre appartenente alla colonna di disambiguazione specificata.");
				}
				if (move.length() == 4) {
					actualMoveRook(x, y, a, b, move);
				} else if (move.length() == 5) {
					captureRook(x, y, a, b, move);
				} else {
					throw new IllegalMoveException("Mossa non riconosciuta.");
				}
			} else {
				throw new IllegalMoveException("Mossa non riconosciuta.");
			}
		}
	}

	void actualMoveRook(int xC, int yC, int x, int y, String move) throws IllegalMoveException {
		if (board[x][y].getPiece() != null && board[x][y].getPiece().getColor() == (whiteTurn ? 1 : 0)) {
			throw new IllegalMoveException(
					"Mossa non valida, devi specificare la cattura come da notazione algebrica.");
		}
		board[x][y].setPiece(board[xC][yC].getPiece());
		board[xC][yC].setEmpty();
		movesDone.add(move);
		((Rook) board[x][y].getPiece()).incrementMoves();
		whiteTurn = !whiteTurn;
		System.out.println(board[x][y].getPiece().getType() + " spostato su " + (char) (y + 97) + (8 - x));
	}

	void captureRook(int xC, int yC, int x, int y, String move) throws IllegalMoveException {
		if (board[x][y].getPiece() == null) {
			throw new IllegalMoveException("Mossa non valida, non c'e' nessun pezzo da catturare.");
		}
		System.out.println(board[x][y].getPiece().getType() + " e' stato catturato da: "
				+ board[xC][yC].getPiece().getType() + " in " + move.substring(2, 4));
		if (board[x][y].getPiece().getColor() == 0) {
			WhitesCaptured.add(board[x][y].getPiece());
		} else {
			BlacksCaptured.add(board[x][y].getPiece());
		}
		board[x][y].setPiece(board[xC][yC].getPiece());
		board[xC][yC].setEmpty();
		movesDone.add(move);
		((Rook) board[x][y].getPiece()).incrementMoves();
		whiteTurn = !whiteTurn;
		System.out.println(board[x][y].getPiece().getType() + " spostato su " + (char) (y + 97) + (8 - x));
	}

	void shortCastling() throws IllegalMoveException {
		if (whiteTurn == true) {
			if ((board[7][4].getPiece() instanceof King) && (board[7][7].getPiece() instanceof Rook)) {
				// controllo che re e torre siano nella posizione corretta
				King k = (King) board[7][4].getPiece();
				Rook r = (Rook) board[7][7].getPiece();
				if ((k.getNumberOfMoves() == 0) && (r.getNumberOfMoves() == 0)) { // controllo che non siano stati
																					// ancora mossi

					if ((board[7][5].getPiece() == null) && (board[7][6].getPiece() == null)) { // controllo se il
																								// percorso e' libero
						if ((King.isThreatened(board, whiteTurn, 7, 4)) || (King.isThreatened(board, whiteTurn, 7, 5))
						// controllo che il re non e', e non finisce sotto scacco durante la mossa
								|| (King.isThreatened(board, whiteTurn, 7, 6))) {
							throw new IllegalMoveException(
									"Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
						}
						k.incrementMoves();
						r.incrementMoves();
						board[7][6].setPiece(k);
						board[7][5].setPiece(r);
						board[7][4].setEmpty();
						board[7][7].setEmpty();
						movesDone.add("0-0");
						System.out.println("Arrocco corto eseguito");
						whiteTurn = false;
					} else {
						throw new IllegalMoveException("Mossa illegale; il percorso non e' libero");
					}

				} else {
					throw new IllegalMoveException(
							"Mossa illegale; Il re o la torre sono gia' stati mossi in precedenza");
				}

			} else {
				throw new IllegalMoveException(
						"Mossa illegale; Impossibile effettuare arrocco corto, Re e torre non sono nella posizione iniziale");
			}
		} else {
			if ((board[0][4].getPiece() instanceof King) && (board[0][7].getPiece() instanceof Rook)) {
				// controllo che re e torre siano nella posizione corretta
				King k = (King) board[0][4].getPiece();
				Rook r = (Rook) board[0][7].getPiece();
				if ((k.getNumberOfMoves() == 0) && (r.getNumberOfMoves() == 0)) { // controllo che non siano stati
																					// ancora mossi

					if ((board[0][5].getPiece() == null) && (board[0][6].getPiece() == null)) {
						if ((King.isThreatened(board, whiteTurn, 0, 4)) || (King.isThreatened(board, whiteTurn, 0, 5))
								|| (King.isThreatened(board, whiteTurn, 0, 6))) { // controllo che il re non e', e non
																					// finisce sotto scacco durante la
																					// mossa
							throw new IllegalMoveException(
									"Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
						}
						// controllo se il percorso e' libero
						k.incrementMoves();
						r.incrementMoves();
						board[0][6].setPiece(k);
						board[0][5].setPiece(r);
						board[0][4].setEmpty();
						board[0][7].setEmpty();
						movesDone.add("0-0");
						System.out.println("Arrocco corto eseguito");
						whiteTurn = true;
					} else {
						throw new IllegalMoveException("Mossa illegale; il percorso non e' libero");
					}

				} else {
					throw new IllegalMoveException(
							"Mossa illegale; Il re o la torre sono gia'  stati mossi in precedenza");
				}

			} else {
				throw new IllegalMoveException(
						"Mossa illegale; Impossibile effettuare arrocco corto, Re e torre non sono nella posizione iniziale");
			}
		}

	}

	void longCastling() throws IllegalMoveException {
		if (whiteTurn == true) {
			if ((board[7][4].getPiece() instanceof King) && (board[7][0].getPiece() instanceof Rook)) {
				// controllo che re e torre siano nella posizione corretta
				King k = (King) board[7][4].getPiece();
				Rook r = (Rook) board[7][0].getPiece();
				if ((k.getNumberOfMoves() == 0) && (r.getNumberOfMoves() == 0)) { // controllo che non siano stati
																					// ancora mossi

					if ((board[7][3].getPiece() == null) && (board[7][2].getPiece() == null)) {
						if ((King.isThreatened(board, whiteTurn, 7, 4)) || (King.isThreatened(board, whiteTurn, 7, 3))
						// controllo che il re non e', e non finisce sotto scacco durante la mossa
								|| (King.isThreatened(board, whiteTurn, 7, 2))) {
							throw new IllegalMoveException(
									"Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
						}
						// controllo se il percorso e' libero
						k.incrementMoves();
						r.incrementMoves();
						board[7][2].setPiece(k);
						board[7][3].setPiece(r);
						board[7][4].setEmpty();
						board[7][0].setEmpty();
						System.out.println("Arrocco lungo eseguito");
						movesDone.add("0-0-0");
						whiteTurn = false;
					} else {
						throw new IllegalMoveException("Mossa illegale; il percorso non e' libero");
					}

				} else {
					throw new IllegalMoveException(
							"Mossa illegale; Il re o la torre sono gia' stati mossi in precedenza");
				}

			} else {
				throw new IllegalMoveException(
						"Mossa illegale; Impossibile effettuare arrocco lungo, Re e torre non sono nella posizione iniziale");
			}
		} else {
			if ((board[0][4].getPiece() instanceof King) && (board[0][0].getPiece() instanceof Rook)) {
				// controllo che re e torre siano nella posizione corretta
				King k = (King) board[0][4].getPiece();
				Rook r = (Rook) board[0][0].getPiece();
				if ((k.getNumberOfMoves() == 0) && (r.getNumberOfMoves() == 0)) { // controllo che non siano stati
																					// ancora mossi

					if ((board[0][3].getPiece() == null) && (board[0][2].getPiece() == null)) {
						if ((King.isThreatened(board, whiteTurn, 0, 4)) || (King.isThreatened(board, whiteTurn, 0, 3))
								|| (King.isThreatened(board, whiteTurn, 0, 2))) { // controllo che il re non e', e non
																					// finisce sotto scacco durante la
																					// mossa
							throw new IllegalMoveException(
									"Mossa illegale; Il re e' sotto scacco, o finirebbe sotto scacco effettuando l'arrocco");
						}
						// controllo se il percorso e' libero
						k.incrementMoves();
						r.incrementMoves();
						board[0][2].setPiece(k);
						board[0][3].setPiece(r);
						board[0][4].setEmpty();
						board[0][0].setEmpty();
						movesDone.add("0-0-0");
						System.out.println("Arroco lungo eseguito");
						whiteTurn = true;
					} else {
						throw new IllegalMoveException("Mossa illegale; il percorso non e' libero");
					}

				} else {
					throw new IllegalMoveException(
							"Mossa illegale; Il re o la torre sono gia'  stati mossi in precedenza");
				}

			} else {
				throw new IllegalMoveException(
						"Mossa illegale; Impossibile effettuare arrocco lungo, Re e torre non sono nella posizione iniziale");
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