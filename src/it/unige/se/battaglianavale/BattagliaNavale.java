/*
 * Giulia Cagnes
 * 
 * Version 0.1 (beta)
 */
package it.unige.se.battaglianavale;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe Battaglia Navale contenente il main utile a mostrare tutte le funzionalità.
 * 
 * @version     0.1  21 May 2018
 * @author      Giulia Cagnes
 *
 */

public class BattagliaNavale {
	/* ArrayList contiene utenti */
	private static ArrayList<UtenteRegistrato> utentiRegistrati = new ArrayList<UtenteRegistrato>();
	/* ArrayList contiene utenti loggati, associazione fra battaglia navale e utente registrato */
	private static ArrayList<UtenteRegistrato> utentiLoggati = new ArrayList<UtenteRegistrato>();

    private static String percorsoRegolamento = "src/Regole-del-gioco";

	/**
	 * Main function 
	 * 
	 * @param args argomenti
	 */
	public static void main(String[] args) {
		String s = null;
		Pattern pattern = null;
		Matcher matcher = null;
		
		
		Scanner input = new Scanner(System.in);
		
		// Aggiungo due utenti registrati per simulare persistenza
		registraUtente(new UtenteRegistrato("Giulia","Cagnes","giulia.cagnes@gmail.com","12345"));
		registraUtente(new UtenteRegistrato("Roberto","Bianchi","roberto.bianchi@gmail.com","54321"));
		
		schermataLogin(input);

		
		
		schermataPrincipale(input);
		
		do {
			System.out.println("Inserisci lunghezze delle navi (es: [2,3,4] per avere 3 navi di dimensione 2,3 e 4 rispettivamente): ");
		    System.out.println("NB: lunghezza massima della nave e' 5 puoi insiririefino ad un massimo 5 navi in campo.");
			s = input.next();

			pattern = Pattern.compile("^([1|2|3|4|5],){0,4}([1|2|3|4|5])$");
			matcher = pattern.matcher(s);
		} while(!matcher.matches());
		
		
		// trasforma la string in input in un array di stringhe (usando la virgola come separatore) es. 3,4,2 -> [3 4 2]
		String[] lunghezzaNaviStr = s.split(",");
		
		// istanzio l'array lunghezza navi la cui dimensione e la stessa del vettore sopra
		int[] lunghezzaNavi = new int[lunghezzaNaviStr.length];
		
	    int i=0;
	    for(String lunghezzaNave:lunghezzaNaviStr){ // alla prima iterazione lunghezzaNave varra 3, alla seconda quattro e alla 3 due
	        lunghezzaNavi[i]=Integer.parseInt(lunghezzaNave);
	        System.out.println(lunghezzaNavi[i]);
	        i++;
	    }
	    
		Giocatore giocatoreA = new Giocatore(utentiLoggati.get(0).getName(), lunghezzaNavi);
		Giocatore giocatoreB = new Giocatore(utentiLoggati.get(1).getName(), lunghezzaNavi);

		Partita partita = new Partita(giocatoreA,giocatoreB);

		// Fase di posizionamento navi			
		do {
			System.out.println("Premere:");
			System.out.println("[R]andom: per disporre le navi randomiament");
			System.out.println("[M]anuale: per disporre le navi manualmente");
			s = input.next();
	        
			pattern = Pattern.compile("[R|M]", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(s);
		} while(!matcher.matches());
		
		if(s.toLowerCase().equals("r")){
			partita.posizionaNaviRandom();
		} else {
			partita.posizionaNaviManualmente(input);
		}
		
		// Fase di gioco
		while(!partita.eTerminata()) {
			boolean haColpito = true;

			System.out.println(partita);
			System.out.println("Spara un colpo!");
			System.out.println("inserisci delle coordinate [1,3] :");
			System.out.println("[#riga,#colonna]");
			
	        s = input.next();
	        
			pattern = Pattern.compile("(\\d),(\\d)");
			matcher = pattern.matcher(s);
			
			if (matcher.matches()) {
				int y = Integer.parseInt(matcher.group(1));
				int x = Integer.parseInt(matcher.group(2));
			
				haColpito = partita.attacca(x, y);
			}	
			
			if(!haColpito) {
				partita.cambiaTurno();	
			}
		}
		input.close();
		
		System.exit(0);
	}
	
	private static void schermataLogin(Scanner input) {
		do {
			String sceltaUtente;
			
			Matcher matcher;
			do {
				System.out.println(">>>>> Benvenuto nel gioco di Battaglia Navale online! <<<<<");				
				System.out.println("Premere:");
				System.out.println("[R]egistra un nuovo utente");
				System.out.println("[L]og in");
				sceltaUtente = input.next();
		        
				Pattern pattern = Pattern.compile("[R|L]", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(sceltaUtente);
			} while(!matcher.matches());
			
	
			if(sceltaUtente.toLowerCase().equals("r")) {
				registraUtenteDaConsole(input);
				loginUtenteDaConsole(input);
			} else { 
				System.out.println("Login primo giocatore:");
				loginUtenteDaConsole(input);
				do {
								
					System.out.println("Secondo giocatore...Premere:");
					System.out.println("[R]egistra un nuovo utente");
					System.out.println("[L]og in");
					sceltaUtente = input.next();
			        
					Pattern pattern = Pattern.compile("[R|L]", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(sceltaUtente);
				} while(!matcher.matches());
				if(sceltaUtente.toLowerCase().equals("r")) {
					registraUtenteDaConsole(input);
					loginUtenteDaConsole(input);
				
				}else {
				System.out.println("login secondo giocatore");
				loginUtenteDaConsole(input);		
				}
			}
		}while(utentiLoggati.size() < 2);
	}

	public static void loginUtenteDaConsole(Scanner input) {
		String email = null;
		String password = null;
		
		UtenteRegistrato u = null;
		do {
			System.out.println("email:");
			
			email = convalidaEmail(input);
				
			System.out.println("password:");			
			password = input.next();
			
			u = verificaLogin(email,password);
		}while(u == null);
		
		utentiLoggati.add(u);
	}
	
	private static String convalidaEmail(Scanner input) {
		Pattern pattern = null;
		Matcher matcher = null;
		
		String email = null;
		do {
			System.out.println("inserisci email di un formato corretto (es. pippo123@gmail.com):");
			email = input.next();
			
			// https://stackoverflow.com/questions/8204680/java-regex-email 
			pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(email);
		}while(!matcher.matches());
		
		return email;
	}

	/*
	 * Verifica che credenziali siano registrate nel sistema.
	 * @param email
	 * @param password
	 * @return L'utente registrato in caso positivo, null altrimenti
	 * */
	public static UtenteRegistrato verificaLogin(String email, String password) {	
		for(int i=0;i<utentiRegistrati.size();++i) {
			UtenteRegistrato corrente = utentiRegistrati.get(i);
			if((corrente.getEmail().equals(email)) && (corrente.getPassword().equals(password))){
				System.out.println("Utente registrato");
				return corrente;
			}
		}
		
		return null;
	}
	
	public static String caricaRegolamentoDaFile() {
	    char[] in = new char[500000];
	    int size = 0;
	    String regolamento="";
	    
	    try {
	        File file = new File(percorsoRegolamento);
	        
	        FileReader fr = new FileReader(file);
	        size = fr.read(in);
	        for(int i=0; i<size; i++) {
	        	regolamento+=in[i];
	        }
	        regolamento+="\n";
	        fr.close();
	    } catch(IOException e) { 
	        e.printStackTrace();
	    }
	    
	    return regolamento;
	}
	
	public static void registraUtenteDaConsole(Scanner input) {		
		System.out.println("Inserisci Nome:");
		String nome = input.next();
		
		System.out.println("Inserisci Cognome:");
		String cognome = input.next();
	
		System.out.println("Inserisci Email:");
		String email = convalidaEmail(input);
		
		System.out.println("Inserisci password:");		
		String password = input.next();
		
		utentiRegistrati.add(new UtenteRegistrato(nome,cognome,email,password));
	}
	
	public static void registraUtente(UtenteRegistrato utente) {
		utentiRegistrati.add(utente);
	}
		
	private static void schermataPrincipale(Scanner input) {
		Matcher matcher = null;
		String s = null;
		
		do {
			System.out.println("Premere:");
			System.out.println("[I]nizia: per giocare direttamente la partita");
			System.out.println("[R]egolamento: per consultare il regolamento prima di iniziare la partita");
			s = input.next();
	        
			Pattern pattern = Pattern.compile("[R|I]", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(s);
			
			if(s.toLowerCase().equals("r")) {
				consultaRegolamento(input);
			}
		} while(!matcher.matches());
	}

	public static void consultaRegolamento(Scanner input) {
		// stampa regolamento
		System.out.println(caricaRegolamentoDaFile());
		
		System.out.println("Premere un tasto per iniziare la partita");
		input.next();
	}
	
	public static void abbandonaPartita() {
		System.exit(1);
	}
	
		
	}

