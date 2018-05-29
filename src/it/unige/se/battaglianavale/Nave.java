/*
 *  Giulia Cagnes
 *  
 *  Version 0.1 (beta)
 */
package it.unige.se.battaglianavale;

import java.util.ArrayList;
/**
 * Classe Nave 
 * 
 * @version          0.1 21 May 2018
 * @author           Giulia Cagnes
 *
 */
public class Nave {
	/** Numero di navi*/
	private static int numeroNavi = 0;
	
	/** Lunghezza delle navi */
	private final int lunghezza;
	
	/** identificativo nave */
	private final int id;
	
	/**parametro booleano indica se la nave e' affondata */
	private boolean affondata;
	
	/**ArrayList di celle occupate*/
	private ArrayList<Cella> celleOccupate;
	
	/**
	 * Costruttore di nave
	 * 
	 * @param lunghezza della nave
	 * @param id numero di navi
	 * @param affondata booleano se lo è true se non lo è false
	 *  
	 */
	public Nave(int lunghezza) {
		this.lunghezza = lunghezza;
		this.id = numeroNavi++;
		
		this.affondata = false;
		celleOccupate = new ArrayList<>();
	}
	
	/**
	 * Controllo se la nave è affondata se tutte le celle occupate
	 * sono state colpite
	 * 
	 * @return boolean true se la nave e' affondata false altrimenti
	 */
	public boolean eAffondata() {
		if(!affondata) {	
			for (int i = 0; i < celleOccupate.size(); i++) {
				if(!celleOccupate.get(i).eColpita()) {
					return false;
				}
			}
			this.affondata = true;
		}
		return affondata;
	}
	
	/**
	 * Gets Id.
	 *
	 * @return Id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets Lunghezza.
	 *
	 * @return Lunghezza
	 */
	public int getLunghezza() {
		return lunghezza;
	}
	
	/**
	 * Aggiungo la cella da occupare all'arraylist delle celleOccupate
	 *
	 * @param cella da occupare
	 */
	public void occupaCella(Cella cellaDaOccupare) {
		celleOccupate.add(cellaDaOccupare);
	}
	
	@Override
	public String toString() { // stampo la nave a schermo
		String ret = "";
		if(this.affondata)
			ret += "X ";
		for (int i = 0; i < lunghezza; i++) {
			ret+="#";
		}
		return ret;
	}
}
