package rest;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import model.db.ExpScript;
import model.db.ExpScriptSection;

/**
 * The class ExpScriptSectionToDB receives REST-Requests and executes operations on ExpScriptSection database objects 
 * in an attached sql database
 * @author Wolfgang Ostermeier
 *
 */
@Path("/ExpScriptSectionToDB")
public class ExpScriptSectionToDB {
	
	/**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit webExperiment
	 */
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
	
	/**
	 * This method creates an ExpScriptSection database object in the attached sql database
	 * @param name settable attribute
	 * @param position settable attribute
	 * @param ordering settable attribute
	 * @param maxreplay settable attribute
	 * @param scrid the primary key of the parent database object
	 * @return the primary key of the new database object
	 */
	@POST
	public String createExpScriptSection(@QueryParam("name") String name, @QueryParam("position") String position, @QueryParam("ordering") String ordering, 
									   @QueryParam("maxreplay") String maxreplay, @QueryParam("scrid") String scrid){
		
    	
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpScriptSection expScrsfinal = new ExpScriptSection();
		expScrsfinal.setName(name);
		expScrsfinal.setOrdering(ordering);
		if(!maxreplay.equals(""))expScrsfinal.setMaxreplay(Integer.parseInt(maxreplay));
		ExpScript expscr = em.find(ExpScript.class,Integer.parseInt(scrid));
		if(!position.equals(""))expScrsfinal.setPosition(Integer.parseInt(position));
		else expScrsfinal.setPosition(expscr.getExpScriptSections().size()+1);
		expScrsfinal.setExpScript(expscr);
		em.persist(expScrsfinal);
		em.getTransaction().commit();
		em.refresh(expScrsfinal);
		em.refresh(expscr);
		em.close();	

		return String.valueOf(expScrsfinal.getId());
	}
	
	/**
	 * This method retrieves an ExpScriptSection database object from the attached sql database
	 * @param id primary key of wanted ExpScriptSection object
	 * @return the wanted ExpScriptSection object
	 */
	@GET
	@Path("/{id}")
	public ExpScriptSection getExpScriptSection(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		ExpScriptSection expScriptSection = em.find(ExpScriptSection.class, Integer.parseInt(id));
		
		return expScriptSection;
	}	
	
	/**
	 * This method changes an ExpScriptSection database object in the attached sql database
	 * @param id primary key of the ExpScriptSection object for localization
	 * @param position changeable attribute, "-1" for no change
	 * @param name changeable attribute, "-1" for no change
	 * @param ordering changeable attribute, "-1" for no change
	 * @param maxreplay changeable attribute, "-1" for no change
	 */
	@PUT
	@Path("/{id}")
	public void changeExpScriptSection(@PathParam("id") String id, @QueryParam("position") String position, @QueryParam("name") String name, @QueryParam("ordering") String ordering,
								@QueryParam("maxreplay") String maxreplay){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpScriptSection expScriptSection = em.find(ExpScriptSection.class, Integer.parseInt(id));
		
		if(!position.equals("-1") && expScriptSection.getPosition()!= Integer.parseInt(position)){
			int oldPosition  = expScriptSection.getPosition();
			int newPosition = Integer.parseInt(position);
			List<ExpScriptSection> expScriptSectionList= expScriptSection.getExpScript().getExpScriptSections();
			if(oldPosition>newPosition){
				for(ExpScriptSection expscrs : expScriptSectionList){
					
					if(expscrs.getPosition()>=newPosition && expscrs.getPosition()<oldPosition){
					ExpScriptSection expscrsManaged = em.find(ExpScriptSection.class, expscrs.getId());
					expscrsManaged.setPosition(expscrs.getPosition()+1);
					em.persist(expscrsManaged);
	
					}
				}	
			}
			if(oldPosition<newPosition){
				for(ExpScriptSection expscrs : expScriptSectionList){
					
					if(expscrs.getPosition()>oldPosition && expscrs.getPosition()<=newPosition){
					ExpScriptSection expscrsManaged = em.find(ExpScriptSection.class, expscrs.getId());
					expscrsManaged.setPosition(expscrs.getPosition()-1);
					em.persist(expscrsManaged);
	
					}
				}	
			}
			expScriptSection.setPosition(newPosition);
		};
		
		if(!name.equals("-1") && !expScriptSection.getName().equals(name)) expScriptSection.setName(name);
		if(!ordering.equals("-1") && !expScriptSection.getOrdering().equals(ordering)) expScriptSection.setOrdering(ordering);
		if(!maxreplay.equals("-1")){
		int maxreplayint = Integer.parseInt(maxreplay);
		if(expScriptSection.getMaxreplay() != maxreplayint ) expScriptSection.setMaxreplay(maxreplayint);
		}
		em.persist(expScriptSection);
		em.getTransaction().commit();

		em.close();	
	}
	
	/**
	 * This method deletes an ExpScriptSection database object from the attached sql database
	 * @param id the primary key of the ExpScriptSection object to be deleted
	 */
	@DELETE
	@Path("/{id}")
	public void deleteExpScriptSection(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpScriptSection expScriptSection = em.find(ExpScriptSection.class, Integer.parseInt(id));
		ExpScript expScript = em.find(ExpScript.class,expScriptSection.getExpScript().getId());

		List<ExpScriptSection> expScriptSections = expScript.getExpScriptSections();
		for(ExpScriptSection expscrs: expScriptSections){
			if(expscrs.getPosition()>expScriptSection.getPosition()){


				ExpScriptSection expscrsManaged = em.find(ExpScriptSection.class, expscrs.getId());
				expscrsManaged.setPosition(expscrsManaged.getPosition()-1);
				em.persist(expscrsManaged);
			}
		};

		em.remove(expScriptSection);
		em.getTransaction().commit();
		em.refresh(expScript);

		em.close();	
	}
	
	/**
	 * This method executes a change operation of the position attribute of multiple ExpScriptSection 
	 * objects and the adjustment of the position attribute of affected ExpScriptSection objects
	 * @param selItems primary keys of the ExpScriptSection objects to be changed
	 * @param position new value for the position attribute of the first ExpScriptSection object
	 */
	@PUT
	@Path("/multimove")
	public void moveMultipleExpScriptSections(@QueryParam("selItems") String selItems, @QueryParam("position") String position){
		
		String[] selItemsArray = selItems.split("\\|");
		List<String> selItemsList = Arrays.asList(selItemsArray);
		EntityManager em = factory.createEntityManager();
		int lastindex = selItemsArray.length-1;
		int newPosition = Integer.parseInt(position);
		int newPositionBackwards = newPosition+lastindex;
		int newPositiontemp  = newPosition;
		ExpScriptSection test = em.find(ExpScriptSection.class, Integer.parseInt(selItemsArray[0]));
		
		if(newPosition<test.getPosition()){
			
			for(int i=0;selItemsArray.length>i;i++){
				em.getTransaction().begin();
				ExpScriptSection expScriptSection = em.find(ExpScriptSection.class, Integer.parseInt(selItemsArray[i]));
				int oldPosition = expScriptSection.getPosition();
				List<ExpScriptSection> expScriptSectionList= expScriptSection.getExpScript().getExpScriptSections();
					for(ExpScriptSection expscrs : expScriptSectionList){
					
						if(!selItemsList.contains(String.valueOf(expscrs.getId())) && expscrs.getPosition()>=newPositiontemp && expscrs.getPosition()<oldPosition){
						ExpScriptSection expscrsManaged = em.find(ExpScriptSection.class, expscrs.getId());
						expscrsManaged.setPosition(expscrs.getPosition()+1);
						em.persist(expscrsManaged);

						}
					}
					expScriptSection.setPosition(newPositiontemp+i);
					em.persist(expScriptSection);
					em.getTransaction().commit();
			}
		}else if (newPosition>test.getPosition()){
			newPositiontemp = newPositionBackwards;
			
			for(int i=selItemsArray.length-1;0<=i;i--){
				em.getTransaction().begin();
				ExpScriptSection expScriptSection = em.find(ExpScriptSection.class, Integer.parseInt(selItemsArray[i]));
				int oldPosition = expScriptSection.getPosition();
				List<ExpScriptSection> expScriptSectionList= expScriptSection.getExpScript().getExpScriptSections();
					for(ExpScriptSection expscrs : expScriptSectionList){
					
						if(!selItemsList.contains(String.valueOf(expscrs.getId())) && expscrs.getPosition()<=newPositiontemp && expscrs.getPosition()>oldPosition){
						ExpScriptSection expscrsManaged = em.find(ExpScriptSection.class, expscrs.getId());
						expscrsManaged.setPosition(expscrs.getPosition()-1);
						em.persist(expscrsManaged);

						}
					}
					expScriptSection.setPosition(newPositiontemp-(selItemsArray.length-1-i));
					em.persist(expScriptSection);
					em.getTransaction().commit();
			}
		}
		em.close();	
	}	

}
