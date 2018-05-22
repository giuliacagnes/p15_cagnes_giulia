/*
 * Nicolò Michelis
 * 
 * Version 0.1 (beta)
 */
package it.unige.se.battaglianavale;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import it.unige.se.battaglianavale.Utils.Direzione;
import it.unige.se.battaglianavale.Utils.StatoCoordinata;

/**
 * Classe Giocatore.
 * 
 * @version		0.1 28 May 2017
 * @author 		Nicolò Michelis
 *
 */
public class Giocatore {
	/** Nome del giocatore*/
	private String nome;

	/** Associazione fra giocatore e navi*/
	private ArrayList<Nave> navi;
	
	/**Campo del giocatore*/
	private Campo campo;

	/**Associazione tra Utils e giocatore */
	private Utils.StatoCoordinata memoriaMosse[][];
	
	/**indice nave da posizionare*/
	private int indiceNaveDaPosizionare;

	/*
	 * lunghezzaNavi: array di lunghezza navi; la dimensione è il numero di navi, mentre i suoi elementi la lunghezza di ogni nave
	 */
	/**
	 * Costruttore
	 * 
	 * @version		0.1 28 May 2017
	 * @author 		Nicolò Michelis
	 *
	 */
	public Giocatore(String nome, int[] lunghezzaNavi) {
		indiceNaveDaPosizionare = 0;
		this.nome = nome;
		
		int nNavi = lunghezzaNavi.length;
		navi = new ArrayList<>(nNavi);
		
		for (int i = 0; i < nNavi; i++) {
			navi.add(new Nave(lunghezzaNavi[i]));
		}
		
		campo = new Campo();
		
		int dim = campo.getDim(); 
		memoriaMosse = new Utils.StatoCoordinata[dim][dim];
		for (int i = 0; i < memoriaMosse.length; i++) {
			Arrays.fill(memoriaMosse[i], Utils.StatoCoordinata.SCONOSCIUTO);
		}
	}
	
	public boolean posizionaNave(int x, int y, Utils.Direzione d) {
		if (indiceNaveDaPosizionare >= navi.size()) {
			return false;
		}
		Nave n = navi.get(indiceNaveDaPosizionare);
		if (campo.posizionaNave(n, x, y, d)) {
			indiceNaveDaPosizionare++;
			return true;
		}
		return false;
	}
	
	public boolean haNaviDaPosizionare() {
		return indiceNaveDaPosizionare < navi.size();
	}
	
	public Utils.StatoCoordinata[][] getMemoriaMosse() {
		return memoriaMosse;
	}
	
	public Campo getCampo() {
		return campo;
	}
	
	public void registraMossa(int x, int y, StatoCoordinata valore) {
		memoriaMosse[y][x] = valore;
	}
	
	public void posizionaNaviRandom(){
		Random r = new Random();
		
		while(this.haNaviDaPosizionare()) {
			int y = r.nextInt(campo.getDim());
			int x = r.nextInt(campo.getDim());
			
			Direzione dir = Direzione.values()[r.nextInt(Direzione.values().length)];
			
			this.posizionaNave(x, y, dir);
		}
	}

	@Override
	public String toString() {
		String ret = "";
		ret += nome;
		ret += "\n";
		ret += "\n";
		for (int i = 0; i < navi.size(); i++) {
			Nave n = navi.get(i);
			if(i==indiceNaveDaPosizionare) {
				ret += "--> ";
			} else if((indiceNaveDaPosizionare==navi.size()) && (n.eAffondata())) {
				ret += "X ";
			}

			ret += n;
			ret += "\n";
		}
		ret += "\n";
		return ret;
	}

	public boolean haNaviInGioco() {
		for (int i = 0; i < navi.size(); i++) {
			if(!navi.get(i).eAffondata()) {
				return true;
			}
		}
		return false;
		
	}
}
