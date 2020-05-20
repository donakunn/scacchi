package it.uniba.main;

/**
 * <<entity>><br>
 * Queen class, implementing the abstract class {@link Piece}<br>
 *
 * @author Donato Lucente
 */
class Queen extends Piece {

	Queen(int col) {

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265B"; // Regina nera

		} else if (col == 1) {
			this.pieceType = "\u2655"; // Regina bianca

		} else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");
	}

	static String[] move(String move) throws IllegalMoveException {
		int x=2; // ascissa
		int y=1; // ordinata
		int xCheck; // sentinella dell'ascissa
		int yCheck; // sentinella dell'ordinata
		boolean blackTurn= Game.getBlackTurn();
		boolean isCapture;
		if (move.length() == 3) {
			isCapture=false;
		} else if ((move.length() == 4) && (move.substring(1, 2).equals("x"))) {
			isCapture=true;
			x=3;
			y=2;
		} else throw new IllegalMoveException("Mossa non consentita per la Donna");

		y = (int) move.charAt(y) - 97;
		x = 8 - Integer.parseInt(move.substring(x, x+1));
		if (Game.getCell(x,y).getPiece() != null) {
			//lancia eccezione se la cella di destinazione è occupata da alleato
			if(Game.getCell(x, y).getPiece().getColor() == (blackTurn ? 0 : 1)) 
				throw new IllegalMoveException("Mossa illegale; Non puoi spostarti sulla cella di un alleato");
			
			//o se è una mossa di spostamento con cella di destinazione occupata da avversario
			else if(Game.getCell(x, y).getPiece().getColor() != (blackTurn ? 0 : 1) && !isCapture)
				throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota");
		
			//o se è una mossa di cattura con cella di destinazione vuota
		}else if(Game.getCell(x,y).getPiece() == null && isCapture)
			throw new IllegalMoveException("Mossa illegale; La cella di destinazione e' vuota");
			
		xCheck = x + 1; // controllo in verticale, verso il basso (della matrice)
		while (xCheck < 8) {
			if ((Game.getCell(xCheck,y).getPiece() instanceof Queen)
					&& (Game.getCell(xCheck,y).getPiece().getColor() == (blackTurn ? 0 : 1))) {
				return actualMove(isCapture,x,y,xCheck,y);
			} else if (Game.getCell(xCheck,y).getPiece() != null) {
				break;
			} else {
				xCheck++;
			}
		}
		xCheck = x - 1;
		while (xCheck >= 0) { // controllo in verticale, verso l'alto (della matrice)
			if ((Game.getCell(xCheck,y).getPiece() instanceof Queen)
					&& (Game.getCell(xCheck,y).getPiece().getColor() == (blackTurn ? 0 : 1))) {
				return actualMove(isCapture,x,y,xCheck,y);
			} else if (Game.getCell(xCheck,y).getPiece() != null) {
				break;
			} else {
				xCheck--;
			}
		}
		yCheck = y + 1; // controllo in orizzontale a destra
		while (yCheck < 8) {
			if ((Game.getCell(x,yCheck).getPiece() instanceof Queen)
					&& (Game.getCell(x,yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
				return actualMove(isCapture,x,y,x,yCheck);
			} else if (Game.getCell(x,yCheck).getPiece() != null) {
				break;
			} else {
				yCheck++;
			}
		}
		yCheck = y - 1;
		while (yCheck >= 0) { // controllo in orizzontale a sinistra
			if ((Game.getCell(x,yCheck).getPiece() instanceof Queen)
					&& (Game.getCell(x,yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
				return actualMove(isCapture,x,y,x,yCheck);
			} else if (Game.getCell(x,yCheck).getPiece() != null) {
				break;
			} else {
				yCheck--;
			}
		}
		xCheck = x - 1;
		yCheck = y - 1;
		while (xCheck >= 0 && yCheck >= 0) { // controllo diagonale alta sinistra
			if ((Game.getCell(xCheck,yCheck).getPiece() instanceof Queen)
					&& (Game.getCell(xCheck,yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
				return actualMove(isCapture,x,y,x,yCheck);
			} else if (Game.getCell(xCheck,yCheck).getPiece() != null) {
				break;
			} else {
				xCheck--;
				yCheck--;
			}
		}
		xCheck = x - 1;
		yCheck = y + 1;
		while (xCheck >= 0 && yCheck < 8) { // controllo diagonale alta destra
			if ((Game.getCell(xCheck,yCheck).getPiece() instanceof Queen)
					&& (Game.getCell(xCheck,yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
				return actualMove(isCapture,x,y,xCheck,yCheck);
			} else if (Game.getCell(xCheck,yCheck).getPiece() != null) {
				break;
			} else {
				xCheck--;
				yCheck++;
			}
		}
		xCheck = x + 1;
		yCheck = y - 1;
		while (xCheck < 8 && yCheck >= 0) { // controllo diagonale bassa sinistra
			if ((Game.getCell(xCheck,yCheck).getPiece() instanceof Queen)
					&& (Game.getCell(xCheck,yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
				return actualMove(isCapture,x,y,xCheck,yCheck);
			} else if (Game.getCell(xCheck,yCheck).getPiece() != null) {
				break;
			} else {
				xCheck++;
				yCheck--;
			}
		}

		xCheck = x + 1;
		yCheck = y + 1;
		while (xCheck < 8 && yCheck < 8) { // controllo diagonale bassa destra
			if ((Game.getCell(xCheck,yCheck).getPiece() instanceof Queen)
					&& (Game.getCell(xCheck,yCheck).getPiece().getColor() == (blackTurn ? 0 : 1))) {
				return actualMove(isCapture,x,y,xCheck,yCheck);
			} else if (Game.getCell(xCheck,yCheck).getPiece() != null) {
				break;
			} else {
				xCheck++;
				yCheck++;
			}
		}
		if(isCapture) {
			throw new IllegalMoveException(
					"Mossa illegale; La donna non puo' effettuare la cattura nella cella di destinazione data");
		}else {
			throw new IllegalMoveException("Mossa illegale; La donna non puo' muoversi qui");
		}
	}

	private static String[] actualMove(boolean isCapture, int x, int y, int xCheck, int yCheck) throws IllegalMoveException{
		//isCapture = true -> mossa di cattura
		//isCapture = false -> mossa di spostamento
		String[] pieces = new String[3]; //0 Donna, 2 cella di dest
		Piece target=null;
		if(isCapture) {
			//update: nell'originale viene fatto il cast a piece, qui lo tolgo
			target=Game.getCell(x,y).getPiece();
		}
		Queen q = (Queen) Game.getCell(xCheck,yCheck).getPiece();
		Game.getCell(xCheck,yCheck).setEmpty();
		Game.getCell(x,y).setPiece(q);
		if (King.isThreatened()) {
			Game.getCell(x,y).setPiece(target);
			Game.getCell(xCheck,yCheck).setPiece(q);
			throw new IllegalMoveException("Mossa illegale; Metterebbe il re sotto scacco");
		} else {
			if(isCapture) {
				if(target.getColor()==0) {
					Game.addBlackCaptured(target.toString());
				}else {
					Game.addWhiteCaptured(target.toString());
				}
				pieces[1]=target.toString();
			}else {
				pieces[1]=null;
			}
			pieces[0] = q.toString();
			pieces[2] = ((char)(y+97))+""+(8-x);
			return pieces;
		}
	}
}
