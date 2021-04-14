package it.polito.tdp.nobel.model;

import java.util.*;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	//inserisco le strutture dati che ci servono 
	private List<Esame> partenza;
	private Set<Esame> soluzioneMigliore;
	private double mediaSoluzioneMigliore;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.partenza = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		Set<Esame> parziale =  new HashSet<Esame>();
		soluzioneMigliore = new HashSet<Esame>();
		mediaSoluzioneMigliore = 0;
		//mi immagino poi un metodo fatto cosii...
		//cerca1(parziale,0,numeroCrediti);   non lo uso tropppo costoso
		cerca2(parziale,0,numeroCrediti);
		return soluzioneMigliore;	
	}
	
	
	//COMPLESSITA' N^2
	private void cerca2(Set<Esame> parziale, int L, int m) {
			//CASI_TERMINALI
			//******* parziale.sommaCrediti() >= m ( mi fermo subito!) *******
			int crediti = sommaCrediti(parziale);
			if(crediti > m) {
				return;
			}
			if(crediti == m) {
				double media = calcolaMedia(parziale);
					if(media > mediaSoluzioneMigliore) {
						soluzioneMigliore = new HashSet<>(parziale); // la sovrascrivo con quella parziale
						mediaSoluzioneMigliore = media;
					}
				return; // esco, non mi interessa piu andare avanti
			}
			//sicuramente crediti < m
			//******* L = N ( ho considerato tutti gli esami e non ci sono piu esami da aggiungere ) ******
			if(L == partenza.size()) {
				return;
			}
			//se non rientro nei casi di terminazione ....
			// partenza[L] è da aggiungere oppure no ? provo entrambe le cose 
			parziale.add(partenza.get(L));
			cerca2(parziale, L+1, m);
			
			parziale.remove(partenza.get(L));
			cerca2(parziale, L+1, m);
		}
	
	
	
	
	/*******************************
	 * COMPLESSITA N! ovviamente non la usiamo e scrivo sotto un modo alternativo
	 * ************************************************************************************
	 */
	
	private void cerca1(Set<Esame> parziale, int L, int m) {
		// TODO Auto-generated method stub
		//CASI_TERMINALI
		//******* parziale.sommaCrediti() >= m ( mi fermo subito!) *******
		int crediti = sommaCrediti(parziale);
		if(crediti > m) {
			return;
		}
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > mediaSoluzioneMigliore) {
				soluzioneMigliore = new HashSet<>(parziale); // la sovrascrivo con quella parziale
				mediaSoluzioneMigliore = media;
			}
			return; // esco, non mi interessa piu andare avanti
		}
		//sicuramente crediti < m
		//******* L = N ( ho considerato tutti gli esami e non ci sono piu esami da aggiungere ) ******
		if(L == partenza.size()) {
			return;
		}
		//se non rientro nei casi di terminazione ....
		// creo i sottoproblemi ( aggiungo i rami del mio problema )
		for(Esame e : partenza) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca1(parziale,L+1,m); // faccio ripartire la mia ricorsione
				parziale.remove(e); // backtracking
			}
		}
		
	}
	
	
	

	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
