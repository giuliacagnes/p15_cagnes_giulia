package it.unige.se.battaglianavale;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class UtenteRegistratoTest {

  
    UtenteRegistrato utente = null;
    
	@Before
	public void init() {
		
		utente = new UtenteRegistrato("testNome","testCognome","test@gmail.com","123");
	}
	
	
	@Test
	public void testNome() {
		String newNome = "pippo";
		utente.setName(newNome);
		assertEquals("Aggiorna nome utente", newNome,utente.getName());
		
	}
	@Test
	public void testCognome() {
		String newCognome = "superpippo";
		utente.setCognome(newCognome);
		assertEquals("Aggiorna cognome utente", newCognome,utente.getCognome());
		
	}
	@Test
	public void testEmail() {
		String newEmail = "pippo@gmail.com";
		utente.setEmail(newEmail);
		assertEquals("Aggiorna email utente", newEmail,utente.getEmail());
		
	}
	@Test
	public void testPassword() {
		String newPassword = "1234";
		utente.setPassword(newPassword);
		assertEquals("Aggiorna email utente", newPassword,utente.getPassword());
		
	}


}
