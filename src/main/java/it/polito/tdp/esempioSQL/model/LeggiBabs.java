package it.polito.tdp.esempioSQL.model;

import java.util.List;

import it.polito.tdp.esempioSQL.db.BabsDAO;

public class LeggiBabs {
	
	public void run() {
		//questa classe e' quella che viene richiamata per eseguire la
		//query, interrogare il database e restituire il risultato
		//DAO= Data Access Object
		//e' un design che consiglia come dividere le classi in maniera organizzata
		//il model (quello che c'e' nel pacchetto) e' ignorante sul database
		//il DAO fa la query, ma non sa a cosa serve il dato, ci pensa il models
		//quindi la classe che chiamo con Dao la metto in un paccheto diverso dal model
		//nello stesso pacchetto del Dao abbiamo anche creato la classe che fa la connessione al database
		BabsDAO dao = new BabsDAO() ;
		
		List<Station> tutte = dao.listStation() ;
		
		//andiamo a stampare le stazioni che abbiamo importato dal database
		//e ne stampiamo il nome
		for(Station s: tutte) {
			System.out.println(s.getName()) ;
		}
		
		System.out.println("\n\n----\n\n") ;
		System.out.println("STAMPIAMO LE STAZIONI CON LANDMARK PASSATO COME PARAMETRO!\n") ;
		//andiamo ad importare quelle stazioni con un certo landmark che 
		//abbiamo passato come parametro
		List<Station> paloAlto = dao.listStationByLandmark("Palo Alto") ;
		for(Station s: paloAlto) {
			System.out.println(s.getName()) ;
		}

	}
	
	public static void main(String args[]) {
		LeggiBabs babs = new LeggiBabs() ;
		babs.run();
	}

}
