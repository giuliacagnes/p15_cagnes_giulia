package it.unige.se.battaglianavale;

public class Cella {
	private final int x;
	private final int y;
	
	private boolean occupata;
	private boolean colpita;


	private int idNave;

	public Cella(int x, int y) {
		idNave = -1;
		this.x = x;
		this.y = y;
		
		occupata = false;
		colpita = false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void occupa(int idNave) {
		this.idNave = idNave;
		
		occupata = true;
	}
	
	public boolean eColpita() {
		return colpita;
	}
	
	public boolean eOccupata() {
		return occupata;
	}
	
	public int getIdNave() {
		return idNave;
	}
	
	public boolean attacca() {
		if(occupata) {
			colpita = true;
			return true;
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (occupata) {
			return "#";
		}
		return "~";
	}
}
