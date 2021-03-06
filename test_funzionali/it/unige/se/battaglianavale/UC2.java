package it.unige.se.battaglianavale;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UC2 {

	@Before
	public void setUp() throws Exception {
		BattagliaNavale.registraUtente(new UtenteRegistrato("Test", "SuperTest", "test@gmail.com", "123"));
	}

	@Test
		public void testLoginFailed() {
			assertNull("l'utente si è loggato con password errata",BattagliaNavale.verificaLogin("test@gmail.com","1234"));
			assertNull("l'utente si è loggato con email errata",BattagliaNavale.verificaLogin("test2@gmail.com","123"));
		}
	@Test
	public void testLoginSuccessfull() {
		assertNotNull("",BattagliaNavale.verificaLogin("test@gmail.com","123"));
	}


}
