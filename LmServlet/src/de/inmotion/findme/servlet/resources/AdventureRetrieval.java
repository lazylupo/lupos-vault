package de.inmotion.findme.servlet.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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

import de.inmotion.findme.servlet.utils.FindMatchRunner;
import de.inmotion.findme.servlet.utils.Notify;

@Path("adventure")
public class AdventureRetrieval {

	
    /**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit lm
	 */
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("lm");
	
	private final static Logger LOGGER = Logger.getLogger(AdventureRetrieval.class
		      .getName());


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAdventure(final Adventure adventure){
		
			try {
				
				EntityManager em = factory.createEntityManager();
				em.getTransaction().begin();
				
				User userfromDB = em.find(User.class, adventure.getUserid());
				//User userfromDB = em.find(User.class,301);
				adventure.setUser(userfromDB);

				em.persist(adventure);
				em.getTransaction().commit();
				em.refresh(adventure);
				em.refresh(userfromDB);
				em.close();	
				
				long[] advIds =new long[1];
				advIds[0] = adventure.getId();
				(new Thread(new FindMatchRunner(advIds))).start();
				
				userfromDB.setPassword(null);
				return Response.ok(userfromDB).build();

				
			} catch (Exception e) {
				 return Response.serverError().entity(adventure).build();

			}

	}
	
	@POST
	@Path("/view")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAdventure(User user){

		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Adventure adventure = em.find(Adventure.class, user.getAdventure().getId());
			
			adventure.setUserid(adventure.getUser().getId());
			em.close();
			
			return Response.ok(adventure).build();

		} catch (Exception e) {

			return Response.serverError().entity(user).build();
		}
	}
	
	@POST
	@Path("/viewById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Adventure getAdventureById(Adventure adventure){

		EntityManager em = factory.createEntityManager();
		Adventure adventure2 = em.find(Adventure.class, adventure.getId());
		em.close();
	
		return adventure2;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAdventure(Adventure adventure){

		try {
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Adventure adventureRetrieved = em.find(Adventure.class, adventure.getId());
			
			if(adventureRetrieved.isActive() == true){
			em.remove(adventureRetrieved);
			em.getTransaction().commit();
			em.close();
			
			return Response.noContent().build();
			}else{
				
				return Response.notModified().build();
			}

		} catch (Exception e) {

			return Response.serverError().entity(adventure).build();
		}
	}
	
	public static Adventure getAdventureByIdStatic(long adventure_id){
		
		EntityManager em = factory.createEntityManager();
		Adventure adventure2 = em.find(Adventure.class, adventure_id);
		adventure2.setUserid(adventure2.getUser().getId());
		em.close();
	
		return adventure2;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Adventure> getAllOtherAdventures(long[] advIds){
		
		ArrayList<Long> advIdsList = new ArrayList<Long>();
		
		for(int i=0;i<advIds.length;i++){
			
			advIdsList.add(Long.valueOf(advIds[i]));
			
		}
	
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT adv from Adventure adv WHERE adv.id NOT IN :id")
				  .setParameter("id", advIdsList);
		List<Adventure> resultList = new ArrayList<Adventure>();
		resultList = (List<Adventure>) q.getResultList();
		em.close();
		
		for(Adventure advItem:resultList){
			
			advItem.setUserid(advItem.getUser().getId());
		}
		return resultList;

	}
	
	public static List<Adventure> getAllOtherActiveAdventures(long[] advIds){
		
		List<Adventure> adventureList = getAllOtherAdventures(advIds);
		
		List<Adventure> resultList = new ArrayList<Adventure>();
		Date now = new Date();

		for(Adventure advItem:adventureList){
			
			if(advItem.isActive() & advItem.getExpirationDate().after(now))resultList.add(advItem);	
		}
		
		return resultList;
	}
	
	public static List<Adventure> getAllRelevantAdventures(long[] advIds){
		
		List<Adventure> activeAdventureList = getAllOtherActiveAdventures(advIds);
		
		List<Adventure> resultList = new ArrayList<Adventure>();
		
		Adventure actualAdv = getAdventureByIdStatic(advIds[0]);
		Position userPos = actualAdv.getUser().getPosition();
		

		for(Adventure advItem:activeAdventureList){
			
			
			if(actualAdv.isMeAlone()==advItem.isDateAlone() && actualAdv.isDateAlone()==advItem.isMeAlone()){
				
				if(((actualAdv.isOppositeFemale() != actualAdv.isOppositeMale() && actualAdv.isOppositeMale()==advItem.getUser().isMale()) || (actualAdv.isOppositeFemale() && actualAdv.isOppositeMale()))
				   && ((advItem.isOppositeFemale() != advItem.isOppositeMale() && advItem.isOppositeMale()==actualAdv.getUser().isMale()) || (advItem.isOppositeFemale() && advItem.isOppositeMale()))) {
					
					Position itemPos = advItem.getUser().getPosition();
					double dist = Position.calcDistanceInKm(userPos.getLatitude(), userPos.getLongitude(), itemPos.getLatitude(), itemPos.getLongitude());
					int distInt = (int) Math.round(dist);
					
					if(distInt<=actualAdv.getPerimeter())resultList.add(advItem);
				
				}
			}
			
		}
		
		return resultList;
	}
	
	@POST
	@Path("/checkForMatch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static void  checkForMatchingAdventure(long[] advIds){
		
		List<Adventure> relevantAdventureList = getAllRelevantAdventures(advIds);
		Adventure actualAdv = getAdventureByIdStatic(advIds[0]);
		LOGGER.info("Die Liste ist leer =  "+ relevantAdventureList.isEmpty());
		
		if(!relevantAdventureList.isEmpty()){
			
			Adventure matchAdv = relevantAdventureList.get(0);
			
			toggleActivateAdventure(matchAdv);
			matchAdv.setActive(false);
			toggleActivateAdventure(actualAdv);
			actualAdv.setActive(false);
			
//			Calendar cal = Calendar.getInstance();
//			cal.setTimeInMillis(System.currentTimeMillis());
//			cal.add(Calendar.MINUTE, 10);
//			
//			Date date = cal.getTime();
//			String test = date.toGMTString();
			
//			matchAdv.setMatchExpireTime(cal);
//			actualAdv.setMatchExpireTime(cal);
			User usr1 = matchAdv.getUser();
			usr1.setPassword(null);
			User usr2= actualAdv.getUser();
			usr2.setPassword(null);
		

			Notify.notifyViaGCM(actualAdv.getUser().getRegistrationId(), Notify.FOUND, "Passendes Abenteuer gefunden!", usr1, "1");
			LOGGER.info("Nachricht geschickt an "+ actualAdv.getUser().getUsername());
			
			
			if(matchAdv.getUser().getRegistrationId() != null){
				Notify.notifyViaGCM(matchAdv.getUser().getRegistrationId(),Notify.FOUND, "Passendes Abenteuer gefunden!", usr2, "1");
				LOGGER.info("Nachricht geschickt an "+ matchAdv.getUser().getUsername());
			}

		}		
	}
	
	@POST
	@Path("/handleReject")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static void  handleRejectedAdventure(long[] advIds){
		
		Adventure advRejecter = getAdventureByIdStatic(advIds[0]);
		Adventure advRejected = getAdventureByIdStatic(advIds[1]);
		
		Notify.notifyViaGCM(advRejected.getUser().getRegistrationId(), Notify.REJECTED, "Match-Partner hat leider abgelehnt!", null, "1");
		System.out.println(advRejected.getUser().getUsername());
		
		toggleActivateAdventure(advRejecter);
		toggleActivateAdventure(advRejected);
		
		long[] usrIds = new long[2];
		usrIds[0] = advRejecter.getUserid();
		usrIds[1] = advRejected.getUserid();
		
		Matcher unfinMtchr = MatcherRetrieval.checkforExistingMatcher(usrIds);
		if(unfinMtchr!=null){
			
			MatcherRetrieval.deleteMatcherStatic(unfinMtchr);

		}
		
		checkForMatchingAdventure(advIds);
	
	}
	
	public static void  toggleActivateAdventure(Adventure adv){
		
	
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			Adventure advRetrieved = em.find(Adventure.class, adv.getId());
			advRetrieved.setActive(!advRetrieved.isActive());
			em.persist(advRetrieved);
			em.getTransaction().commit();
			em.close();

	}

}
