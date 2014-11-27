package de.inmotion.findme.servlet.resources;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("position")
public class PositionRetrieval {

	
    /**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit lm
	 */
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("lm");

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPosition(final Position position){
		
			try {
				EntityManager em = factory.createEntityManager();
				em.getTransaction().begin();
				
				User userfromDB = em.find(User.class, position.getUserid());
				position.setUser(userfromDB);

				em.persist(position);
				em.getTransaction().commit();
				em.refresh(position);
				em.refresh(userfromDB);
				em.close();	
				
				return Response.ok(userfromDB).build();

				
			} catch (Exception e) {
				 return Response.serverError().entity(position).build();
			}

	}
	
	@POST
	@Path("/view")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPosition(User user){

		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Position position = em.find(Position.class, user.getPosition().getId());
			em.close();
			
			return Response.ok(position).build();

		} catch (Exception e) {

			return Response.serverError().entity(user).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePosition(User user){


		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			
			User userRetrieved =  em.find(User.class, user.getId());
			userRetrieved.getPosition().setLatitude(user.getPosition().getLatitude());
			userRetrieved.getPosition().setLongitude(user.getPosition().getLongitude());
			
			em.persist(userRetrieved);
			em.getTransaction().commit();
		
			em.close();
			
			return Response.noContent().build();

		} catch (Exception e) {

			return Response.serverError().entity(user).build();
		}
	}
	
	@POST
	@Path("/viewById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Position getPositionById(Position position){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("lm");
		EntityManager em = factory.createEntityManager();
		Position position2 = em.find(Position.class, position.getId());
	
		return position2;
	}

}
