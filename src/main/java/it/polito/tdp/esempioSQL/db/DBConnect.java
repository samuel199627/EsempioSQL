package it.polito.tdp.esempioSQL.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnect {
	
	//varaibile di tipo connection che e' la mia connessione al database
	public static Connection getConnection() throws SQLException {
		//questa e' la stringa di connessione
		String jdbcURL = "jdbc:mysql://localhost/babs?user=root&password=Caraglio199627" ;
		//noi il drivermanager non lo vediamo neanche, e' un'interfaccia che non vediamo 
		//e che java gestisce in maniera automatica
		return DriverManager.getConnection(jdbcURL) ;
	}

}
