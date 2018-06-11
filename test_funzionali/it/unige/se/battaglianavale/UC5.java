package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UC5 { //gioca nuova partita

	Partita p = null;
	Giocatore g1,g2;
	int[] lunghezze = {2,3};
	@Before
	public void setUp() throws Exception {
		g1 = new Giocatore("topolino", lunghezze);
		g2 = new Giocatore ("pippo", lunghezze);
		
	}

	@Test
	public void creaNuovaPartita() {
		p = new Partita(g1,g2);
		assertNotNull(p);
	}

}
