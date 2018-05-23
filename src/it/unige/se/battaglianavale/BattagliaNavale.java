/*
 * Giulia Cagnes
 * 
 * Version 0.1 (beta)
 */
package it.unige.se.battaglianavale;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unige.se.battaglianavale.Utils.Direzione;

/**
 * Classe Battaglia Navale contenente il main utile a mostrare tutte le funzionalità.
 * 
 * @version     0.1  21 May 2018
 * @author      Giulia Cagnes
 *
 */

public class BattagliaNavale {
	/** ArrayList contiene utenti*/
	private static ArrayList<UtenteRegistrato> utenti = new ArrayList<UtenteRegistrato>();
	
	/**ArrayList conitene utenti loggati, associazione fra battaglia navale e utente registrato*/
	private static ArrayList<UtenteRegistrato> utentiLoggati = new ArrayList<UtenteRegistrato>();

	
	/**
	 * Main function 
	 * 
	 * @param args argomenti
	 */
	public static void main(String[] args) {
		utenti.add(new UtenteRegistrato("Giulia","Cagnes","giulia.cagnes@gmail.com","12345")); // aggiungo due utenti registrati
		utenti.add(new UtenteRegistrato("Roberto","Bianchi","roberto.bianchi@gmail.com","54321"));
		
		
		Scanner input = new Scanner(System.in);
		login(input);// login degli utenti
		login(input);
		
		boolean posizionaRandom = false;
	
		String si = null;
		Pattern pat = null;
		Matcher mi = null;
		
		
		do {
			System.out.println("Inserisci lunghezze delle navi: ");
			System.out.println("(es: [2,3,4] per avere 3 navi di dimensione 2,3 e 4 rispettivamente.)");
		    System.out.println("Nota: lunghezza massima nave e' 5 e minima 1, puoi insierire da 1 fino a massimo 5 navi in campo.");
			si = input.next();

			pat = Pattern.compile("^([1|2|3|4|5],){0,4}([1|2|3|4|5])$");
			mi = pat.matcher(si);
		} while(!mi.matches());
		
		
		// trasforma la string in input in un array di stringhe (usando la virgola come separatore) es. 3,4,2 -> [3 4 2]
		String[] lunghezzaNaviStr = si.split(",");
		
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

		Partita p = new Partita(giocatoreA,giocatoreB);
		
		Giocatore diTurno = p.getDiTurno();
//		int i=0;
		
//		for(String lunghezzaNave:si.split(",")){
//			lunghezzaNavi[i]=Integer.parseInt(lunghezzaNave);
//			System.out.println(lunghezzaNavi[i]);
	//		i++;
//		}

		do {
			System.out.println("inserisci: -> 1 se vuoi scacchiera navi random ");
			System.out.println("           -> 2 se vuoi posizionare navi manualmente");
			si = input.next();
	        
			pat = Pattern.compile("[1|2]");
			mi = pat.matcher(si);
		} while(!mi.matches());
		
		if(si.equals("1")){
			posizionaRandom = true;
		} else {
			posizionaRandom = false;
		}
		// Posiziona Navi			
		if(posizionaRandom) {
				p.posizionaNaviRandom();		
		} else {
			
			do {
				System.out.println(diTurno);
				System.out.println(diTurno.getCampo());

				System.out.println("Posizionamento Navi...");
				System.out.println("inserisci delle coordinate e una direzione es. [1,3,U] :");
				System.out.println("[#riga, #colonna, direzione possibile: U_= UP , D = DOWN, L = LEFT, R = RIGHT]");
			
				String s = input.next();
	        
				Pattern pattern = Pattern.compile("(\\d),(\\d),([U|D|L|R])");
				Matcher m = pattern.matcher(s);
			
					if (m.matches()) {
						int y = Integer.parseInt(m.group(1));
						int x = Integer.parseInt(m.group(2));
						String dir = m.group(3);
				
						switch (dir) {
						case "U":
							diTurno.posizionaNave(x, y, Direzione.SU);
							break;
						case "D":
							diTurno.posizionaNave(x, y, Direzione.GIU);
							break;
						case "L":
							diTurno.posizionaNave(x, y, Direzione.SINISTRA);
							break;
						case "R":
							diTurno.posizionaNave(x, y, Direzione.DESTRA);
							break;
						}
				
					} if (!diTurno.haNaviDaPosizionare()) {
						diTurno = p.cambiaTurno();
					}
				} while (diTurno.haNaviDaPosizionare());
		}
		
		// Fase di gioco
		while(!p.eTerminata()) {
			boolean haColpito = true;

			System.out.println(p);
			System.out.println("inserisci delle coordinate [1,3] :");
			System.out.println("[#riga,#colonna]");
			
	        String s = input.next();
	        
			Pattern pattern = Pattern.compile("(\\d),(\\d)");
			Matcher m = pattern.matcher(s);
			
			if (m.matches()) {
				int y = Integer.parseInt(m.group(1));
				int x = Integer.parseInt(m.group(2));
			
				haColpito = p.attacca(x, y);
			}
				
			
			if(!haColpito) {
				p.cambiaTurno();	
			}
			
		}
		input.close();
	}
	
	public static void login(Scanner input) {
		String email = null;
		String password = null;
		
		UtenteRegistrato u = null;
		do {
			System.out.println("inserisci email:");
			
			String s = input.next();
			
			/* https://stackoverflow.com/questions/8204680/java-regex-email */
		 	Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		 	
			Matcher m = pattern.matcher(s);
			
			if (!m.matches()) {
			
				System.out.println("Inserire nuovamente un email di un formato corretto [es. pippo123@gmail.com]");
				continue;
			}
			email = s;
			
			System.out.println("Inserisci password:");
			
			password = input.next();
			
			u = verificaLogin(email, password);
		}while(u == null);
		
		utentiLoggati.add(u);
	}
	
	public static UtenteRegistrato verificaLogin(String email, String password) {
		
		for(int i=0;i<utenti.size();++i) {
			UtenteRegistrato corrente = utenti.get(i);
			if((corrente.getEmail().equals(email)) && (corrente.getPassword().equals(password))){
				System.out.println("Utente registrato");
				return corrente;
			}
		}
		System.out.println("Utente non registrato");
		return null;
	}
	
		
		
		
		
		
		
		
		
		
	}

