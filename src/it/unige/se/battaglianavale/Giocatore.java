/*
 * Giulia Cagnes
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
 * @author 		Giulia Cagnes
 *
 */
public class Giocatore {
	/** Nome del giocatore*/
	private String nome;

	/** Associazione fra giocatore e navi*/
	private ArrayList<Nave> navi;
	
	public ArrayList<Nave> getNavi() {
		return navi;
	}
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
	 * Costruttore del Giocatore
	 * 
	 *@param nome del giocatore
	 *@param array di lunghezze navi
	 *
	 */
	public Giocatore(String nome, int[] lunghezzaNavi) {
		indiceNaveDaPosizionare = 0;
		this.nome = nome;
		
		int nNavi = lunghezzaNavi.length; //array con le lunghezze delle navi, la .lenght è il numero di navi
		navi = new ArrayList<>(nNavi); //lista con le navi
		
		//cicla fino al numero di navi 
		//aggiungo alla lista una nuova nave contente lunghezza nave ad ogni iterazione
		for (int i = 0; i < nNavi; i++) {
			navi.add(new Nave(lunghezzaNavi[i]));
		}
		
		campo = new Campo(); //campo del giocatore
		
		int dim = campo.getDim(); // dimensione del campo fissata a 10
		memoriaMosse = new Utils.StatoCoordinata[dim][dim]; //istanzio una matrice 10x10
		for (int i = 0; i < memoriaMosse.length; i++) {// riempo la matrice con stato cordinata = sconosciuto
			Arrays.fill(memoriaMosse[i], Utils.StatoCoordinata.SCONOSCIUTO);
		}
	}
	/**
     * Posiziona nave passando cordinate e direzione
     *
     * @param x coordinata indice colonna
     * @param y coordinata indice riga
     * @param d direzione della nave
     * @return boolean false se indice della nave è maggiore o uguale al numero di navi che voglio in campo
     *                 true incremento indice navi e posiziono la nave n nelle cordinate e direzione passate dall'utente
     */
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
	
	/**
     * Il giocatore ha navi da posizionare 
     *
     * @return boolean false se non ha navi da posizionare in campo
     *                 true  se ha navi da posizionare in campo e se è prevista dal vettore navi
     */
	
	public boolean haNaviDaPosizionare() {
		return indiceNaveDaPosizionare < navi.size();
	}
	
	/**
	 * Get memoria mosse 
	 * 
	 * @return memoriaMosse
	 */
	public Utils.StatoCoordinata[][] getMemoriaMosse() {
		return memoriaMosse;
	}
	
	/**
	 * Get Campo 
	 * 
	 * @return campo
	 */
	public Campo getCampo() {
		return campo;
	}
	
	/**
	 * Registra la mossa 
	 * @param x indice di riga
	 * @param y indice di colonna
	 * @param valore stato della cordinata
	 * 
	 * @return memoriaMosse
	 */
	public void registraMossa(int x, int y, StatoCoordinata valore) {
		memoriaMosse[y][x] = valore; 
	}
	
	/**
	 * Posiziona le navi random
	 */
	public void posizionaNaviRandom(){
		Random r = new Random(); //instanzio oggetto r di classe Random
		
		while(this.haNaviDaPosizionare()) {
			int y = r.nextInt(campo.getDim()); //ritorna numero generato casualmente
			int x = r.nextInt(campo.getDim());
			
			Direzione dir = Direzione.values()[r.nextInt(Direzione.values().length)];//direzione generata casualmente
			
			this.posizionaNave(x, y, dir); //posiziona nave cordinate scelte randomicamente
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
/**
 * IL Giocatore ha navi in gioco
 * 
 * @return true se tutte le tue navi sono tutte affondate, false altrimenti
 */
	public boolean haNaviInGioco() {
		for (int i = 0; i < navi.size(); i++) {
			if(!navi.get(i).eAffondata()) {
				return true;
			}
		}
		return false;
		
	}
public void setCampo(Campo campo) {
	this.campo = campo;
}
}
