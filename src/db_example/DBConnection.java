package db_example;
/*
DBConnection: Eine Klasse um eine Datenbankverbindung
zu erstellen und SQL-Abfragen durchzufuehren.

Copyright (C) 2011 Karsten Blauel

Dieses Programm ist freie Software. Sie koennen es unter den Bedingungen der GNU General Public License,
wie von der Free Software Foundation veroeffentlicht, weitergeben und/oder modifizieren, entweder gemaess
Version 3 der Lizenz oder (nach Ihrer Option) jeder spaeteren Version.
Die Veroeffentlichung dieses Programms erfolgt in der Hoffnung, dass es Ihnen von Nutzen sein wird, aber
OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FUER
EINEN BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben.
Falls nicht, siehe <http://www.gnu.org/licenses/>.
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBConnection {
	
	private String driver = "com.mysql.jdbc.Driver";	// Datenbank-Treiber
	private String dbTyp = "jdbc:mysql";				// DB-Connection-Prefix
	private String dbPort = "3306";						// Port der Datenbank
	private String dbName = "testarduino";				// Datenbankname
	private String dbUser = "arduinoboy";				// Benutzername
	private String dbIpAdress = "localhost";	 		// Adresse der Datenbank
	private String dbUserPasswd = "ardrapsknubbel";		// Kennwort des Benutzers
	
	private String sqlStatement = ""; //"SELECT * FROM users";	// SQL-Statement

	public DBConnection()
	{
		setDriver(driver);			// Datenbank-Treiber
		setDbTyp(dbTyp);				// DB-COnnection-Prefix
		setDbPort(dbPort);				// Port der Datenbank
		setDbName(dbName); 				// Datenbankname
		setDbUser(dbUser); 				// Benutzername
		setDbIpAdress(dbIpAdress); 		// Adresse der Datenbank
		setDbUserPasswd(dbUserPasswd);	// Kennwort des Benutzers
	}
	
	/**
	 * Anfrage senden und RecordSet zurueckgeben
	 * @return
	 */
	public ResultSet doRequest()
	{
		return doRequest(sqlStatement);
	}
	
	public int doUpdate()
	{
		return doUpdate(sqlStatement);
	}

	/**
	 * Anfrage senden und RecordSet zurueckgeben
	 * @param sqlStatement
	 * @return
	 */
	public ResultSet doRequest(String sqlStatement)
	{
//		System.out.println(sqlStatement);
		
		Connection con = null;		// Connection-Objekt, stellt Verbindung mit Datenbank her
		Statement statement = null;	// Statement-Objekt, fuer SQL-Ausdruecke und Abgragen zustaendig
		ResultSet rSet = null;		// ResultSet-Objekt nimmt die angefragten Daten auf
		String connectExpr = dbTyp+"://"+dbIpAdress+":"+dbPort+"/"+dbName;
		
//		System.out.println("Verbindungsstring="+connectExpr);
		
		try {
			Class.forName(driver);	// Der JDBC-Treiber wird geladen 
		}
		catch (ClassNotFoundException e1) { e1.printStackTrace(); }	// Fehlerbehandlung
		
		try {
			// Stelle Verbindung mit MySQL-Datenbank wvs-java-db her.
			
//			System.out.println("Verbinden mit: "+connectExpr+", "+dbUser+", "+dbUserPasswd);
			con = DriverManager.getConnection(connectExpr, dbUser, dbUserPasswd);
		}
		catch (SQLException e1)	// Fehlerbehandlung
		{
			JOptionPane.showMessageDialog(null, "<html>Fehler beim Öffnen der Datenbank.<br>Bitte informieren Sie den Administrator!</html>");

			e1.printStackTrace();
		}	
		
		try {
			statement = con.createStatement();	// initialisiere statemanent-Instanz ueber Factory-Methode von Connection-Objekt
			
			// Erzeuge SQL-Statement mittels Zeichenkette ...
			// und fuere die Anfrage direkt aus ...
			// schreibe das Ergebnis der Anfrage in den RecordSet rSet
			rSet = statement.executeQuery(sqlStatement);
		}
		catch (SQLException e)
		{
//			JOptionPane.showMessageDialog(null, "<html>Fehler beim Öffnen der Datenbank.<br>Bitte informieren Sie den Administrator!</html>");

			e.printStackTrace();
			
		}	// Fehlerbehandlung
		
		return rSet;	// Ergebniss-Set zurueckgeben
	}
	
	/**
	 * Anfrage senden und RecordSet zurueckgeben
	 * @param sqlStatement
	 * @return
	 */
	public int doUpdate(String sqlStatement)
	{
//		System.out.println(sqlStatement);
		
		Connection con = null;		// Connection-Objekt, stellt Verbindung mit Datenbank her
		Statement statement = null;	// Statement-Objekt, fuer SQL-Ausdruecke und Abgragen zustaendig
		int retV = 0;		// ResultSet-Objekt nimmt die angefragten Daten auf
		
		try {
			Class.forName(driver);	// Der JDBC-Treiber wird geladen 
		}
		catch (ClassNotFoundException e1) { e1.printStackTrace(); }	// Fehlerbehandlung
		
		try {
			// Stelle Verbindung mit MySQL-Datenbank wvs-java-db her.
			con = DriverManager.getConnection(dbTyp+"://"+dbIpAdress+":"+dbPort+"/"+dbName, dbUser, dbUserPasswd);
		}
		catch (SQLException e1) { e1.printStackTrace(); }	// Fehlerbehandlung
		
		try {
			statement = con.createStatement();	// initialisiere statemanent-Instanz ueber Factory-Methode von Connection-Objekt
			
			// Erzeuge SQL-Statement mittels Zeichenkette ...
			// und fuere die Anfrage direkt aus ...
			// schreibe das Ergebnis der Anfrage in den RecordSet rSet
			retV = statement.executeUpdate(sqlStatement);
		}
		catch (SQLException e) { e.printStackTrace(); }	// Fehlerbehandlung
		
		return retV;	// Ergebniss-Set zurueckgeben
	}

	// Getter und Setter fuer die benoetigten Connection-Variablen
	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setDbTyp(String dbTyp) {
		this.dbTyp = dbTyp;
	}

	public void setDbIpAdress(String dbIpAdress) {
		this.dbIpAdress = dbIpAdress;
	}
	
	public String getDbIpAdress() {
		return dbIpAdress;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public void setDbUserPasswd(String dbUserPasswd) {
		this.dbUserPasswd = dbUserPasswd;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}
}
