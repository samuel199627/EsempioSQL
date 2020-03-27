package it.polito.tdp.esempioSQL.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.esempioSQL.model.Station;

//separiamo il model dal database quindi in questo pacchetto mettiamo tutto quello che interroga al db
//creiamo una connessione in una classe a parte perche' in questa classe la chiamiamo in entrambi i metodi e quindi non aveva
//senso copiare due volte lo stesso codice

public class BabsDAO {
	
	public List<Station> listStation() {
		List<Station> result = new ArrayList<>() ;
		
		//istruzione che vogliamo fare connettendoci al database (in cui saltiamo due colonne)
		//l'order by name e' in ordine alfabetico
		//quella che dobbiamo prova in sequel pro per vedere se funziona e'
		//SELECT station_id, name, dockcount, landmark FROM station ORDER BY name
		String sql = "SELECT station_id, name, dockcount, landmark FROM station ORDER BY name" ;
				
		try {
			//questa riga puo' generare problemi
			Connection conn = DBConnect.getConnection() ;
			
			//qui passiamo l'istruzione SQL e la eseguiamo sotto si divide il caricamento dall'esecuzione perche' potrei dire
			//che la query che ho passato e' ancora incompleta tipo abbiamo per alcune colonne dei punti interrogativi che magari sono
			//valori che importo dall'utente e che devo settare all'istruzione SQL qui nel prepareStatement
			//con il punto interrogativo c'e' un esempio sotto
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			//qui la iseguiamo l'istruzione SQL sul driver che abbiamo passato prima
			ResultSet res = st.executeQuery() ;
			
			//res contiene il modo per accedere al risultato, cioe' dobbiamo estrarre il risultato
			//Contiene il modo in cui accedere il risultato nel senso che non ho dentro res tutte le righe
			//ma lui e' un cursore che punta ad una riga e inizialmente punta alla riga prima della prima riga
			//la riga precedente alla prima riga e' dove il cursore si posiziona inizialmente
			//.next() va alla riga successiva e restituisce un booleano che dice se la riga c'e' oppure no
			//next la prima volta  serve per far puntare il cursore correttamente alla prima riga dato che inizialmente
			//parte da una riga fittizia prima della prima riga
			//il ciclo while funziona anche se non c'e' un risultato perche' semplicemente con next vado subito
			//alla riga dopo l'ultima e dunque il while non viene neanche eseguito e non da problemi
			while(res.next()) {
				//ci creiamo i nostri oggetti finche' ho righe estratte e che sono da estrarre da risultato
				//estraggo con in argomento il nome della colonna del database
				Station s = new Station(res.getInt("station_id"),
						res.getString("name"),
						res.getInt("dockcount"),
						res.getString("landmark")) ;
				
				//aggiungo l'oggetto creato alla lista di stazioni che mi sono definito
				result.add(s) ;				
			}
			//chiudo lo statement cioe' la richiesta della query e almeno libero risorsa
			st.close();
			
			//qui senza necessariamente chiudere la connessione potrei creare un nuovo statement per fare 
			//la richiesta di eseguire una nuova query
					
			//chiudere la connessione e' molto importante perche' altrimenti
			//non posso avere infinite connessioni aperte e dopo un po' non mi
			//fara' piu' connettere. Se chiudo invece tutto okay
			conn.close();
			
			return result ;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null ;
	}

	
	public List<Station> listStationByLandmark(String landmark) {
		List<Station> result = new ArrayList<>() ;
		
		//ecco un'altra istruzione SQL in cui abbiamo un paramtro, il landmark, che passiamo come parametro
		//e lo notiamo perche' abbiamo un punto interrogativo che ci permette di dire che dobbiamo passare
		//un parametro
		//l'istruzione notiamo che e' una concatenazione di stringhe
		//quella che dobbiamo prima provare in SequelPro per vedere se funziona e' questa
		//SELECT station_id, name, dockcount, landmark FROM station WHERE landmark="Palo Alto" ORDER BY name
		String sql = "SELECT station_id, name, dockcount, landmark FROM station "
				+ "WHERE landmark=? ORDER BY name" ;
				
		try {
			//questa e' quella che puo' scatenare l'eccezione
			Connection conn = DBConnect.getConnection() ;
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			//ecco qui che settiamo il parametro prima di eseguire la query
			st.setString(1, landmark) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Station s = new Station(res.getInt("station_id"),
						res.getString("name"),
						res.getInt("dockcount"),
						res.getString("landmark")) ;
				
				result.add(s) ;				
			}
			st.close();
						
			conn.close();
			
			return result ;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null ;
	}
}
