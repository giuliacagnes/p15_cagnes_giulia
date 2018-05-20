package it.unige.se.battaglianavale;

import it.unige.se.battaglianavale.Utils.StatoCoordinata;

public class Partita {
	private Giocatore giocatoreA;
	private Giocatore giocatoreB;
	
	private Giocatore diTurno;
	private Giocatore vincitore;
	
	public Partita(Giocatore giocatoreA,Giocatore giocatoreB) {
		this.giocatoreA = giocatoreA;
		this.giocatoreB = giocatoreB;

		diTurno = giocatoreA;
		vincitore = null;
	}
	
	public boolean eTerminata() {
		if(!giocatoreA.haNaviInGioco()) {
			this.vincitore = giocatoreB;
		} else if(!giocatoreB.haNaviInGioco()) {
			this.vincitore = giocatoreA;
		} else {
			return false;
		}
		
		return true;
	}
	
	public Giocatore getDiTurno() {
		return diTurno;
	}
	
	public Giocatore cambiaTurno() {
		if(diTurno == giocatoreA) {
			diTurno = giocatoreB;
		} else {
			diTurno = giocatoreA;
		}
		return diTurno;
	}
	
	public boolean attacca(int x, int y) {
		Campo campoAvversario = null;
		if(diTurno == giocatoreA)
			campoAvversario = giocatoreB.getCampo();
		else
			campoAvversario = giocatoreA.getCampo();
		
		if(campoAvversario.attaccaCella(x,y)) {
			diTurno.registraMossa(x,y,StatoCoordinata.COLPITO);
			return true;
		}
		diTurno.registraMossa(x,y,StatoCoordinata.MANCATO);
		return false;
	}
	
	public void posizionaNaviRandom() {
		diTurno.posizionaNaviRandom();
		this.cambiaTurno();
		diTurno.posizionaNaviRandom();
		this.cambiaTurno();
	}
	
	@Override
	public String toString() {
		String ret = new String();
		
		ret += "Giocatore: " + diTurno;
		ret += "\n";
		
		Utils.StatoCoordinata matrix[][] = diTurno.getMemoriaMosse();
		int dim = matrix.length;
		for (int i = 0; i < matrix.length; i++) {
		    for (int j = 0; j < matrix[i].length; j++) {
		    	ret += matrix[i][j] + "   ";
		    }
		    ret += " " + i + "\n\n";
		}
		for (int j = 0; j < matrix[dim-1].length; j++) {
	    	ret += j + "   ";
	    }
		ret += "\n";
		
		return ret;
	}
}
