package de.inmotion.findme.servlet.resources;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.inmotion.findme.servlet.db.UserDbService;
import de.inmotion.findme.servlet.db.UserDbServiceImpl;

/**
 * Webservice-Schnittstelle um die Korrekt eines Nutzers zu pruefen.
 * Hierfuer wird keine Authentifizierung via Http Basic Authentication benoetigt.
 * @author Tobias Wochinger
 *
 */
@Path("login")
public class Login {
	
	/**
	 * Prueft die Login-Daten eines Nutzers.
	 * @param username Nutzername des Nutzers
	 * @param password Passwort des Nutzers
	 * @return <code>true</code> falls Login korrekt, sonst <code>false</code>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{username}/{password}")
	public Response login(@PathParam("username") final String username, 
			@PathParam("password") final String password) {
		UserDbService database = new UserDbServiceImpl();
		try {
			boolean correct =  database.isLoginCorrect(username, password);
			database.closeService();
			return Response.ok(correct).build();
		} catch (SQLException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Ueberpruft ob ein Nutzer mit diesen Nutzernamen vorhanden ist.
	 * @param userName Angefragter Name
	 * @return <code>true</code> falls User schon existiert, sonst <code>false</code>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validate/{userName}")
	public Response checkUserExistence(@PathParam("userName") final String userName) {
		UserDbService service = new UserDbServiceImpl();
		
		try {
			int existence = service.getUserId(userName);
			service.closeService();
			return Response.ok(existence > 0 ? true : false).build();
		} catch (SQLException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
