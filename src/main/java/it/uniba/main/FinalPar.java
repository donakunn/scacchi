package it.uniba.main;

/**
 * <<utility>><br>
 * <p>Titolo: FinalPar</p>
 * <p>Descrizione: La classe FinalPar contiene tutte le costanti che vengono usate all'interno delle altre
 * classi per i vari controlli.
 * e le varie operazioni.</p>
 * 
 * @author Patrick Clark
 */
public final class FinalPar {
	/**
	 * E' il costruttore della classe FinalPar.
	 */
    private FinalPar() {
    }
    /**
     * Numero massimo di righe.
     */
    public static final int MAXROW = 8;
    /**
     * Numero massimo di colonne.
     */
    public static final int MAXCOL = 8;
    /**
     * Limite massimo della scacchiera.
     */
    public static final int OUTOFBOUND = 7;
    /**
     * Lunghezza dell mossa.
     */
    public static final int MOVELENGTH = 2;
    /**
     * Lunghezza della mossa specificando il pezzo da muovere.
     */
    public static final int PIECEMOVELENGTH = 3;
    /**
     * Lunghezza della cattura semplice.
     */
    public static final int CAPTURELENGTH = 4;
    /**
     * Lunghezza della cattura semplice con abiguita'.
     */
    public static final int AMBCAPTLENGTH = 5;
    /**
     * Lunghezza della cattura en passant se si usa la notazione 'e.p.'.
     */
    public static final int ALTEPLENGTH = 8;
    /**
     * Lunghrzza della cattura en passant se si usa la notazione 'ep'.
     */
    public static final int CAPTEPLENGTH = 6;
    /**
     * Prima posizione della stringa equivalente alla mossa o alla cattura.
     */
    public static final int CHARPOS0 = 0;
    /**
     * Seconda posizione della stringa equivalente alla mossa o alla cattura.
     */
    public static final int CHARPOS1 = 1;
    /**
     * Terza posizione della stringa equivalente alla mossa o alla cattura.
     */
    public static final int CHARPOS2 = 2;
    /**
     * Quarta posizione della stringa equivalente alla mossa o alla cattura.
     */
    public static final int CHARPOS3 = 3;
    /**
     * Quinta posizione della stringa equivalente alla mossa o alla cattura.
     */
    public static final int CHARPOS4 = 4;
    /**
     * Settima posizione della stringa equivalente alla mossa o alla cattura.
     */
    public static final int CHARPOS6 = 6;
    /**
     * Nona posizione della stringa equivalente alla mossa o alla cattura.
     */
    public static final int CHARPOS8 = 8;
    /**
     * Prima riga o colonna della scacchiera.
     */
    public static final int POS0 = 0;
    /**
     * Seconda riga o colonna della scacchiera.
     */
    public static final int POS1 = 1;
    /**
     * Terza riga o colonna della scacchiera.
     */
    public static final int POS2 = 2;
    /**
     * Quarta riga o colonna della scacchiera.
     */
    public static final int POS3 = 3;
    /**
     * Quinta riga o colonna della scacchiera.
     */
    public static final int POS4 = 4;
    /**
     * Sesta riga o colonna della scacchiera.
     */
    public static final int POS5 = 5;
    /**
     * Settima riga o colonna della scacchiera.
     */
    public static final int POS6 = 6;
    /**
     * Ottava riga o colonna della scacchiera.
     */
    public static final int POS7 = 7;
    /**
     * Corrisponde alla lettera 'a' in ASCII.
     */
    public static final int AINASCII = 97;
    /**
     * Corrisponde allo 0 in ASCII.
     */
    public static final int DIGIT0INASCII = 48;
    /**
     * Dimensione dell'array di stringhe usati per mosse e catture. 
     */
    public static final int STRARRDIM = 3;
    /**
     * Dimensione dell'array per l'arrocco.
     */
    public static final int CASTLARRDIM = 1;
    /**
     * Ascissa iniziale del Re nero.
     */
    public static final int STARTBKINGX = 0;
    /**
     * Ordinata iniziale del Re nero
     */
    public static final int STARTBKINGY = 4;
    /**
     * Ascissa iniziale del Re bianco.
     */
    public static final int STARTWKINGX = 7;
    /**
     * Ordinata iniziale del Re bianco.
     */
    public static final int STARTWKINGY = 4;
}
