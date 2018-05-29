
/* 
 * Giulia Cagnes
 * 
 * Version 0.1 (beta)
 */
package it.unige.se.battaglianavale;

import java.util.ArrayList;
/**
 * Classe Campo di gioco
 * 
 * @version 0.1 22 May 2018
 * @author Giulia Cagnes
 *
 */
public class Campo {
	/** matrice di tipo cella chiamata griglia*/
	private Cella griglia[][];
	
	/**dimensione matrice è fissato a 10*/
	private final int dim = 10;
	
	/**lista di interi contenuti gli id delle navi*/
	private ArrayList<Integer> idNavi;
	
	/**
     * Costruttore di campo
     *
     */
	public Campo() {
		griglia = new Cella[dim][dim];//ciclo che riempe a matrice griglia con le celle
		for (int i = 0; i < griglia.length; i++) { // numero di righe
			for (int j = 0; j < griglia[i].length; j++) { // numero di colonne della riga i-esima
				griglia[i][j] = new Cella(i,j);
			}
		}
		idNavi = new ArrayList<>();
	}
	
	/**
	 * 
	 * @param nave 
	 * @param x indice di colonna
	 * @param y indice di riga
	 * @param direzione 
	 * @return boolean False : se la posizione non è dentro la griglia;
	 * 				           se la nave non ci sta dentro alla griglia;
	 * 				           se le celle dove posizionare sono gia occupate
	 *                         se ha altre navi attorno
	 *                         questi quattro controlli per tutte le 4 direzioni possibili
	 *                 True  : posiziona nave (le celle vengono occupate), aggiunta alla lista indice navi
	 */
	public boolean posizionaNave(Nave nave, int x, int y, Utils.Direzione direzione) {
		// Controllo che la posizione fornita sia all'interno della griglia
		if (x >= dim || x < 0 || y >= dim || y < 0 ) {
			return false;
		}
		
		switch (direzione) {
		case SU:
			// Controllo se la nave sta dentro alla griglia
			if (y - nave.getLunghezza() < 0) { 
				return false;                  
			}
				
			// Controllo se le celle non sono occupate da altre navi o se altre navi non siano attorno
			for (int i = 0; i < nave.getLunghezza(); i++) {
				if (	griglia[y-i][x].eOccupata() ||
						haAltreNaviAttorno(nave,x,y-i)) {
					return false;
				}
			}
			
			// Occupo celle
			for (int i = 0; i < nave.getLunghezza(); i++) {
				griglia[y-i][x].occupa(nave.getId());
				nave.occupaCella(griglia[y-i][x]);
			}
			break;
		
		
		case GIU:
			// Controllo se la nave sta dentro alla griglia
			if (y + nave.getLunghezza() >= dim) {
				return false;
			}
			
			// Controllo se le celle non sono occupate da altre navi o se altre navi non siano attorno
			for (int i = 0; i < nave.getLunghezza(); i++) {
				if (	griglia[y+i][x].eOccupata() ||
						haAltreNaviAttorno(nave,x,y+i)) {
					return false;
				}
			}
			
			// Occupo celle
			for (int i = 0; i < nave.getLunghezza(); i++) {
				griglia[y+i][x].occupa(nave.getId());
				nave.occupaCella(griglia[y+1][x]);
			}
			break;
			
			
		case DESTRA:
			// Controllo se la nave sta dentro alla griglia
			if (x + nave.getLunghezza() >= dim) {
				return false;
			}
			
			// Controllo se le celle non sono occupate da altre navi o se altre navi non siano attorno
			for (int i = 0; i < nave.getLunghezza(); i++) {
				if (	griglia[y][x+i].eOccupata() ||
						haAltreNaviAttorno(nave,x+i,y)) {
					return false;
				}
			}
			
			// Occupo celle
			for (int i = 0; i < nave.getLunghezza(); i++) {
				griglia[y][x+i].occupa(nave.getId());
				nave.occupaCella(griglia[y][x+i]);
			}
			break;
			
			
		case SINISTRA:
			// Controllo se la nave sta dentro alla griglia
			if (x - nave.getLunghezza() < 0) {
				return false;
			}
			
			// Controllo se le celle non sono occupate da altre navi o se altre navi non siano attorno
			for (int i = 0; i < nave.getLunghezza(); i++) {
				if (	griglia[y][x-i].eOccupata() ||
						haAltreNaviAttorno(nave,x-i,y)) {
					return false;
				}
			}
			
			// Occupo celle
			for (int i = 0; i < nave.getLunghezza(); i++) {
				griglia[y][x-i].occupa(nave.getId());
				nave.occupaCella(griglia[y][x-i]);
			}
			break;			
			
			
		}
		
		idNavi.add(nave.getId());
		return true;
	}
	
	/**
	 * Get getDim dimensione 
	 * 
	 * @return dimensione
	 */
	public int getDim() {
		return dim;
	}
	
	/**
	 * Controllo se la nave che voglio posizionare ha delle navi attorno
	 * 
	 * @param nave 
	 * @param x indice colonna
	 * @param y indire riga
	 * @return boolean true : se siamo sul bordo della griglia e la cella precendente e' occupata e l'id è differente
	 *                 false: se le condizioni precedenti,per tutte le direzioni, non sono verificate => non ha navi intorno
	 */
	private boolean haAltreNaviAttorno(Nave nave, int x, int y) {
		// Su
		if(		(y != 0) && // non siamo nel bordo superiore
				(griglia[y-1][x].eOccupata()) && // non e libera
				nave.getId() != griglia[y-1][x].getIdNave()) // e una nave diversa
		{
			return true;
		}
		// Su-sinistra
		if(		(y != 0) && // non siamo nel bordo superiore
				(x != 0) && // non siamo nel bordo sinistro
				(griglia[y-1][x-1].eOccupata()) && // non e libera
				nave.getId() != griglia[y-1][x-1].getIdNave()) // e una nave diversa
		{
			return true;
		}
		// Su-destra
		if(		(y != 0) && // non siamo nel bordo superiore
				(x != dim-1) && // non siamo nel bordo destro
				(griglia[y-1][x+1].eOccupata()) && // non e libera
				nave.getId() != griglia[y-1][x+1].getIdNave()) // e una nave diversa
		{
			return true;
		}
		
		
		
		
		// Giu
		if(		(y != dim-1) && // non siamo nel bordo inferiore
				(griglia[y+1][x].eOccupata()) && // non e libera
				(nave.getId() != griglia[y+1][x].getIdNave())) // e una nave diversa
		{
			return true;
		}
		// Giu-sinistra
		if(		(y != dim-1) && // non siamo nel bordo inferiore
				(x != 0) && // non siamo nel bordo sinistro
				(griglia[y+1][x-1].eOccupata()) && // non e libera
				(nave.getId() != griglia[y+1][x-1].getIdNave())) // e una nave diversa
		{
			return true;
		}
		// Giu-destra
		if(		(y != dim-1) && // non siamo nel bordo inferiore
				(x != dim-1) && // non siamo nel bordo destro
				(griglia[y+1][x+1].eOccupata()) && // non e libera
				(nave.getId() != griglia[y+1][x+1].getIdNave())) // e una nave diversa
		{
			return true;
		}
		
		
		
		
		
		// Destra
		if(		(x != dim-1) && // non siamo nel bordo destro
				(griglia[y][x+1].eOccupata()) && // non e libera
				(nave.getId() != griglia[y][x+1].getIdNave())) // e una nave diversa
		{
			return true;
		}
		// Sinistra
		if(		(x != 0) && // non siamo nel bordo sinistro
				(griglia[y][x-1].eOccupata()) && // non e libera
				(nave.getId() != griglia[y][x-1].getIdNave())) // e una nave diversa
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Attacco la cella
	 * 
	 * @param x numero di colonna
	 * @param y numero di riga
	 * @return boolean true  : se ha attaccato la cella
	 *                 false :non ho attaccato la cella
	 */
	public boolean attaccaCella(int x, int y) {
		if(griglia[y][x].attacca()) // internamente  y e' numero di riga
			return true;            // x numero di colonna
		return false;
	}
	
	public Cella[][] getGriglia() {
		return griglia;
	}

	/**
	 * stampo a schermo la griglia 
	 */
	@Override
	public String toString() { // stampo la griglia a schermo
		String ret = new String();
		
		for (int i = 0; i < griglia.length; i++) {
		    for (int j = 0; j < griglia[i].length; j++) {
		    	ret += griglia[i][j] + "   ";
		    }
		    ret += " " + i + "\n\n";
		}
		for (int j = 0; j < griglia[dim-1].length; j++) {
	    	ret += j + "   ";
	    }
		ret += "\n";
		
		return ret;
	}
}
