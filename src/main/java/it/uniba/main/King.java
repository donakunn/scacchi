package it.uniba.main;

/**
* �entity�<br>
* King class, implementing the abstract class {@link Piece}<br>
* Includes a method to verify the checkmate event.
* 
* @author Donato Lucente
* @author Filippo Iacobellis
* 
*/
class King extends Piece {


	King(int col) {

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265A"; // Re nero
			nMoves = 0;

		} else if (col == 1) {
			this.pieceType = "\u2654"; // Re bianco
			nMoves = 0;

		} else
			throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

	}

	void incrementMoves() {
		nMoves++;
	}

	int getNumberOfMoves() {
		return this.nMoves;
	}

	static boolean isThreatened(Cell[][] board, boolean whiteTurn, int x, int y) {
		int i, j;
		if (!whiteTurn) {
			if (x < 7) {
				if (y > 0 && board[x + 1][y - 1].getPiece() instanceof Pawn
						&& board[x + 1][y - 1].getPiece().getColor() == 0) {
					return true;
				}
				if (y < 7 && board[x + 1][y + 1].getPiece() instanceof Pawn
						&& board[x + 1][y + 1].getPiece().getColor() == 0) {
					return true;
				}
			}
		}
		if (whiteTurn) {
			if (x > 0) {
				if (y > 0 && board[x - 1][y - 1].getPiece() instanceof Pawn
						&& board[x - 1][y - 1].getPiece().getColor() == 1) {
					return true;
				}
				if (y < 7 && board[x - 1][y + 1].getPiece() instanceof Pawn
						&& board[x - 1][y + 1].getPiece().getColor() == 1) {
					return true;
				}
			}
		}
		// up right
		i = 1;
		j = 1;
		while (x - i >= 0 && y + j <= 7) {
			if (board[x - i][y + j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if (board[x - i][y + j].getPiece().getColor() != (whiteTurn ? 1 : 0) || board[x - i][y + j].getPiece() instanceof Pawn)
				break;
			if (board[x - i][y + j].getPiece() instanceof Bishop || board[x - i][y + j].getPiece() instanceof Queen)
				return true;
			i++;
			j++;
		}
		// down right
		i = 1;
		j = 1;
		while (x + i <= 7 && y + j <= 7) {
			if (board[x + i][y + j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if (board[x + i][y + j].getPiece().getColor() != (whiteTurn ? 1 : 0) || board[x + i][y + j].getPiece() instanceof Pawn)
				break;
			if (board[x + i][y + j].getPiece() instanceof Bishop || board[x + i][y + j].getPiece() instanceof Queen)
				return true;
			i++;
			j++;
		}
		// down left
		i = 1;
		j = 1;
		while (x + i <= 7 && y - j >= 0) {
			if (board[x + i][y - j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if (board[x + i][y - j].getPiece().getColor() != (whiteTurn ? 1 : 0) || board[x + i][y - j].getPiece() instanceof Pawn)
				break;
			if (board[x + i][y - j].getPiece() instanceof Bishop || board[x + i][y - j].getPiece() instanceof Queen)
				return true;
			i++;
			j++;
		}
		// up left
		i = 1;
		j = 1;
		while (x - i >= 0 && y - j >= 0) {
			if (board[x - i][y - j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if (board[x - i][y - j].getPiece().getColor() != (whiteTurn ? 1 : 0) || board[x - i][y - j].getPiece() instanceof Pawn)
				break;
			if (board[x - i][y - j].getPiece() instanceof Bishop || board[x - i][y - j].getPiece() instanceof Queen)
				return true;
			i++;
			j++;
		}

		// right
		j = 1;
		while (y + j <= 7) {
			if (board[x][y + j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if (board[x][y + j].getPiece().getColor() != (whiteTurn ? 1 : 0) || board[x][y + j].getPiece() instanceof Pawn)
				break;
			if (board[x][y + j].getPiece() instanceof Rook || board[x][y + j].getPiece() instanceof Queen)
				return true;
			j++;
		}
		// down
		i = 1;
		while (x + i <= 7) {
			if (board[x + i][y].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if (board[x + i][y].getPiece().getColor() != (whiteTurn ? 1 : 0) || board[x + i][y].getPiece() instanceof Pawn)
				break;
			if (board[x + i][y].getPiece() instanceof Rook || board[x + i][y].getPiece() instanceof Queen)
				return true;
			i++;
		}
		// left
		j = 1;
		while (y - j >= 0) {
			if (board[x][y - j].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if (board[x][y - j].getPiece().getColor() != (whiteTurn ? 1 : 0) || board[x][y - j].getPiece() instanceof Pawn)
				break;
			if (board[x][y - j].getPiece() instanceof Rook || board[x][y - j].getPiece() instanceof Queen)
				return true;
			j++;
		}
		// up
		i = 1;
		while (x - i >= 0) {
			if (board[x - i][y].getPiece() == null) {
				i++;
				j++;
				continue;
			}
			if (board[x - i][y].getPiece().getColor() != (whiteTurn ? 1 : 0) || board[x - i][y].getPiece() instanceof Pawn)
				break;
			if (board[x - i][y].getPiece() instanceof Rook || board[x - i][y].getPiece() instanceof Queen)
				return true;
			i++;
		}
		// knight
		if (y + 2 <= 7) {
			if (x - 1 >= 0 && board[x - 1][y + 2].getPiece() instanceof Knight
					&& board[x - 1][y + 2].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
			if (x + 1 <= 7 && board[x + 1][y + 2].getPiece() instanceof Knight
					&& board[x + 1][y + 2].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
		}
		if (y + 1 <= 7) {
			if (x - 2 >= 0 && board[x - 2][y + 1].getPiece() instanceof Knight
					&& board[x - 2][y + 1].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
			if (x + 2 <= 7 && board[x + 2][y + 1].getPiece() instanceof Knight
					&& board[x + 2][y + 1].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
		}
		if (y - 1 >= 0) {
			if (x - 2 >= 0 && board[x - 2][y - 1].getPiece() instanceof Knight
					&& board[x - 2][y - 1].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
			if (x + 2 <= 7 && board[x + 2][y - 1].getPiece() instanceof Knight
					&& board[x + 2][y - 1].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
		}
		if (y - 2 >= 0) {
			if (x - 1 >= 0 && board[x - 1][y - 2].getPiece() instanceof Knight
					&& board[x - 1][y - 2].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
			if (x + 1 <= 7 && board[x + 1][y - 2].getPiece() instanceof Knight
					&& board[x + 1][y - 2].getPiece().getColor() == (whiteTurn ? 1 : 0))
				return true;
		}
		return false;
	}

}
