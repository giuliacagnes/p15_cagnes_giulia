package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UC1 {

	@Before
	public void init() {
		UtenteRegistrato utente= new UtenteRegistrato("Test", "SuperTest", "test@gmail.com", "123");
	}	
	
	@Test
	public void testLoginFailed() {
		assertNull("l'utente si è loggato con password errata",BattagliaNavale.login("test@gmail.com","1234"));
		assertNull("",BattagliaNavale.login("test2@gmail.com","123"));
	}
	
	@Test
	public void testLoginSuccessfull() {
		assertNotNull("",BattagliaNavale.login("test@gmail.com","123"));
	}

}
