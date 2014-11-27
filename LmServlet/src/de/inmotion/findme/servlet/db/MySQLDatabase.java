package de.inmotion.findme.servlet.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Klasse zur Kapselung der Datenbankverbindung.
 * @author Tobias Wochinger
 *
 */
public class MySQLDatabase {
	
    private Connection connection;
    
    /**
     * Oeffnet eine Datenbankverbindung mittels JNDI.
     * Bietet den Vorteil, dass die Datenbankverbindung von Tomcat mittels DBCP
     * verwaltet werden.
     * 
     * Datenbankverbindung kann unter TOMCAT_HOME/conf/context.xml konfiguriert werden.
     */
    public MySQLDatabase() {
    	Context initContext;
		try {
			initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource dataSource = (DataSource) envContext.lookup("jdbc/lovemachine");
	        connection = dataSource.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

    }
    
    public Connection getConnection() {
    	return connection;
    }

}
