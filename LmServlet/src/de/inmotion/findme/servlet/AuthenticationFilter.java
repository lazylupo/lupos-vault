package de.inmotion.findme.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.DatatypeConverter;

import de.inmotion.findme.servlet.db.UserDbService;
import de.inmotion.findme.servlet.db.UserDbServiceImpl;

/**
 * Realisiert die Authentifizierung mit HTTP Basic Authentication.
 * @author Tobias Wochinger
 *
 */
public class AuthenticationFilter implements ContainerRequestFilter {
	
	public static final int NAME = 0;
	public static final int PASSWORD = 1;
	
	public static final String AUTHORIZATION_KEY = "Authorization";
	
	/** Ressourcen bei denen keine Authentifizierung noetig ist. */
	public static final String[] EXCLUDED_RESOURCES = new String[] {"registrate", "login"};
	
	
	@Override
	public void filter(final ContainerRequestContext containerRequest)
			throws IOException {
		
		String firstUrlSegment = getFirstPathSegment(containerRequest);
		
		if (!isSegmentExcludedFromAuthorization(firstUrlSegment)) {
			validateAuthentication(containerRequest);
		}
	}
	
	/**
	 * Gibt das erste Segment der angeforderten Ressource zueruck.
	 * Beispie: Request auf www.beispiel.de/erstesSegment/zweitesSegment/.
	 * @param containerRequest Context des aktuellen Requests
	 * @return Erstes Segment der URL.
	 */
	private String getFirstPathSegment(
			final ContainerRequestContext containerRequest) {
		UriInfo uri = containerRequest.getUriInfo();
		PathSegment firstSegment = uri.getPathSegments().get(0);
		return firstSegment.toString();
	}

	/**
	 * Validiert den Request via Http Basic Authentication.
	 * Nutzername und Passwort muessen in der Datenbank stehen.
	 * @param containerRequest Context des Requests
	 */
	private void validateAuthentication(final ContainerRequestContext containerRequest) {
		String authentication = containerRequest
				.getHeaderString(AUTHORIZATION_KEY);

		if (authentication == null) {
			abort(containerRequest);
		}else

		{
			String[] dividedAuthentication = decode(authentication);
	
			if (dividedAuthentication == null || dividedAuthentication.length != 2|| !isUserValid(dividedAuthentication)) {
				abort(containerRequest);
			}
		}
	}
	
	/**
	 * Bricht den Request mit einer Fehlermeldung ab.
	 * @param context Gefilterter ContainerRequestContext
	 */
	private void abort(final ContainerRequestContext context) {
		context.abortWith(Response
				.status(Response.Status.UNAUTHORIZED)
				.entity("User cannot access the resource.")
				.build());
	}
	
	/**
	 * Wandelt den Header-Value von "authorization" in ein lesbares Format um.
	 * @param auth header-value für den key "authorization"
	 * @return String-Arrray, das in erster Position den Login-Namen 
	 * und in 2ter Position das Passwort enthält.
	 */
	public String[] decode(final String auth) {
		// Replacing "Basic THE_BASE_64" to "THE_BASE_64" directly
		String cleanAuth = auth.replaceFirst("[B|b]asic ", "");

		// Decode the Base64 into byte[]
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(cleanAuth);
		String[] result = null;
		// If the decode fails in any case
		if (decodedBytes != null && decodedBytes.length > 0) {
			result = new String(decodedBytes).split(":", 2);
		}

		// Now we can convert the byte[] into a splitted array :
		// - the first one is login,
		// - the second one password
		return result;
	}
	
	/**
	 * Checkt ob User und Passwort valide sind.
	 * @param authentication Authentifizerungsdaten des Nutzers
	 * @return <code>true</code> falls valide, sonst <code>false</code>
	 */
	private boolean isUserValid(final String [] authentication) {
		UserDbService service = new UserDbServiceImpl();
		try {
			boolean result = service.isLoginCorrect(authentication[NAME], authentication[PASSWORD]);
			service.closeService();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Ueberprueft ob die angeforderte Ressource von der Authentifizerung ausgeschlossen ist.
	 * @param firstSegment Erstes Segment der URL
	 * @return <code>true</code> falls ausgeschlossen, sonst <code>false</code>
	 */
	private boolean isSegmentExcludedFromAuthorization(final String firstSegment) {
		boolean excluded = false;
		
		int counter = 0;
		while (!excluded && counter < EXCLUDED_RESOURCES.length) {
			excluded = firstSegment.equals(EXCLUDED_RESOURCES[counter]);
			counter++;
		}
		return excluded;
	}
}
