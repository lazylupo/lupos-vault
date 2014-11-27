package de.inmotion.findme.servlet.resources;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.internal.process.AsyncContext.Factory;


@Path("user")
public class UserRetrieval {

	
    /**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit lm
	 */
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("lm");

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(final User user){
		
    	if(checkUsername(user.getUsername())==false){
			try {
				EntityManager em = factory.createEntityManager();
				em.getTransaction().begin();
				user.getPosition().setUser(user);
				em.persist(user);
				em.getTransaction().commit();

				em.refresh(user);

				em.close();	
				
				return Response.ok(user).build();

				

				
			} catch (Exception e) {
				 return Response.serverError().entity(user).build();
			}
    	}else return Response.noContent().build();
	}
	
	@POST
	@Path("/view")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(User user){

		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			Query q = em.createQuery("SELECT usr from User usr WHERE usr.username LIKE :username AND usr.password LIKE :password")
					  .setParameter("username", user.getUsername())
					  .setParameter("password", user.getPassword());
			User userRetrieved  = (User) q.getSingleResult();
			
			
			if(user.getPosition()!=null){
				userRetrieved.getPosition().setLatitude(user.getPosition().getLatitude());
				userRetrieved.getPosition().setLongitude(user.getPosition().getLongitude());
			}
			
			if(user.getRegistrationId()!= null && user.getRegistrationId().compareTo(userRetrieved.getRegistrationId())!=0){
				
				userRetrieved.setRegistrationId(user.getRegistrationId());
			}
			
			em.persist(userRetrieved);
			em.getTransaction().commit();
			em.refresh(userRetrieved);

			em.close();
			userRetrieved.setPassword(null);
			return Response.ok(userRetrieved).build();
		} catch (Exception e) {

			return Response.noContent().build();
		}
	}
	
	@POST
	@Path("/viewById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUserById(User user){

		try {
			EntityManager em = factory.createEntityManager();
			User userRetrieved = em.find(User.class, user.getId());
			em.close();
			
			return Response.ok(userRetrieved).build();
		} catch (Exception e) {
			
			return Response.serverError().entity(user).build();
		}
	}
	
	public User getUser(String id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("lm");
		EntityManager em = factory.createEntityManager();
		User user = em.find(User.class, Integer.parseInt(id));
		em.close();
		user.setPassword(null);
		return user;
	}
	
	public static boolean checkUsername(String username){

		try {
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT usr from User usr WHERE usr.username LIKE :username")
					  .setParameter("username", username);

			User userRetrieved  = (User) q.getSingleResult();
			em.close();
			
			if(userRetrieved != null){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return false;
		}
	
	}

}
