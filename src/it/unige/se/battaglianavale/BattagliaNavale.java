package it.unige.se.battaglianavale;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unige.se.battaglianavale.Utils.Direzione;

public class BattagliaNavale {
	private static ArrayList<UtenteRegistrato> utenti = new ArrayList<UtenteRegistrato>();
	private static ArrayList<UtenteRegistrato> utentiLoggati = new ArrayList<UtenteRegistrato>();

	public static void main(String[] args) {
		
		utenti.add(new UtenteRegistrato("giulia","cagnes","giulia.cagnes@gmail.com","12345"));
		utenti.add(new UtenteRegistrato("roberto","bianchi","roberto.bianchi@gmail.com","54321"));
		
		Scanner input = new Scanner(System.in);
		login(input);
		login(input);
		
		boolean posizionaRandom = true;
		
		int[] lunghezzaNavi = {5,3,2,5,3};

		Giocatore giocatoreA = new Giocatore(utentiLoggati.get(0).getName(), lunghezzaNavi);
		Giocatore giocatoreB = new Giocatore(utentiLoggati.get(1).getName(), lunghezzaNavi);
		
		Partita p = new Partita(giocatoreA,giocatoreB);

		Giocatore diTurno = p.getDiTurno();
		
		// Posiziona Navi
		if(posizionaRandom) {
			p.posizionaNaviRandom();
		} else {
			do {
				System.out.println(diTurno);
				System.out.println(diTurno.getCampo());
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
					
				} 
				if (!diTurno.haNaviDaPosizionare()) {
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
			
			System.out.println("inserisci password:");
			
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
