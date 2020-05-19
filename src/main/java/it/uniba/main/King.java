package it.uniba.main;

/**
 * <<entity>><br>
 * King class, implementing the abstract class {@link Piece}<br>
 * Includes a method to verify the checkmate event.
 *
 * @author Donato Lucente
 * @author Filippo Iacobellis
 */
class King extends Piece {
	private static int coordBlackKing[] = new int[]{0,4}; // coordinate re nero, [0]=x [1]=y
	private static int coordWhiteKing[] = new int[]{7,4}; // coordinate re bianco, [0]=x [1]=y
	King(int col) {

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265A"; // Re nero
			nMoves = 0;

		} else if (col == 1) {
			this.pieceType = "\u2654"; // Re bianco
			nMoves = 0;

		} else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");
	}

	void incrementMoves() {
		nMoves++;
	}

	int getNumberOfMoves() {
		return this.nMoves;
	}

	static boolean isThreatened() {
		int x,y;
		if(Game.getBlackTurn()) {
			x= coordBlackKing[0];
			y= coordBlackKing[1];
		}else {
			x= coordWhiteKing[0];
			y= coordWhiteKing[1];
		}
		return isThreatened(x,y);
	}

	static boolean isThreatened(int x, int y) {
		int i, j;
		Piece checkPiece;
		boolean blackTurn= Game.getBlackTurn();
		if (blackTurn) {
			if (x < 7) {
				checkPiece=Game.getCell(x + 1, y - 1).getPiece();
				if (y > 0
						&& checkPiece instanceof Pawn
						&& checkPiece.getColor() == 1) {
					return true;
				}
				checkPiece=Game.getCell(x + 1, y + 1).getPiece();
				if (y < 7
						&& checkPiece instanceof Pawn
						&& checkPiece.getColor() == 1) {
					return true;
				}
			}
		}
		if (!blackTurn) {
			if (x > 0) {
				checkPiece=Game.getCell(x - 1, y - 1).getPiece();
				if (y > 0
						&& checkPiece instanceof Pawn
						&& checkPiece.getColor() == 0) {
					return true;
				}
				checkPiece=Game.getCell(x - 1, y + 1).getPiece();
				if (y < 7
						&& checkPiece instanceof Pawn
						&& checkPiece.getColor() == 0) {
					return true;
				}
			}
		}
		// up right
		i = 1;
		j = 1;
		while (x - i >= 0 && y + j <= 7) {
			checkPiece= Game.getCell(x - i, y + j).getPiece();
			if (checkPiece == null) {
				i++;
				j++;
				continue;
			}
			if (checkPiece.getColor() != (blackTurn ? 1 : 0)
					|| checkPiece instanceof Pawn) break;
			if (checkPiece instanceof Bishop
					|| checkPiece instanceof Queen) return true;
			i++;
			j++;
		}
		// down right
		i = 1;
		j = 1;
		while (x + i <= 7 && y + j <= 7) {
			checkPiece= Game.getCell(x + i, y + j).getPiece();
			if (checkPiece == null) {
				i++;
				j++;
				continue;
			}
			if (checkPiece.getColor() != (blackTurn ? 1 : 0)
					|| checkPiece instanceof Pawn) break;
			if (checkPiece instanceof Bishop
					|| checkPiece instanceof Queen) return true;
			i++;
			j++;
		}
		// down left
		i = 1;
		j = 1;
		while (x + i <= 7 && y - j >= 0) {
			checkPiece= Game.getCell(x + i, y - j).getPiece();
			if (checkPiece == null) {
				i++;
				j++;
				continue;
			}
			if (checkPiece.getColor() != (blackTurn ? 1 : 0)
					|| checkPiece instanceof Pawn) break;
			if (checkPiece instanceof Bishop
					|| checkPiece instanceof Queen) return true;
			i++;
			j++;
		}
		// up left
		i = 1;
		j = 1;
		while (x - i >= 0 && y - j >= 0) {
			checkPiece= Game.getCell(x - i, y - j).getPiece();
			if (checkPiece == null) {
				i++;
				j++;
				continue;
			}
			if (checkPiece.getColor() != (blackTurn ? 1 : 0)
					|| checkPiece instanceof Pawn) break;
			if (checkPiece instanceof Bishop
					|| checkPiece instanceof Queen) return true;
			i++;
			j++;
		}

		// right
		j = 1;
		while (y + j <= 7) {
			checkPiece= Game.getCell(x, y + j).getPiece();
			if (checkPiece == null) {
				i++;
				j++;
				continue;
			}
			if (checkPiece.getColor() != (blackTurn ? 1 : 0)
					|| checkPiece instanceof Pawn) break;
			if (checkPiece instanceof Rook || checkPiece instanceof Queen)
				return true;
			j++;
		}
		// down
		i = 1;
		while (x + i <= 7) {
			checkPiece= Game.getCell(x + i, y).getPiece();
			if (checkPiece == null) {
				i++;
				j++;
				continue;
			}
			if (checkPiece.getColor() != (blackTurn ? 1 : 0)
					|| checkPiece instanceof Pawn) break;
			if (checkPiece instanceof Rook || checkPiece instanceof Queen)
				return true;
			i++;
		}
		// left
		j = 1;
		while (y - j >= 0) {
			checkPiece= Game.getCell(x, y - j).getPiece();
			if (checkPiece == null) {
				i++;
				j++;
				continue;
			}
			if (checkPiece.getColor() != (blackTurn ? 1 : 0)
					|| checkPiece instanceof Pawn) break;
			if (checkPiece instanceof Rook || checkPiece instanceof Queen)
				return true;
			j++;
		}
		// up
		i = 1;
		while (x - i >= 0) {
			checkPiece= Game.getCell(x - i, y).getPiece();
			if (checkPiece == null) {
				i++;
				j++;
				continue;
			}
			if (checkPiece.getColor() != (blackTurn ? 1 : 0)
					|| checkPiece instanceof Pawn) break;
			if (checkPiece instanceof Rook || checkPiece instanceof Queen)
				return true;
			i++;
		}
		// knight
		if (y + 2 <= 7) {
			if (x - 1 >= 0
					&& Game.getCell(x - 1, y + 2).getPiece() instanceof Knight
					&& Game.getCell(x - 1, y + 2).getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
			if (x + 1 <= 7
					&& Game.getCell(x + 1, y + 2).getPiece() instanceof Knight
					&& Game.getCell(x + 1, y + 2).getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
		}
		if (y + 1 <= 7) {
			if (x - 2 >= 0
					&& Game.getCell(x - 2, y + 1).getPiece() instanceof Knight
					&& Game.getCell(x - 2, y + 1).getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
			if (x + 2 <= 7
					&& Game.getCell(x + 2, y + 1).getPiece() instanceof Knight
					&& Game.getCell(x + 2, y + 1).getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
		}
		if (y - 1 >= 0) {
			if (x - 2 >= 0
					&& Game.getCell(x - 2, y - 1).getPiece() instanceof Knight
					&& Game.getCell(x - 2, y - 1).getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
			if (x + 2 <= 7
					&& Game.getCell(x + 2, y - 1).getPiece() instanceof Knight
					&& Game.getCell(x + 2, y - 1).getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
		}
		if (y - 2 >= 0) {
			if (x - 1 >= 0
					&& Game.getCell(x - 1, y - 2).getPiece() instanceof Knight
					&& Game.getCell(x - 1, y - 2).getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
			if (x + 1 <= 7
					&& Game.getCell(x + 1, y - 2).getPiece() instanceof Knight
					&& Game.getCell(x + 1, y - 2).getPiece().getColor() == (blackTurn ? 1 : 0)) return true;
		}
		return false;
	}
	static String[] move(String move) throws IllegalMoveException {
		int x = 2;
		int y = 1;
		boolean blackTurn=Game.getBlackTurn();
		// pezzi da ritornare a fine esecuzione
		String printOut[] = new String[3];

		if (move.length() == 4) {
			x = 3;
			y = 2;
		}
		//cella di destinazione da ritornare, parti da colonna specificata e tagli fino alla fine della stringa
		printOut[2]=move.substring(y);
		y = (int) move.charAt(y) - 97;
		x = 8 - Integer.parseInt(move.substring(x, x + 1));

		if (Game.getCell(x,y).getPiece() != null
				&& Game.getCell(x,y).getPiece().getColor() != (blackTurn ? 1 : 0)) {
			throw new IllegalMoveException("Non puoi spostarti sulla cella di un alleato.");
		}
		int xK = -1;
		int yK = -1;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (Game.getCell(i,j).getPiece() instanceof King
						&& Game.getCell(i,j).getPiece().getColor() != (blackTurn ? 1 : 0)) {
					xK = i;
					yK = j;
					break;
				}
			}
		}
		if (Math.abs(x - xK) > 1 || Math.abs(y - yK) > 1) {
			throw new IllegalMoveException("Il Re non puo' muoversi in quella cella");
		}
		if (isThreatened(x,y)) {
			throw new IllegalMoveException("Mossa illegale, metterebbe il Re sotto scacco");
		}

		// null valore standard perche' non sappiamo se e' una cattura o meno
		printOut[0] = Game.getCell(xK,yK).getPiece().toString();
		if (Game.getCell(x,y).getPiece() == null) {
			if (move.charAt(1) == 'x') {
				throw new IllegalMoveException(
						"Mossa illegale, non c'e' nessun pezzo da catturare nella cella di arrivo");
			}
			printOut[0] = Game.getCell(xK,yK).getPiece().getType();
		} else {
			if (move.charAt(1) != 'x') {
				throw new IllegalMoveException(
						"Mossa illegale, devi specificare la cattura come da notazione algebrica");
			}
			if (blackTurn) {
				Game.addBlackCaptured(Game.getCell(x,y).getPiece().toString());
			} else {
				Game.addWhiteCaptured(Game.getCell(x,y).getPiece().toString());
			}
			printOut[1] = Game.getCell(x,y).getPiece().getType();
		}
		Game.getCell(x,y).setPiece(Game.getCell(xK,yK).getPiece());
		((King) Game.getCell(x,y).getPiece()).incrementMoves();
		Game.getCell(xK,yK).setEmpty();
		// imposta le nuove coordinate del king
		if (blackTurn) {
			coordBlackKing[0] = x;
			coordBlackKing[1] = y;
		} else {
			coordWhiteKing[0] = x;
			coordWhiteKing[1] = y;
		}
		return printOut;
	}
}
