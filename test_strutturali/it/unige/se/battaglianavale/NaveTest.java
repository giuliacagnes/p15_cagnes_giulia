package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class NaveTest {
	Nave nave;
	Cella[] celleDaOccupare;
	
	@Before
	public void init() {
		nave = new Nave(5);

		// istanzio
		celleDaOccupare = new Cella[5];
		celleDaOccupare[0] = new Cella(3, 3);
		celleDaOccupare[1] = new Cella(3, 4);
		celleDaOccupare[2] = new Cella(3, 5);
		celleDaOccupare[3] = new Cella(3, 6);
		celleDaOccupare[4] = new Cella(3, 7);
		
		// occupo celle
		celleDaOccupare[0].occupa(nave.getId());
		nave.occupaCella(celleDaOccupare[0]);
		celleDaOccupare[1].occupa(nave.getId());
		nave.occupaCella(celleDaOccupare[1]);
		celleDaOccupare[2].occupa(nave.getId());
		nave.occupaCella(celleDaOccupare[2]);
		celleDaOccupare[3].occupa(nave.getId());
		nave.occupaCella(celleDaOccupare[3]);
		celleDaOccupare[4].occupa(nave.getId());
		nave.occupaCella(celleDaOccupare[4]);
	}
	
	@Test
	public void testAffondata1() { //sbagliato		
		// attacco celle
		celleDaOccupare[0].attacca();
		celleDaOccupare[1].attacca();
		celleDaOccupare[2].attacca();
		celleDaOccupare[3].attacca();
		celleDaOccupare[4].attacca();

		assertTrue("la nave non e' affondata",nave.eAffondata());	
	}
	
	
	@Test
	public void testAffondata2() { //sbagliato		
		// attacco celle
		celleDaOccupare[0].attacca();
		celleDaOccupare[2].attacca();
		celleDaOccupare[3].attacca();
		celleDaOccupare[4].attacca();

		assertFalse("la nave e' affondata",nave.eAffondata());	
	}
}
