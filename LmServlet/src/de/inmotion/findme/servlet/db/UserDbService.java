package de.inmotion.findme.servlet.db;

import java.sql.SQLException;
import java.util.List;

import de.inmotion.findme.servlet.pojo.Registration;

/**
 * Stellt Datenbankoperationen rund um Nutzer bereit.
 * @author Tobias Wochinger
 *
 */
public interface UserDbService {

	/**
	 * Schreibt einen neuen Nutzer in die Datenbank.
	 * @param registration Daten des neuen Benutzers
	 * @throws SQLException falls ein Fehler bei der Datenbankoperation auftritt
	 * @throws IllegalArgumentException Falls der Nutzer schon existiert oder 
	 * seine Daten fehlerhaft sind
	 */
	void insertUser(final Registration registration) throws SQLException;
	
	/**
	 * Liest aller Nutzernamen aus der Datenbank aus.
	 * @return Nutzernamen in textueller Form
	 * @throws SQLException falls ein Fehler bei der Datenbankoperation auftritt
	 */
	List<String> getUsernames() throws SQLException;
	
	/**
	 * Gibt die Datenbank-Id eines Nutzers zurueck.
	 * @param username Nutzer, dessen Id angefordert wird
	 * @return ID des Nutzers, <code>0</code> falls der Nutzer nicht existiert
	 * @throws SQLException falls ein Fehler bei der Datenbankoperation auftritt
	 */
	int getUserId(final String username) throws SQLException;
	
	/**
	 * Gibt an ob in der Datenbank ein entsprechender Eintrag existiert.
	 * @param username Nutzername des Nutzers
	 * @param password Passwort des Nutzers
	 * @return <code>true</code>, falls eine Datenbank Eintrag existiert
	 * bei dem Nutzernamen und Passwort uebereinstimmen. Sonst <code>false</code>.
	 * @throws SQLException falls ein Fehler bei der Datenbankoperation auftritt
	 */
	boolean isLoginCorrect(String username, String password) throws SQLException;
	
	/**
	 * Schliesst den Service.
	 *  und gibt die Datenbankverbindung an den Pool zurueck.
	 */
	void closeService();
	
}
