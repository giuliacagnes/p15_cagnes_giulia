package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class GiocatoreTest {

	
	Giocatore giocatore;

	
	@Before
	public void init() {
		int[] testLunghezzaNavi = {5,3,2,5,3};
		giocatore = new Giocatore("Test", testLunghezzaNavi);
	
	}
	
	@Test
	public void testPosizionaNave1() {
		boolean haPosizionato = giocatore.posizionaNave(2, 3,Utils.Direzione.SU);
		
		assertFalse("la prima nave di lunghezza 5 e stata posizionata anche se non cera abbastanza spazio", haPosizionato);
	}

	@Test
	public void testPosizionaNave2() {
		boolean haPosizionato = giocatore.posizionaNave(2, 3,Utils.Direzione.GIU);
		
		assertTrue("la prima nave di lunghezza 5 non e stata posizionata anche se c'era abbastanza spazio", haPosizionato);
	}
	
	@Test
	public void testPosizionaNave3() {
		boolean[] haPosizionato = new boolean[5];
		haPosizionato[0] = giocatore.posizionaNave(0, 0,Utils.Direzione.GIU);
		haPosizionato[1] = giocatore.posizionaNave(2, 2,Utils.Direzione.GIU);
		haPosizionato[2] = giocatore.posizionaNave(4, 4,Utils.Direzione.GIU);
		haPosizionato[3] = giocatore.posizionaNave(6, 0,Utils.Direzione.GIU);
		haPosizionato[4] = giocatore.posizionaNave(8, 3,Utils.Direzione.GIU);
		
		boolean[] haPosizionatoAtteso = {true,true,true,true,true};
				
		assertArrayEquals("alcune delle 5 navi non sono state posizionate", haPosizionato,haPosizionatoAtteso);
	}
	
	@Test
	public void testPosizionaNave4() {
		giocatore.posizionaNave(0, 0,Utils.Direzione.GIU);
		giocatore.posizionaNave(2, 2,Utils.Direzione.GIU);
		giocatore.posizionaNave(4, 4,Utils.Direzione.GIU);
		giocatore.posizionaNave(6, 0,Utils.Direzione.GIU);
		giocatore.posizionaNave(8, 3,Utils.Direzione.GIU);

		boolean haPosizionato = giocatore.posizionaNave(9, 0,Utils.Direzione.GIU);
				
		assertFalse("ha posizionato una nave aggiuntiva", haPosizionato);
	}
	
	@Test
	public void testHaNaviDaPosizionare() {
		assertTrue("il giocatore non ha navi da posizionare",giocatore.haNaviDaPosizionare());
		giocatore.posizionaNave(0, 0,Utils.Direzione.GIU);
		
		assertTrue("il giocatore non ha navi da posizionare",giocatore.haNaviDaPosizionare());
		giocatore.posizionaNave(2, 2,Utils.Direzione.GIU);
		
		assertTrue("il giocatore non ha navi da posizionare",giocatore.haNaviDaPosizionare());
		giocatore.posizionaNave(4, 4,Utils.Direzione.GIU);
		
		assertTrue("il giocatore non ha navi da posizionare",giocatore.haNaviDaPosizionare());
		giocatore.posizionaNave(6, 0,Utils.Direzione.GIU);
		
		assertTrue("il giocatore non ha navi da posizionare",giocatore.haNaviDaPosizionare());
		giocatore.posizionaNave(8, 3,Utils.Direzione.GIU);
		
		assertFalse("il giocatore ha navi da posizionare",giocatore.haNaviDaPosizionare());	
	}
	@Test
	public void TestRegistraMosse() {
		int dim = giocatore.getMemoriaMosse().length;
		Utils.StatoCoordinata[][] memoriaMosseTest = new Utils.StatoCoordinata[dim][dim];
		for (int i = 0; i < dim; i++) {
			Arrays.fill(memoriaMosseTest[i], Utils.StatoCoordinata.SCONOSCIUTO);
		}
		
		memoriaMosseTest[3][4] = Utils.StatoCoordinata.COLPITO;
		memoriaMosseTest[5][2] = Utils.StatoCoordinata.MANCATO;
		memoriaMosseTest[3][7] = Utils.StatoCoordinata.MANCATO;
		
		giocatore.registraMossa(4, 3, Utils.StatoCoordinata.COLPITO);
		giocatore.registraMossa(2, 5, Utils.StatoCoordinata.MANCATO);
		giocatore.registraMossa(7, 3, Utils.StatoCoordinata.MANCATO);
		Utils.StatoCoordinata[][] memoriaMosse = giocatore.getMemoriaMosse();
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				assertEquals("non e stata registrata una mossa correttamente",memoriaMosse[i][j],memoriaMosseTest[i][j]);
			}
		}
	}
}
