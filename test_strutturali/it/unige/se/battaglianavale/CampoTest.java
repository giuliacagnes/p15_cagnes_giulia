package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.unige.se.battaglianavale.Utils.Direzione;

public class CampoTest {
	
	Campo campo = null;
    Giocatore giocatore;
    Cella[][] griglia;
    int dim;
    Nave n;
    
	@Before	
	public void init(){
		int[] lunghezzaNavi = {2,3};
		giocatore = new Giocatore("giocatore",lunghezzaNavi);
		campo = giocatore.getCampo();
		n = new Nave(3);
	}
	@Test
	public void testPosizionaNave1() {
		

		//coordinate sbagliate
		assertFalse("Nave posizionata anche se le cordinate sono sbagliate",campo.posizionaNave(n, 0, -1, Utils.Direzione.SU));
		assertFalse("Nave posizionata anche se le cordinate sono sbagliate",campo.posizionaNave(n, -1, 0, Utils.Direzione.SU));
		assertFalse("Nave posizionata anche se le cordinate sono sbagliate",campo.posizionaNave(n, 0, 10, Utils.Direzione.SU));
		assertFalse("Nave posizionata anche se le cordinate sono sbagliate",campo.posizionaNave(n, 10, 0, Utils.Direzione.SU));
		assertFalse("Nave posizionata anche se le cordinate sono sbagliate",campo.posizionaNave(n, 10, 10, Utils.Direzione.SU));
		assertFalse("Nave posizionata anche se le cordinate sono sbagliate",campo.posizionaNave(n, -1, -1, Utils.Direzione.SU));
	}
	@Test
	public void testPosizionaNave2() {
		
		
		//nave che esce dalla griglia
		assertFalse("nave posizionata anche se esce dalla griglia",campo.posizionaNave(n, 0, 0, Utils.Direzione.SU));
		assertFalse("nave posizionata anche se esce dalla griglia",campo.posizionaNave(n, 9, 9, Utils.Direzione.GIU));
		assertFalse("nave posizionata anche se esce dalla griglia",campo.posizionaNave(n, 0, 0, Utils.Direzione.SINISTRA));
		assertFalse("nave posizionata anche se esce dalla griglia",campo.posizionaNave(n, 9, 9, Utils.Direzione.DESTRA));
		
	}
	@Test
	public void testPosizionaNave3() {
		
		//ha altre navi attorno
		giocatore.posizionaNave(0, 0, Utils.Direzione.GIU);
		assertFalse("nave posizionata ma tocca un altra nave",campo.posizionaNave(n, 1, 3, Utils.Direzione.SU));
		giocatore.posizionaNave(9, 9, Utils.Direzione.SU);
		assertFalse("nave posizionata ma tocca un altra nave", campo.posizionaNave(n, 7, 9, Utils.Direzione.GIU));
		giocatore.posizionaNave(0, 0, Utils.Direzione.DESTRA);
		assertFalse("nave posizionata ma tocca un altra nave",campo.posizionaNave(n, 1,4 ,Utils.Direzione.SINISTRA));
		giocatore.posizionaNave(9, 9, Utils.Direzione.SINISTRA);
		assertFalse("nave posizionata ma tocca un altra nave",campo.posizionaNave(n, 8,7 ,Utils.Direzione.DESTRA));
		
		
	}	
	@Test
	public void testHaAltreNaviAttorno1() {
	
		giocatore.posizionaNave(6, 6, Utils.Direzione.SU);
		//campo.posizionaNave(n, 6, 6, Utils.Direzione.GIU);
		assertTrue("la nave non ha altre navi attorno ",campo.haAltreNaviAttorno(n, 6,5));
		
		giocatore.posizionaNave(3, 3, Utils.Direzione.DESTRA);
		assertFalse("la nave ha una nave attorno", campo.haAltreNaviAttorno(n, 8, 8));
		
		
	}

		
		
		


}
