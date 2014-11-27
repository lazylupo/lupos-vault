package de.inmotion.findme.servlet.pojo;

/**
 * Kapselt die verschiedenen Eigenschaften eine Registrierung.
 * Wird unter anderm von Jackson zur automatischen JSON-Konvertierung genutzt.
 * @author Tobias Wochinger
 *
 */
public class Registration {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
