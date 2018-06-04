package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
// attacca cella
public class UC10 {
    Giocatore giocatoreA;
    Giocatore giocatoreB;
	
	Partita p;
	
	Giocatore giocatoreAV;
	@Before
	public void setUp() throws Exception {
		int[] lunghezzaNavi = {2,3,1};
		giocatoreA = new Giocatore("GiocatoreA",lunghezzaNavi);
		giocatoreB = new Giocatore("GiocatoreB",lunghezzaNavi);
		
		Campo c = new Campo();
		c.posizionaNave(new Nave(2), 3, 3, Utils.Direzione.SU);
		c.posizionaNave(new Nave(3), 5, 5, Utils.Direzione.DESTRA);
		c.posizionaNave(new Nave(1), 7, 7, Utils.Direzione.SINISTRA);

		giocatoreA.setCampo(c);
		
		c = new Campo();
		c.posizionaNave(new Nave(2), 3, 3, Utils.Direzione.SU);
		c.posizionaNave(new Nave(3), 5, 5, Utils.Direzione.DESTRA);
		c.posizionaNave(new Nave(1), 7, 7, Utils.Direzione.SINISTRA);

		giocatoreB.setCampo(c);
		
		p = new Partita(giocatoreA, giocatoreB);
	}

	@Test
	public void attaccaCella1() {
		assertTrue("la cella 3,3 non e' stata attaccata",p.attacca(3, 3));
		assertTrue("la cella 5,5 non e' stata attaccata",p.attacca(5, 5));
		assertTrue("la cella 7,7 non e' stata attaccata",p.attacca(7, 7));
		
	}
	@Test
	public void attaccaCella2() {
		assertFalse("la nave risulta attaccata ma non lo e'",p.attacca(9, 9));
		assertFalse("la nave risulta attaccata ma non lo e'",p.attacca(1, 1));
	}
	

}
