package it.unige.se.battaglianavale;

public class Utils {
	public static enum Direzione {
		SU,
		GIU,
		DESTRA,
		SINISTRA
	}
	
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