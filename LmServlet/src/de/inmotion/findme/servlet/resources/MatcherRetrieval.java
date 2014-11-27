package de.inmotion.findme.servlet.resources;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.inmotion.findme.servlet.utils.Notify;


@Path("matcher")
public class MatcherRetrieval {

	
    /**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit lm
	 */
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("lm");

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrEditMatcher(long[] userids){
		
			Matcher mtchr = checkforExistingMatcher(userids);
				
			if(mtchr == null){	
				try {
					EntityManager em = factory.createEntityManager();
					em.getTransaction().begin();
					
					Matcher mtchrNew = new Matcher();
					mtchrNew.setUser1_id(userids[0]);
					
	
					em.persist(mtchrNew);
					em.getTransaction().commit();
					em.refresh(mtchrNew);

					em.getTransaction().begin();
					User usr = em.find(User.class, userids[0]);
					usr.setMatcher(mtchrNew);
					em.persist(usr);
					em.getTransaction().commit();
					
					em.close();	
					
					return Response.noContent().build();
	
					
				} catch (Exception e) {
					
					 return Response.serverError().entity(userids).build();
				}
			}else{
				
			try {
				
				EntityManager em = factory.createEntityManager();
				em.getTransaction().begin();
				mtchr = em.find(Matcher.class, mtchr.getId());
				
				if(mtchr.getUser1_id()==0){
					mtchr.setUser1_id(userids[0]);
				}else{
					mtchr.setUser2_id(userids[0]);
				}
				
				em.persist(mtchr);
				em.getTransaction().commit();
				em.refresh(mtchr);
				
				em.getTransaction().begin();
				User user1 = em.find(User.class, userids[0]);
				user1.setMatcher(mtchr);
				em.persist(user1);
				em.getTransaction().commit();
				em.refresh(user1);
				
				User user2 = em.find(User.class, userids[1]);

				em.close();	
					
					Notify.notifyViaGCM(user1.getRegistrationId(), Notify.ACCEPTED, "Der Match ist zustande gekommen!", mtchr, "1");
					Notify.notifyViaGCM(user2.getRegistrationId(), Notify.ACCEPTED, "Der Match ist zustande gekommen!", mtchr, "1");
	
					
				} catch (Exception e) {
					 
					return Response.serverError().entity(userids).build();
				}
					
				return Response.noContent().build();
				
			}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteMatcher(User user){
		
		User oppUserRetrieved;

		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Matcher matcherRetrieved = em.find(Matcher.class, user.getMatcher().getId()); 
			Adventure userAdvRetrieved = em.find(Adventure.class, user.getAdventure().getId()); 
			
			if(matcherRetrieved.getUser1_id() == user.getId()) oppUserRetrieved = em.find(User.class, matcherRetrieved.getUser2_id());
			else oppUserRetrieved = em.find(User.class, matcherRetrieved.getUser1_id());
			
			Adventure oppUserAdvRetrieved = em.find(Adventure.class, oppUserRetrieved.getAdventure().getId()); 

			em.remove(matcherRetrieved);
			em.remove(userAdvRetrieved);
			em.remove(oppUserAdvRetrieved);
			
			Notify.notifyViaGCM(oppUserRetrieved.getRegistrationId(), Notify.REJECTED, "Match-Partner hat leider abgelehnt!", null, "1");
			
			em.getTransaction().commit();
			em.close();
			
			return Response.noContent().build();

		} catch (Exception e) {

			return Response.serverError().entity(user).build();
		}
	}
	
	public static void deleteMatcherStatic(Matcher matcher){

		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Matcher matcherRetrieved = em.find(Matcher.class, matcher.getId()); 
			
			em.remove(matcherRetrieved);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {

			
		}
	}
	
	public static Matcher checkforExistingMatcher(long[] userids){
			
		ArrayList<Long> userIdsList = new ArrayList<Long>();
		
		for(int i=0;i<userids.length;i++){
			
			userIdsList.add(Long.valueOf(userids[i]));
			
		}

		try {
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT mtchr from Matcher mtchr WHERE mtchr.user1_id IN :userids OR mtchr.user2_id IN :userids")
					  .setParameter("userids", userIdsList);

			Matcher matcherRetrieved  = (Matcher) q.getSingleResult();
			em.close();
			
			if(matcherRetrieved != null){
				return matcherRetrieved;
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
		return null;
	}

}
