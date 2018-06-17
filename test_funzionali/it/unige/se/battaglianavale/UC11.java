package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UC11 {

	Giocatore g1;
	Giocatore g2;
	int[] lung = {2,3};
	
	Partita p;
	@Before
	public void setUp() throws Exception {
		g1 = new Giocatore("Pippo", lung);
		g2 = new Giocatore("Pluto", lung);
		p = new Partita(g1, g2);
	}

	@Test
	public void cambioTurno() {
		p.cambiaTurno();
		assertEquals("cambio turno non è avvenuto", p.getDiTurno(),g2); //p.getDiTurno() == g2
		p.cambiaTurno();
		assertEquals("cambio turno non è avvenuto", p.getDiTurno(),g1); //p.getDiTurno() == g2
	}

}
