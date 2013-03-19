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

import model.db.ExpItem;
import model.db.ExpScriptSection;

/**
 * The class ExpItemToDB receives REST-Requests and executes operations on ExpItem database objects 
 * in an attached sql database
 * @author Wolfgang Ostermeier
 *
 */
@Path("/ExpItemToDB")
public class ExpItemToDB {
	
	/**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit webExperiment
	 */
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
	
	/**
     * This method creates an ExpItem database object in the attached sql database
     * @param position settable attribute
     * @param label settable attribute
     * @param options settable attribute
     * @param assessmentoptions settable attribute
     * @param expected settable attribute
     * @param url settable attribute
     * @param scrsid the primary key of the parent database object
     * @return the primary key of the new database object
     */
	@POST
	public String createExpItem(@QueryParam("position") String position,@QueryParam("label") String label,
			  @QueryParam("options") String options, @QueryParam("assessmentoptions") String assessmentoptions, 
			  @QueryParam("expected") String expected, @QueryParam("url") String url,@QueryParam("scrsid") String scrsid){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		ExpItem expItemfinal = new ExpItem();
		
		expItemfinal.setLabel(label);
		expItemfinal.setOptions(options);
		expItemfinal.setAssessmentoptions(assessmentoptions);
		expItemfinal.setExpected(expected);
		expItemfinal.setUrl(url);
		ExpScriptSection expscrs = em.find(ExpScriptSection.class, Integer.parseInt(scrsid));
		if(!position.equals("-1"))expItemfinal.setPosition(Integer.parseInt(position));
		else expItemfinal.setPosition(expscrs.getExpItems().size()+1);
		expItemfinal.setExpScriptSection(expscrs);
		em.persist(expItemfinal);
		em.getTransaction().commit();
		em.refresh(expItemfinal);
		em.refresh(expscrs);
		em.close();	
		return String.valueOf(expItemfinal.getId());
	}
	
	/**
     * This method retrieves an ExpItem database object from the attached sql database
     * @param id primary key of wanted ExpItem object
     * @return the wanted ExpItem object
     */
	@GET
	@Path("/{id}")
	public ExpItem getExpItem(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		ExpItem expItem = em.find(ExpItem.class, Integer.parseInt(id));
		
		return expItem;
	}
	
	/**
     * This method changes an ExpItem database object in the attached sql database
     * @param id primary key of the ExpItem object for localization
     * @param position changeable attribute, "-1" for no change
     * @param label changeable attribute, "-1" for no change
     * @param options changeable attribute, "-1" for no change
     * @param assessmentoptions changeable attribute, "-1" for no change
     * @param expected changeable attribute, "-1" for no change
     * @param url changeable attribute, "-1" for no change
     */
	@PUT
	@Path("/{id}")
	public void changeExpItem(@PathParam("id") String id, @QueryParam("position") String position,@QueryParam("label") String label,
							  @QueryParam("options") String options, @QueryParam("assessmentoptions") String assessmentoptions, 
							  @QueryParam("expected") String expected, @QueryParam("url") String url){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpItem expItem = em.find(ExpItem.class, Integer.parseInt(id));

		if(!position.equals("") && !position.equals("-1") && expItem.getPosition()!= Integer.parseInt(position)){
			int oldPosition  = expItem.getPosition();
			int newPosition = Integer.parseInt(position);
			List<ExpItem> expItemList= expItem.getExpScriptSection().getExpItems();
			if(oldPosition>newPosition){
				for(ExpItem expi : expItemList){
					
					if(expi.getPosition()>=newPosition && expi.getPosition()<oldPosition){
					ExpItem expiManaged = em.find(ExpItem.class, expi.getId());
					expiManaged.setPosition(expi.getPosition()+1);
					em.persist(expiManaged);
	
					}
				}	
			}
			if(oldPosition<newPosition){
				for(ExpItem expi : expItemList){
					
					if(expi.getPosition()>oldPosition && expi.getPosition()<=newPosition){
					ExpItem expiManaged = em.find(ExpItem.class, expi.getId());
					expiManaged.setPosition(expi.getPosition()-1);
					em.persist(expiManaged);
	
					}
				}	
			}
			expItem.setPosition(newPosition);
		};
		
		if(!label.equals("-1") && !expItem.getLabel().equals(label)) expItem.setLabel(label);
		if(!options.equals("-1") && !expItem.getOptions().equals(options)) expItem.setOptions(options);
		if(!assessmentoptions.equals("-1") && !expItem.getAssessmentoptions().equals(assessmentoptions)) expItem.setAssessmentoptions(assessmentoptions);
		if(!expected.equals("-1") && !expItem.getExpected().equals(expected)) expItem.setExpected(expected);
		if(!url.equals("-1") && !expItem.getUrl().equals(url)) expItem.setUrl(url);
		em.persist(expItem);
		em.getTransaction().commit();
		em.close();	
	}
	
	/**
	 * This method deletes an ExpItem database object from the attached sql database
	 * @param id the primary key of the ExpItem object to be deleted
	 */
	@DELETE
	@Path("/{id}")
	public void deleteExpItem(@PathParam("id") String id){
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpItem expItem = em.find(ExpItem.class, Integer.parseInt(id));
		ExpScriptSection expScriptSection = em.find(ExpScriptSection.class, expItem.getExpScriptSection().getId());

		List<ExpItem> expItems = expScriptSection.getExpItems();
		for(ExpItem expi: expItems){
			if(expi.getPosition()>expItem.getPosition()){

				ExpItem expiManaged = em.find(ExpItem.class, expi.getId());
				expiManaged.setPosition(expiManaged.getPosition()-1);
				em.persist(expiManaged);
			}
		};
		em.remove(expItem);
		em.getTransaction().commit();
		em.refresh(expScriptSection);
		em.close();	
	}
	
	/**
	 * This method executes a change operation of the position attribute of multiple ExpItem 
	 * objects and the adjustment of the position attribute of affected ExpItem objects
	 * @param selItems primary keys of the ExpItem objects to be changed
	 * @param position new value for the position attribute of the first ExpItem object
	 */
	@PUT
	@Path("/multimove")
	public void moveMultipleExpItems(@QueryParam("selItems") String selItems, @QueryParam("position") String position){
		
		String[] selItemsArray = selItems.split("\\|");
		List<String> selItemsList = Arrays.asList(selItemsArray);
		EntityManager em = factory.createEntityManager();
		int lastindex = selItemsArray.length-1;
		int newPosition = Integer.parseInt(position);
		int newPositionLast = newPosition+lastindex;
		int newPositiontemp  = newPosition;
		ExpItem test = em.find(ExpItem.class, Integer.parseInt(selItemsArray[0]));
		
		if(newPosition<test.getPosition()){
			
			for(int i=0;selItemsArray.length>i;i++){
				em.getTransaction().begin();
				ExpItem expItem = em.find(ExpItem.class, Integer.parseInt(selItemsArray[i]));
				int oldPosition = expItem.getPosition();
				List<ExpItem> expItemList= expItem.getExpScriptSection().getExpItems();
					for(ExpItem expi : expItemList){
					
						if(!selItemsList.contains(String.valueOf(expi.getId())) && expi.getPosition()>=newPositiontemp && expi.getPosition()<oldPosition){
						ExpItem expiManaged = em.find(ExpItem.class, expi.getId());
						expiManaged.setPosition(expi.getPosition()+1);
						em.persist(expiManaged);

						}
					}
					expItem.setPosition(newPositiontemp+i);
					em.persist(expItem);
					em.getTransaction().commit();
			}
		}else if (newPosition>test.getPosition()){
			newPositiontemp = newPositionLast;
			
			for(int i=selItemsArray.length-1;0<=i;i--){
				em.getTransaction().begin();
				ExpItem expItem = em.find(ExpItem.class, Integer.parseInt(selItemsArray[i]));
				int oldPosition = expItem.getPosition();
				List<ExpItem> expItemList= expItem.getExpScriptSection().getExpItems();
					for(ExpItem expi : expItemList){
					
						if(!selItemsList.contains(String.valueOf(expi.getId())) && expi.getPosition()<=newPositiontemp && expi.getPosition()>oldPosition){
							ExpItem expiManaged = em.find(ExpItem.class, expi.getId());
						expiManaged.setPosition(expi.getPosition()-1);
						em.persist(expiManaged);

						}
					}
					expItem.setPosition(newPositiontemp-(selItemsArray.length-1-i));
					em.persist(expItem);
					em.getTransaction().commit();
			}
		}
		em.close();	
	}	

}
