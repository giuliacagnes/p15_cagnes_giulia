package it.unige.se.battaglianavale;

public class UtenteRegistrato {
	private String name;
	private String email;
	private String password;
	
	public UtenteRegistrato(String name, String cognome, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}
}
