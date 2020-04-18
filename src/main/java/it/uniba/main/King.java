package it.uniba.main;




class King extends Piece {

	
	King(int col) {
		
		this.color= col;
		if (col == 0) {
			this.pieceType = "\u265A"; //Re nero 
			
		}
		else if (col == 1) {
			this.pieceType = "\u2654"; //Re bianco
			
			
		}
		else throw new IllegalArgumentException("Valore non valido, valori accettati: 0,1");

		
	}

	

}
