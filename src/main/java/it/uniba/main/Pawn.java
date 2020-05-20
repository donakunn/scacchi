package it.uniba.main;

/**
 * <<entity>><br>
 * Pawn class, implementing the abstract class {@link Piece}<br>
 * Includes a counter for moves done and a method to verify the en-passant capturable condition
 *
 * @author Donato Lucente
 */
class Pawn extends Piece {

	Pawn(int col) { // costruttore classe Pedone
		nMoves = 0;

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265F"; // pedone nero

		} else {
			this.pieceType = "\u2659"; // pedone bianco

		} 
	}

	private void incrementMoves() {
		this.nMoves++;
	}

	private Boolean enPassantCatturable(
			int x) { // restituisce true se il pedone ha effettuato una sola mossa con salto di 2,
		// false altrimenti
		if ((getColor() == 0) && (nMoves == 1) && (x == 4)) {
			return true;
		} else if ((getColor() == 1) && (nMoves == 1) && (x == 3)) {
			return true;
		} else return false;
	}

	static String[] move(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata
		int xCheck; //ordinata su cui fare il check del pezzo
		boolean blackTurn= Game.getBlackTurn();

		y = (int) (move.charAt(0)) - 97; // lettura x e y casella di destinazione
		x = 8 - Integer.parseInt(move.substring(1, 2));

		Pawn p;
		String[] pieceAndCell = new String[3]; //0 pezzo che viene mosso, //2 cella di destinazione
		if (Game.getCell(x,y).getPiece() != null) {
			throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota.");
		}
		if ((x > 0)
				&& (x < 8)
				&& (Game.getCell(x - 1,y).getPiece() instanceof Pawn)
				&& (Game.getCell(x - 1,y).getPiece().getColor() == 0)
				// check se casella in x-1 c'e' pedone con colore 1
				&& blackTurn != false) {
			xCheck=x-1;
		} else if ((x > 1)
				&& (x < 8)
				&& (Game.getCell(x - 2,y).getPiece() instanceof Pawn)
				&& (Game.getCell(x - 2,y).getPiece().getColor() == 0)
				// check se casella in x-2 c'e' pedone con colore 1
				&& (blackTurn != false)
				&& (Game.getCell(x - 2,y).getPiece().getMoves() == 0)) { // se le condizioni sono
			// rispettate fa la mossa
			xCheck=x-2;
		} else if ((x >= 0)
				&& (x < 7)
				&& (Game.getCell(x + 1,y).getPiece() instanceof Pawn)
				&& (Game.getCell(x + 1,y).getPiece().getColor() == 1)
				// check se casella in x+1 c'e' pedone con colore 0
				&& blackTurn != true) { // se le condizioni sono rispettate fa la mossa
			xCheck=x+1;
		} else if ((x >= 0)
				&& (x < 6)
				&& (Game.getCell(x + 2,y).getPiece() instanceof Pawn)
				&& (Game.getCell(x + 2,y).getPiece().getColor() == 1)
				// check se casella in x+2 c'e' pedone con colore 1
				&& (blackTurn != true)
				&& (Game.getCell(x + 2,y).getPiece().getMoves() == 0)) { // se le condizioni sono
			// rispettate fa la mossa
			xCheck=x+2;
		} else {
			throw new IllegalMoveException("Mossa illegale; Nessun pedone puo' spostarsi qui");
		}
		//abbiamo il pawn target, facciamo i controlli su di lui
		p = (Pawn) Game.getCell(xCheck,y).getPiece();
		Game.getCell(xCheck,y).setEmpty();
		Game.getCell(x,y).setPiece(p);
		if (King.isThreatened()) {
			Game.getCell(xCheck,y).setPiece(p);
			Game.getCell(x,y).setEmpty();
			throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
		}
		p.incrementMoves();
		pieceAndCell[0] = p.toString();
		pieceAndCell[2] = move;
		return pieceAndCell;
	}

	static String[] capture(String move) throws IllegalMoveException {
		int x; // ascissa
		int y; // ordinata
		int z; // colonna del pezzo di provenienza
		int xCheck, yCheck; //coordinate target check
		boolean blackTurn= Game.getBlackTurn();

		Pawn p;
		Piece caught;
		String[] pieces = new String[3]; // 0 pezzo che cattura, 1 pezzo catturato, 2 cella di destinazione

		y = (int) (move.charAt(2)) - 97;
		x =
				8
				- Integer.parseInt(
						move.substring(
								3, 4)); // calcolo x,y di cella di destinazione e z colonna di partenza
		z = (int) (move.charAt(0)) - 97;

		if (Game.getCell(x, y).getPiece()
				== null) { // se cella di destinazione e' vuota prova a fare cattura en passant
			try {
				pieces = captureEnPassant(move);
				return pieces;
			} catch (IllegalMoveException e) {
				throw new IllegalMoveException(e.getMessage());
			}
		}
		if(Math.abs(z-y)>=2||(z-y)==0) {
			throw new IllegalMoveException(
					"Mossa illegale; Nessuna possibile cattura da parte di un pedone a partire dalla colonna indicata");
		}
		if (blackTurn != false) {
			xCheck=x-1;
			if (z == y - 1) {
				yCheck=y-1;
			} else {
				yCheck=y+1;
			}
		} else {
			xCheck=x+1;
			if (z == y - 1) {
				yCheck=y-1;
			} else {
				yCheck=y+1;
			}
		}
		if (Game.getCell(x, y) == null) throw new IllegalMoveException("Mossa illegale. La cella e' vuota.");
		if (!(Game.getCell(xCheck, yCheck).getPiece() instanceof Pawn)) throw new IllegalMoveException(
				"Mossa illegale; Nessun pedone puo' catturare dalla colonna di partenza indicata.");
		p = (Pawn) Game.getCell(xCheck, yCheck).getPiece();
		if (Game.getCell(x, y).getPiece().getColor() != p.getColor()) {
			caught = Game.getCell(x, y).getPiece();
			Game.getCell(x, y).setPiece(p);
			Game.getCell(xCheck, yCheck).setEmpty();
			if (King.isThreatened()) {
				Game.getCell(x, y).setPiece(caught);
				Game.getCell(xCheck, yCheck).setPiece(p);
				throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
			} else {
				if(caught.getColor()==0) {
					Game.addBlackCaptured(caught.toString());
				}else {
					Game.addWhiteCaptured(caught.toString());
				}
				pieces[0] = p.toString();
				pieces[1] = caught.toString();
				pieces[2] = move.substring(2, 4);
				p.incrementMoves();
				return pieces;
			}
		} else
			throw new IllegalMoveException(
					"Mossa illegale; Impossibile catturare pezzo dello stesso colore.");
	}

	static String[] captureEnPassant(String move) throws IllegalMoveException {

		int x; // ascissa
		int y; // ordinata
		int z; // colonna del pezzo di provenienza
		int xCheck, yCheck; //coordinate target check
		boolean blackTurn= Game.getBlackTurn();
		Pawn p;
		String[] pieces = new String[3]; // 0 pezzo catturato, 1 pezzo che cattura //2 cella di destinazione

		y = (int) (move.charAt(2)) - 97;
		x = 8 - Integer.parseInt(move.substring(3, 4));
		z = (int) (move.charAt(0)) - 97;
		if(Math.abs(z-y)>=2||(z-y)==0) {
			throw new IllegalMoveException("Mossa illegale; Nessuna possibile cattura da parte di un pedone a partire dalla colonna indicata");
		}
		if (blackTurn == false) {
			xCheck=x-1;
			if (z == y - 1) {
				yCheck=y-1;
			} else {
				yCheck=y+1;
			}
		} else {
			xCheck=x+1;
			if (z == y - 1) {
				yCheck=y-1;
			} else {
				yCheck=y+1;
			}
		}
		if (!(Game.getCell(xCheck, yCheck).getPiece()
				instanceof Pawn)) throw new IllegalMoveException("Mossa illegale; Nessun pedone puo' effettuare cattura e.p. a partire dalla colonna inserita");
		if (Game.getCell(xCheck, y).getPiece() instanceof Pawn) {
			p = (Pawn) Game.getCell(xCheck, yCheck).getPiece();
			Pawn caught = (Pawn) Game.getCell(xCheck, y).getPiece();
			if ((Game.getCell(x, y) == null) && (caught.enPassantCatturable(xCheck))) {
				Game.getCell(x, y).setPiece(p);
				Game.getCell(xCheck, yCheck).setEmpty();
				Game.getCell(xCheck, y).setEmpty();
				if (King.isThreatened()) {
					Game.getCell(x, y).setEmpty();
					Game.getCell(xCheck, yCheck).setPiece(p);
					Game.getCell(xCheck, y).setPiece(caught);
					throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
				} else {
					if(caught.getColor()==0) {
						Game.addBlackCaptured(caught.toString());
					}else {
						Game.addWhiteCaptured(caught.toString());
					}
					pieces[0] = p.toString();
					pieces[1] = caught.toString();
					pieces[2] = move.substring(2, 4) + " e.p." ;
					p.incrementMoves();
					return pieces;
				}
			} else
				throw new IllegalMoveException(
						"Mossa illegale; Pezzo non catturabile con e.p. o cella di destinazione occupata");
		} else
			throw new IllegalMoveException(
					"Mossa illegale; Nessun pedone catturabile e.p. alla posizione indicata.");
	}
}
