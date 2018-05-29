/*
 *  Giulia Cagnes
 *  
 *  Version 0.1 (beta)
 */
package it.unige.se.battaglianavale;
/**
 * Classe UtenteRegistrato
 * 
 * @version   0.1 May 2018
 * @author    Giulia Cagnes 
 *
 */

public class UtenteRegistrato {
	/** Nome dell'utente */
	private String name;
	
	/** Cognome dell'utente*/
	private String cognome;
	
	/** Email dell'utente */
	private String email;
	
	/** Password dell'utente */
	private String password;
	
	/**
	 * Costruttore di UtenteRegistrato
	 * 
	 * @param name
	 * @param cognome
	 * @param email
	 * @param password
	 */
	
	public UtenteRegistrato(String name, String cognome, String email, String password) {
		super();
		this.name = name;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Get email dell'utente.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set email.
	 * 
	 * @param email dell'utente
	 */
	public void setEmail(String email) {
		this.email = email;
		
	}
	
	/**
	 * Get password dell'utente.
	 * 
	 * @return password dell'utente
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set password.
	 * 
	 * @param password dell'utente
	 */
	public void setPassword(String password) {
		this.password = password;
		
	}
	
	/**
	 * Get name dell'utente.
	 * 
	 * @return name dell'utente
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name.
	 * 
	 * @param name dell'utente
	 */
	public void setName(String nome) {
		this.name = nome;
	}
	
	/**
	 * Get cognome dell'utente.
	 * 
	 * @return cognome dell'utente
	 */
	public String getCognome() {
		return cognome;
	}
	

	/**
	 * Set cognome.
	 * 
	 * @param cognome dell'utente
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
}
