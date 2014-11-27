package de.inmotion.findme.servlet;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Hautpklasse des Servlets.
 * Hier koennen globale Konfigurationen gemacht werden.
 * @author Tobias Wochinger
 *
 */
public class RestApplication extends ResourceConfig {

	/**
	 * Erstellt eine neue Webapplikation.
	 */
	public RestApplication() {
		super();
		packages("de.inmotion.findme.servlet");
		//register(AuthenticationFilter.class);
	}
}
