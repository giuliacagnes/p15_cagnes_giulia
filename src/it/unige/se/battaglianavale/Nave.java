package it.unige.se.battaglianavale;

import java.util.ArrayList;

public class Nave {
	private static int numeroNavi = 0;
	private final int lunghezza;
	private final int id;
	private boolean affondata;
	private ArrayList<Cella> celleOccupate;
	
	public Nave(int lunghezza) {
		this.lunghezza = lunghezza;
		this.id = numeroNavi++;
		
		this.affondata = false;
		celleOccupate = new ArrayList<>();
	}
	
	public boolean eAffondata() {
		if(!affondata) {	
			for (int i = 0; i < celleOccupate.size(); i++) {
				if(!celleOccupate.get(i).eColpita()) {
					return false;
				}
			}
			this.affondata = true;
		}
		return affondata;
	}

	public int getId() {
		return id;
	}

	public int getLunghezza() {
		return lunghezza;
	}
	
	public void occupaCella(Cella cellaDaOccupare) {
		celleOccupate.add(cellaDaOccupare);
	}
	
	@Override
	public String toString() {
		String ret = "";
		if(this.affondata)
			ret += "X ";
		for (int i = 0; i < lunghezza; i++) {
			ret+="#";
		}
		return ret;
	}
}
