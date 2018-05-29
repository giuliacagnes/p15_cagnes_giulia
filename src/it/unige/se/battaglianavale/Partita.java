/*
 * Giulia Cagnes
 * 
 *  Verson 0.1 (beta)
 */
package it.unige.se.battaglianavale;

import it.unige.se.battaglianavale.Utils.StatoCoordinata;

/**
 * Classe Partita
 * 
 * @version		0.1 22 May 2018
 * @author 		Giulia Cagnes
 *
 */
public class Partita {
	/** primo giocatore che partecipa alla partita*/
	private Giocatore giocatoreA;
	
	/** secondo giocatore che partecipa alla partita*/
	private Giocatore giocatoreB;
	
	/** giocatore che deve effettuare il turno in partita */
	private Giocatore diTurno;
	
	/**giocatore vincitore della parita di battaglia navale*/
	private Giocatore vincitore;
	
	/**
     * Costruttore di Partita della Battaglia Navale
     *
     * @param giocatoreA
     * @param giocatoreB
     * 
     */
	public Partita(Giocatore giocatoreA,Giocatore giocatoreB) {
		this.giocatoreA = giocatoreA;
		this.giocatoreB = giocatoreB;

		diTurno = giocatoreA;
		vincitore = null;
	}
	/**
	 * Partita e' terminata
	 * 
	 * @return boolean false se o giocatoreA o giocatoreB hanno navi in gioco, 
	 * 		           true altrimenti partita terminata
	 */
	public boolean eTerminata() {
		if(!giocatoreA.haNaviInGioco()) {
			this.vincitore = giocatoreB;
		} else if(!giocatoreB.haNaviInGioco()) {
			this.vincitore = giocatoreA;
		} else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get giocatore diTurno
	 * @return diTurno
	 */
	public Giocatore getDiTurno() {
		return diTurno;
	}
	
	/**
	 * Cambia Turno
	 * @return Giocatore diTurno
	 */
	public Giocatore cambiaTurno() {
		if(diTurno == giocatoreA) {
			diTurno = giocatoreB;
		} else {
			diTurno = giocatoreA;
		}
		return diTurno;
	}
	
	/**
	 * Attacca cella
	 * 
	 * @param x indice di riga
	 * @param y indice di colonna
	 * @return boolean True giocatore di turno aggiorna matrice memoria mosse con stato cordinata colpito
	 *                 False giocatore di turno aggiorna matrice memoria mosse con stato mancato nelle cordinate passate
	 */
	public boolean attacca(int x, int y) {
		Campo campoAvversario = null;
		if(diTurno == giocatoreA)
			campoAvversario = giocatoreB.getCampo();
		else
			campoAvversario = giocatoreA.getCampo();
		
		if(campoAvversario.attaccaCella(x,y)) {
			diTurno.registraMossa(x,y,StatoCoordinata.COLPITO);
			return true;
		}
		diTurno.registraMossa(x,y,StatoCoordinata.MANCATO);
		return false;
	}
	
	/**
	 * Posiziona le navi random per entrambi i giocatori
	 */
	public void posizionaNaviRandom() {
		System.out.println("schacchiere di navi Radom sono state assegnate");
		diTurno.posizionaNaviRandom();
		this.cambiaTurno();
		diTurno.posizionaNaviRandom();
		this.cambiaTurno();
	}
	
	@Override
	public String toString() {
		String ret = new String();
		
		ret += "Giocatore: " + diTurno;
		ret += "\n";
		
		Utils.StatoCoordinata matrix[][] = diTurno.getMemoriaMosse();
		int dim = matrix.length;
		for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[i].length; j++) {
		    	ret += matrix[i][j] + "   ";
		    }
		    ret += " " + i + "\n\n";
		}
		for (int j = 0; j < matrix[dim-1].length; j++) {
	    	ret += j + "   ";
	    }
		ret += "\n";
		
		return ret;
	}
}
