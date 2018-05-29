/*
 *  Giulia Cagnes
 *  
 *  Version 0.1 (beta)
 */
package it.unige.se.battaglianavale;

/**
 * Classe Cella 
 * 
 * @version    0.1 22 May 2018
 * @author     Giulia Cagnes
 *
 */
public class Cella {
	/**coordinata x*/
	private final int x;
	
	/**coordinata y*/
	private final int y;
	
	/**booleana cella occupata*/
	private boolean occupata;
	
	/**booleana cella colpita*/
	private boolean colpita;

    /**id nave*/
	private int idNave;

	/**
	 * Costruttore di Cella
	 * 
	 * @param x indice di colonna
	 * @param y indice di riga
	 */
	public Cella(int x, int y) {
		idNave = -1;
		this.x = x;
		this.y = y;
		
		occupata = false;
		colpita = false;
	}

	/**
	 * Get getX coordinata x
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get getY coordinata y
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Occupa cella passo idNave
	 * 
	 * @param idNave
	 */
	public void occupa(int idNave) {
		this.idNave = idNave;
		
		occupata = true;
	}
	
	/**
	 * La cella e' colpita
	 * 
	 * @return boolean true se la cella e' colpita, false altrimenti
	 */
	public boolean eColpita() {
		return colpita;
	}
	
	/**
	 * La cella e' occupata
	 * 
	 * @return boolean true occupata false altrimenti
	 */
	public boolean eOccupata() {
		return occupata;
	}
	
	/**
	 * Get getIdNave
	 * 
	 * @return idNave
	 */
	public int getIdNave() {
		return idNave;
	}
	
	/**
	 * Attacca la cella 
	 * 
	 * @return boolean True se la cella è occupata => cella colpita
	 * 				   False se non hai attaccato
	 */
	public boolean attacca() {
		if(occupata) {
			colpita = true;
			return true;
		}
		return false; // return true secondo me 
	}
	/**
	 * stampo a schermo con il simbolo # se la cella e' occupata dalla nave altrimenti ~
	 */
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (occupata) {
			return "#";
		}
		return "~";
	}
}
