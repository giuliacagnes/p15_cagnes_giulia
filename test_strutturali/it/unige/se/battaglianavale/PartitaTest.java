package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PartitaTest {
	Partita p;
	Giocatore giocatore1;
	Giocatore giocatore2;
	Giocatore diTurno;
	
	@Before
	public void init() {
		int[] lunghezzanavi= {3,2};
		
		giocatore1 = new Giocatore("giocatore1", lunghezzanavi);
		giocatore2 = new Giocatore("giocatore2", lunghezzanavi);
		

		p = new Partita(giocatore1, giocatore2);
		
		giocatore1.posizionaNave(3, 2, Utils.Direzione.GIU);
		giocatore2.posizionaNave(3, 2, Utils.Direzione.GIU);
		
		giocatore1.posizionaNave(6, 4, Utils.Direzione.GIU);
		giocatore2.posizionaNave(6, 4, Utils.Direzione.GIU);
	}
	

	@Test
	public void testETerminata1() {
		//giocatore1
		p.attacca(3, 2);
		p.attacca(3, 3);
		p.attacca(3, 4);  //affonda nave 1
		
		p.cambiaTurno();
		
		//giocatore2
		p.attacca(3, 2);
		p.attacca(3, 3);
		p.attacca(3, 4);  //affonda nave 1
		
		assertFalse("la partita e' terminata",p.eTerminata());
	}
	
	@Test
	public void testETerminata2() {
		//giocatore1
		p.attacca(3, 2);
		p.attacca(3, 3);
		p.attacca(3, 4); //affonda nave 1	
		
		p.cambiaTurno();
		
		//giocatore2
		p.attacca(3, 2);
		p.attacca(3, 3);
		p.attacca(3, 4); //affonda nave 1

		p.attacca(6, 4);
		p.attacca(6, 5); //affonda nave 2

		assertTrue("la partita non e' terminata",p.eTerminata());
	}

	@Test		
	public void testAttacca1() {
		p.attacca(3, 2);
		
		Utils.StatoCoordinata[][] m = giocatore1.getMemoriaMosse();
		Cella[][] griglia = giocatore2.getCampo().getGriglia();
		
		for (int i = 0; i < griglia.length; i++) {
			for (int j = 0; j < griglia.length; j++) {
				if(i == 3 && j == 2) {
					assertEquals("giocatore1, cella " + i + " " + j + " é stata registrata la mossa sbagliata " + m[j][i],m[j][i],Utils.StatoCoordinata.COLPITO);
					assertTrue("La celle " + i + " " + j + " di giocatore2 non e stata attaccata",griglia[j][i].eColpita());
				}
				else {
					assertEquals("giocatore1, cella " + i + " " + j + " é stata registrata la mossa sbagliata " + m[j][i],m[j][i],Utils.StatoCoordinata.SCONOSCIUTO);
					assertFalse("La celle " + i + " " + j + " di giocatore2 non e stata attaccata",griglia[j][i].eColpita());					
				}
			}
		}
	}
	
	@Test		
	public void testAttacca2() {		
		Utils.StatoCoordinata[][] m = giocatore1.getMemoriaMosse();
		Cella[][] griglia = giocatore2.getCampo().getGriglia();
		
		assertEquals("giocatore1 é stata registrata la mossa sbagliata " + m[2][3],m[2][3],Utils.StatoCoordinata.SCONOSCIUTO);
		assertFalse("La celle di giocatore2 non e stata attaccata",griglia[2][3].eColpita());
	}
	
	
	@Test		
	public void testAttacca3() {
		p.attacca(0, 0);
		
		Utils.StatoCoordinata[][] m = giocatore1.getMemoriaMosse();
		Cella[][] griglia = giocatore2.getCampo().getGriglia();
		
		assertEquals("giocatore1 é stata registrata la mossa sbagliata " + m[0][0],m[0][0],Utils.StatoCoordinata.MANCATO);
		assertFalse("La celle di giocatore2 non e stata attaccata",griglia[0][0].eColpita());
	}
	
	@Test
	public void testCambiaTurno() {
		assertEquals("giocatore1 non e di turno", p.getDiTurno(),giocatore1);
		p.cambiaTurno();
		assertEquals("giocatore2 non e di turno", p.getDiTurno(),giocatore2);
	}
	

}
