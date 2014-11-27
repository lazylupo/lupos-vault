package de.inmotion.findme.servlet.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.inmotion.findme.servlet.db.UserDbService;
import de.inmotion.findme.servlet.db.UserDbServiceImpl;

/**
 * Schnittstelle fuer alle Requests, die Nutzer betreffen.
 * @author Tobias Wochinger
 *
 */

@Path("user")
public class Users {
	
	/**
	 * Liefert alle vorhandenen Nutzernamen zurueck.
	 * @return Nutzernamen als Liste von Strings
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		UserDbService service = new UserDbServiceImpl();
		
		try {
			List<String> users = service.getUsernames();
			service.closeService();
			return Response.ok(users).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
