package de.inmotion.findme.servlet.db;

import java.sql.SQLException;

import de.inmotion.findme.servlet.resources.Position;

/**
 * Bietet Datenbankoperationen an, die Nutzerorte betreffen.
 * @author Tobias Wochinger
 *
 */
public interface CoordinateDbService {

	/**
	 * Schreibt die Position eines Nutzers in die Datenbank.
	 * @param position Neue Position, die  gespeichert werden soll
	 * @param username Nutzername des Nutzers, der an dieser Position war
	 * @throws SQLException falls ein Fehler bei der Datenbankoperation auftritt
	 * @throws IllegalArgumentException Falls der Nutzer, dessen Koordinaten
	 * gespeichert werden sollen nicht existiert.
	 */
	void insertPosition(final Position position, final String username) throws SQLException;
	
	/**
	 * Liest die letzte Position eines Nutzers aus der Datenbank.
	 * @param username Nutzer, dessen Position gelesen werden soll
	 * @return Aktuellste Position des Nutzers
	 * @throws SQLException falls ein Fehler bei der Datenbankoperation auftritt
	 */
	Position getLatestPosition(final String username) throws SQLException;
	
	/**
	 * Schliesst den Service.
	 *  und gibt die Datenbankverbindung an den Pool zurueck.
	 */
	void closeService();
}
