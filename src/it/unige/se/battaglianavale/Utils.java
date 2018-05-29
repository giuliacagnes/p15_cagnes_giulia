/*
 *  Giulia Cagnes
 *  
 *  Version 0.1 (beta)
 */
package it.unige.se.battaglianavale;

/**
 * classe Utils enumerativa
 * 
 * @version  0.1 22 May 2018
 * @author   Giulia Cagnes
 *
 */
public class Utils {
	/**
	 * Direzione ha 4 possibili valori :SU, GIU, DESTRA E SINISTRA
	 *
	 */
	public static enum Direzione {
		SU,
		GIU,
		DESTRA,
		SINISTRA
	}
	/**
	 * 
	 * StatoCoordinata ha 3 possibili valori : sconosciuto stampato come "."
	 *                                         mancato stampato come     "O"
	 *                                         colpito stampato come     "X"
	 *
	 */
	public static enum StatoCoordinata {
		SCONOSCIUTO {
	        @Override
	        public String toString() {
	            return ".";
	        }
	    },
		MANCATO {
	        @Override
	        public String toString() {
	            return "O";
	        }
	    },
		COLPITO {
	        @Override
	        public String toString() {
	            return "X";
	        }
	    }
	}
}