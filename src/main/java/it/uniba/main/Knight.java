package it.uniba.main;

/**
 * <<entity>><br>
 * Knight class, implementing the abstract class {@link Piece}<br>
 *
 * @author Donato Lucente
 */
class Knight extends Piece {

	Knight(int col) {

		this.color = col;
		if (col == 0) {
			this.pieceType = "\u265E"; // Cavallo nero

		} else if (col == 1) {
			this.pieceType = "\u2658"; // Cavallo bianco

		} else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");
	}

	private static boolean isMovable(int x, int y, int a, int b) {
		if ((Math.abs(x - a) == 1 && Math.abs(y - b) == 2)
				|| (Math.abs(y - b) == 1 && Math.abs(x - a) == 2)) {
			return true;
		}
		return false;
	}

	static String[] move(String move) throws IllegalMoveException {
		int count = 0;
		int xC1 = -1;
		int yC1 = -1;
		int xC2 = -1;
		int yC2 = -1;
		//coordinate del cavallo scelto da muovere
		int xTarget=-1;
		int yTarget=-1;
		boolean isCapture=false;
		boolean blackTurn= Game.getBlackTurn();

		int a = 8 - Integer.parseInt(move.substring(move.length() - 1));
		int b = (int) move.charAt(move.length() - 2) - 97;

		if((move.length()==4 && move.charAt(1)== 'x') || (move.length()==5 && move.charAt(2)== 'x')) {
			isCapture=true;
		}

		if (Game.getCell(a,b).getPiece() != null) {
			//lancia eccezione se la cella di destinazione è occupata da alleato
			if(Game.getCell(a, b).getPiece().getColor() == (blackTurn ? 0 : 1)) 
				throw new IllegalMoveException("Mossa illegale; Non puoi spostarti sulla cella di un alleato");

			//o se è una mossa di spostamento con cella di destinazione occupata da avversario
			else if(Game.getCell(a, b).getPiece().getColor() != (blackTurn ? 0 : 1) && !isCapture)
				throw new IllegalMoveException("Mossa illegale; La cella di destinazione non e' vuota");

			//o se è una mossa di cattura con cella di destinazione vuota
		}else if(Game.getCell(a,b).getPiece() == null && isCapture)
			throw new IllegalMoveException("Mossa illegale; La cella di destinazione e' vuota");

		//provo a trovare entrambi i cavalli nella scacchiera
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (Game.getCell(i, j).getPiece() instanceof Knight
						&& Game.getCell(i, j).getPiece().getColor() == (blackTurn ? 0 : 1)) {
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
			if (isMovable(xC1, yC1, a, b)) {
				count = count + 1;
			}
		}
		if (xC2 != -1 && yC2 != -1) {
			if (isMovable(xC2, yC2, a, b)) {
				count = count + 2;
			}
		}
		if (count == 0) {
			throw new IllegalMoveException("Nessun cavallo puo' spostarsi in quella cella.");
		}

		if (count == 1) {
			xTarget=xC1;
			yTarget=yC1;
		} else if (count == 2) {
			xTarget=xC2;
			yTarget=yC2;
		}else if (count == 3) {
			if (move.length() == 3) {
				throw new IllegalMoveException(
						"Mossa ambigua, devi specificare quale dei due cavalli muovere secondo la notazione algebrica.");
			}
			if (move.charAt(1) >= '1' && move.charAt(1) <= '8') {
				if (xC1 == xC2) {
					throw new IllegalMoveException(
							"Quando i due cavalli si trovano sulla stessa riga e' necessario specificare la colonna!");
				}
				if (xC1 == (8 - Integer.parseInt(move.substring(1, 2)))) {
					xTarget = xC1;
					yTarget = yC1;
				} else if (xC2 == (8 - Integer.parseInt(move.substring(1, 2)))) {
					xTarget = xC2;
					yTarget = yC2;
				} else {
					throw new IllegalMoveException(
							"Nessun cavallo appartenente alla riga di disambiguazione specificata.");
				}
			} else if (move.charAt(1) >= 'a' && move.charAt(1) <= 'h') {
				if (yC1 == yC2) {
					throw new IllegalMoveException(
							"Quando i due cavalli si trovano sulla stessa colonna e' necessario specificare la riga!");
				}
				if (yC1 == ((int) move.charAt(1) - 97)) {
					xTarget = xC1;
					yTarget = yC1;
				} else if (yC2 == ((int) move.charAt(1) - 97)) {
					xTarget = xC2;
					yTarget = yC2;
				} else {
					throw new IllegalMoveException(
							"Nessun cavallo appartenente alla colonna di disambiguazione specificata.");
				}
			} else {
				throw new IllegalMoveException(
						"Mossa ambigua, devi specificare quale dei due cavalli muovere secondo la notazione algebrica.");
			}
		}

		return actualMove(isCapture,xTarget,yTarget,a,b);
	}

	static private String[] actualMove(boolean isCapture, int xC, int yC, int x, int y) throws IllegalMoveException {
		String[] pieces = new String[3];
		Piece target=null;
		if(isCapture) {
			target=Game.getCell(x,y).getPiece();
		}
		Knight k= (Knight) Game.getCell(xC,yC).getPiece();
		Game.getCell(xC,yC).setEmpty();
		Game.getCell(x,y).setPiece(k);
		if (King.isThreatened()) {
			Game.getCell(x,y).setPiece(target);
			Game.getCell(xC,yC).setPiece(k);
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
			pieces[0] = k.toString();
			pieces[2] = ((char)(y+97))+""+(8-x);
			return pieces;
		}
	}
}
