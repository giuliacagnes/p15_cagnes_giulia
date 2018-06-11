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

	/*stringa contenente il percorso per trovare il file dove sono scritte le regole del gioco*/
    private static String percorsoRegolamento = "src/Regole-del-gioco";

	/**
	 * Main function 
	 * 
	 * @param args argomenti
	 */
	public static void main(String[] args) {
		//dichiarazione e inizializzazione parametri
		String s = null;
		Pattern pattern = null;
		Matcher matcher = null;
		
		//istanza di un oggetto di tipo Scanner che utlizzo in seguito per leggere dati da tastiera 
		Scanner input = new Scanner(System.in);
		
		// Aggiungo due utenti registrati per simulare persistenza
		UtenteRegistrato r = new UtenteRegistrato("pippo", "pluto", "pp@gmail.com", "111");
		registraUtente(r);
	
		registraUtente(new UtenteRegistrato("Giulia","Cagnes","giulia.cagnes@gmail.com","12345"));
		registraUtente(new UtenteRegistrato("Roberto","Bianchi","roberto.bianchi@gmail.com","54321"));
		
		//funzione per far effettuare il login e la registrazione agli utenti
		schermataLogin(input);
		
		//funzione che visualizza il menu principale che consiste in gioca nuova partita o leggere regole del gioco
		schermataPrincipale(input);
		
		//scelta paramentri,decisione concordata tra due giocatori prima di iniziare la partita 
		do {
			System.out.println("Inserisci lunghezze delle navi (es: [2,3,4] per avere 3 navi di dimensione 2,3 e 4 rispettivamente): ");
		    System.out.println("NB: la lunghezza massima delle navi e' 5. Puoi inserire fino ad un massimo 5 navi in campo.");
		    System.out.println("oppure premi la lettera [q]uit per terminare la partita");
			s = input.next();
	        if(s.toLowerCase().equals("q")) {
	        	abbandonaPartita();
	        }

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
	        //System.out.println(lunghezzaNavi[i]);
	        i++;
	    }
	    
		Giocatore giocatoreA = new Giocatore(utentiLoggati.get(0).getName(), lunghezzaNavi);
		Giocatore giocatoreB = new Giocatore(utentiLoggati.get(1).getName(), lunghezzaNavi);

		Partita partita = new Partita(giocatoreA,giocatoreB);

		// Fase di posizionamento navi
		do {
			System.out.println("Premere:");
			System.out.println("[R]andom: per disporre le navi automaticamente in modo casuale nel campo");
			System.out.println("[M]anuale: per disporre le navi manualmente nel campo");
			s = input.next();
	        
			pattern = Pattern.compile("[R|M]", Pattern.CASE_INSENSITIVE); //scelta tra posizionaNaviRandom o PosizionaNaviManualmente
			matcher = pattern.matcher(s);
		} while(!matcher.matches());
		
		if(s.toLowerCase().equals("r")){
			partita.posizionaNaviRandom();
		} else {
			partita.posizionaNaviManualmente(input);
		}
		
		// Fase di gioco
		System.out.println("ricorda! premi la lettera [q]uit per abbandonare la partita");
		while(!partita.eTerminata()) {
			boolean haColpito = true;

			System.out.println(partita);
			System.out.println("Spara un colpo!"); //inserire le cordinate per colpire una cella 
			System.out.println("inserisci delle coordinate [1,3] :");
			System.out.println("[#riga,#colonna]");
			
	        s = input.next();
	        if(s.toLowerCase().equals("q")) {
	        	abbandonaPartita();
	        }
	        
			pattern = Pattern.compile("(\\d),(\\d)");
			matcher = pattern.matcher(s);
			
			if (matcher.matches()) {
				int y = Integer.parseInt(matcher.group(1));
				int x = Integer.parseInt(matcher.group(2));
			
				haColpito = partita.attacca(x, y);
			}	
			
			if(!haColpito) { // se la cella che ha attaccato non contiene la nave allora cambia turno giocatore
				partita.cambiaTurno();	
			}
		}
		input.close();
		System.out.println("--->>>> Il giocatore "+ partita.getDiTurno() +" ha vinto la partita di Battaglia Navale!!!<<<<---- =)");
		System.exit(0);
	}
	/**
	 * Schermata di Login che consente all'utente di scegliere se registrarsi e effettuare il login oppure di loggarsi direttamente.
	 * @param input
	 */
	
	private static void schermataLogin(Scanner input) {
		do {
			String sceltaUtente;
			
			Matcher matcher;
			do {
				System.out.println(">>>>> Benvenuto nel gioco di Battaglia Navale online! <<<<<");				
				System.out.println("Premere:");
				System.out.println("[R]egistrati come nuovo utente e poi effettua il log in");
				System.out.println("[L]og in");
				sceltaUtente = input.next();
		        
				Pattern pattern = Pattern.compile("[R|L]", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(sceltaUtente);
			} while(!matcher.matches());
			
	
			if(sceltaUtente.toLowerCase().equals("r")) {
				registraUtenteDaConsole(input);
				System.out.println("Login primo giocatore:");
				loginUtenteDaConsole(input);
			} else { 
				System.out.println("Login primo giocatore:");
				loginUtenteDaConsole(input);
			}
				do {
								
					System.out.println("Secondo giocatore...Premere:");
					System.out.println("[R]egistrati come nuovo utente e poi effettua il log in");
					System.out.println("[L]og in");
					sceltaUtente = input.next();
			        
					Pattern pattern = Pattern.compile("[R|L]", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(sceltaUtente);
				} while(!matcher.matches());
				if(sceltaUtente.toLowerCase().equals("r")) {
					registraUtenteDaConsole(input);
					System.out.println("Login secondo giocatore:");
					loginUtenteDaConsole(input);
				
				}else {
				System.out.println("login secondo giocatore");
				loginUtenteDaConsole(input);		
				}
			
		}while(utentiLoggati.size() < 2);
	}
	/**
	 *  Login dell'utente inserendo email e password 
	 * @param input
	 */

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
		System.out.println("Login effettuato correttamente!");
	}
	
	/**
	 * Verifica che il formato email inserito sia corretto come spiegato nell'esempio
	 * @param input
	 * @return email
	 */
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

	/**
	 * Verifica che credenziali siano registrate nel sistema.
	 * @param email
	 * @param password
	 * @return L'utente registrato in caso positivo, null altrimenti
	 */
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
	
	/**
	 * Legge dal file regoleDelGioco il regolamento del gioco
	 * @return regolamento
	 */
	public static String caricaRegolamentoDaFile() {
	    char[] in = new char[500000];
	    int size = 0;
	    String regolamento="";
	    
	    try {
	        File regoleDelGioco = new File(percorsoRegolamento);
	        
	        FileReader fr = new FileReader(regoleDelGioco);
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
	
	/**
	 * Registra utente da console permette all'utente di registrarsi e di essere aggiunto alla
	 * lista degli utenti registrati
	 * @param input
	 */
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
		System.out.println("Ti sei registrato correttamente! Ora effettua il login..");
	}
	
	public static void registraUtente(UtenteRegistrato utente) {
		utentiRegistrati.add(utente);
	}
		
	/**
	 * Schermata principale consente di scegliere se leggere il regolamento o di iniziare
	 * direttamente a giocare una nuova partita
	 * @param input
	 */
	private static void schermataPrincipale(Scanner input) {
		Matcher matcher = null;
		String s = null;
		
		do {
			System.out.println("Premere:");
			System.out.println("[I]nizia: per giocare una nuova partita di battaglia navale online ");
			System.out.println("[R]egolamento: per consultare il regolamento prima di iniziare la partita");
			s = input.next();
	        
			Pattern pattern = Pattern.compile("[R|I]", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(s);
			
			if(s.toLowerCase().equals("r")) {
				consultaRegolamento(input);
			}
		} while(!matcher.matches());
	}
    /**
     * Cosulta Regolamento stampa a schermo le regole del gioco 
     * @param input
     */
	public static void consultaRegolamento(Scanner input) {
		// stampa regolamento
		System.out.println(caricaRegolamentoDaFile());
		
		System.out.println("Premere una lettera a scelta per iniziare la partita");
		input.next();
	}
	/**
	 * Abbandona partita permette all'utente di terminare la partita in qualunque momento nella fase di gioco
	 */
	public static void abbandonaPartita() {
		System.out.println("Partita Terminata!!!! un giocatore ha abbandonato la partita di battaglia navele in corso! ");
		System.exit(1);
	}
	
		
	}

