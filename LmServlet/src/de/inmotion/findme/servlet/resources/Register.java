package de.inmotion.findme.servlet.resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.inmotion.findme.servlet.db.UserDbService;
import de.inmotion.findme.servlet.db.UserDbServiceImpl;
import de.inmotion.findme.servlet.pojo.Registration;

/**
 * Schnittstelle zum Registrieren eines neuen Nutzers.
 * Hierbei wird keine Authentifizierung via Http Basic Authentication benoetigt.
 * @author Tobias Wochinger
 *
 */
@Path("registrate")
public class Register {

	/**
	 * Webservice um einen neuen Nutzer zu registrieren.
	 * @param registration Registrierungsdaten
	 * @return Status ob die Operation erfolgreich war.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrate(final Registration registration) {
		UserDbService service = new  UserDbServiceImpl();
		try {
			service.insertUser(registration);
			service.closeService();
			return Response.status(Status.CREATED).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (IllegalArgumentException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
