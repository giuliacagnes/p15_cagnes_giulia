/*
 * Giulia Cagnes
 * 
 *  Verson 0.1 (beta)
 */
package it.unige.se.battaglianavale;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unige.se.battaglianavale.Utils.Direzione;
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
		setVincitore(null);
	}
	/**
	 * Partita e' terminata
	 * 
	 * @return boolean false se o giocatoreA o giocatoreB hanno navi in gioco, 
	 * 		           true altrimenti partita terminata
	 */
	public boolean eTerminata() {
		if(!giocatoreA.haNaviInGioco()) {
			this.setVincitore(giocatoreB);
		} else if(!giocatoreB.haNaviInGioco()) {
			this.setVincitore(giocatoreA);
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
	 * @param x indice di colonna
	 * @param y indice di riga
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
	
	/**
	 * Posiziona una singola nave per il giocatore di turno
	 */
	
	protected void posizionaNaveManualmente(int x, int y, Utils.Direzione dir) {
		if (diTurno.haNaviDaPosizionare()){
			diTurno.posizionaNave(x, y, dir);
			
			if (!diTurno.haNaviDaPosizionare()) {
				this.cambiaTurno();
			}
		}
	}

	/**
	 * Posiziona le navi manualmente per entrambi i giocatori
	 */
	public void posizionaNaviManualmente(Scanner input) {
		do {
			System.out.println(diTurno);
			System.out.println(diTurno.getCampo());

			System.out.println("Posizionamento Navi...");
			System.out.println("inserisci delle coordinate e una direzione es. [1,3,D] :");
			System.out.println("[#riga, #colonna, direzione possibile: U_= UP , D = DOWN, L = LEFT, R = RIGHT]");
		
			String s = input.next();
        
			Pattern pattern = Pattern.compile("(\\d),(\\d),([U|D|L|R])");
			Matcher m = pattern.matcher(s);
		
				if (m.matches()) {
					int y = Integer.parseInt(m.group(1));
					int x = Integer.parseInt(m.group(2));
					String dir = m.group(3);
					
					switch (dir) {
					case "U":
						this.posizionaNaveManualmente(x, y, Direzione.SU);
						break;
					case "D":
						this.posizionaNaveManualmente(x, y, Direzione.GIU);
						break;
					case "L":
						this.posizionaNaveManualmente(x, y, Direzione.SINISTRA);
						break;
					case "R":
						this.posizionaNaveManualmente(x, y, Direzione.DESTRA);
						break;
					}
			
				}
			} while (diTurno.haNaviDaPosizionare());
	}
	
	/**
	 * stampa a schermo il giocatore di turno
	 * e la sua griglia
	 */
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
	public Giocatore getVincitore() {
		return vincitore;
	}
	public void setVincitore(Giocatore vincitore) {
		this.vincitore = vincitore;
	}
}
