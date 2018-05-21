package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class CellaTest {
	Cella cella;
	
	
	@Before
	public void init() {
		cella = new Cella(3,5);
	}
	
	@Test
	public void testGetX1() {
		assertTrue(cella.getX() == 3);
	}

	@Test
	public void testGetX2() {
		assertFalse(cella.getX()!= 3);
	}
	
	@Test
	public void testGetY1() {
		assertTrue(cella.getY() == 5);
	}
	@Test
	public void testGetY2() {
		assertFalse(cella.getY() != 5);
	}
	
	@Test 
	public void testOccupata1() {
		cella.occupa(3);
		assertTrue("la cella non e' occupata", cella.eOccupata());
	}
	
	@Test 
	public void testOccupata2() {
		assertFalse("la cella e' occupata", cella.eOccupata());
	}
	
	@Test 
	public void testColpita1() {
		cella.attacca();
		assertFalse("la cella e' colpita", cella.eColpita());
	}
	
	@Test 
	public void testColpita2() {
		cella.occupa(3);
		cella.attacca();
		assertTrue("la cella non e' colpita", cella.eColpita());
	}
	
	@Test 
	public void testIdNave() {
		cella.occupa(3);
		assertEquals("la cella e occupata con una nave diversa da " + cella.getIdNave(), cella.getIdNave(),3);
	}
}
