package rest;

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
import javax.xml.bind.JAXBException;

import model.db.ExpScript;
import model.db.Experiment;

/**
 * The class ExpScriptToDB receives REST-Requests and executes operations on ExpScript database objects 
 * in an attached sql database
 * @author Wolfgang Ostermeier
 *
 */
@Path("/ExpScriptToDB")
public class ExpScriptToDB {
	
	/**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit webExperiment
	 */
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
	
    /**
     * This method creates an ExpScript database object in the attached sql database
     * @param name settable attribute
     * @param description settable attribute
     * @param author settable attribute
     * @param id the primary key of the parent database object
     * @return the primary key of the new database object
     */
	@POST
	public String createExpScript(@QueryParam("name") String name, @QueryParam("description") String description, @QueryParam("author") String author, @QueryParam("id") String id){
		
    	
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpScript expScrfinal = new ExpScript();
		expScrfinal.setName(name);
		expScrfinal.setDescription(description);
		expScrfinal.setAuthor(author);
		Experiment exp = em.find(Experiment.class, Integer.parseInt(id));
		expScrfinal.setExperiment(exp);
		em.persist(expScrfinal);
		em.getTransaction().commit();
		em.refresh(expScrfinal);
		em.refresh(exp);
		em.close();	

		return String.valueOf(expScrfinal.getId());
	}
	
	/**
	 * This method retrieves an ExpScript database object from the attached sql database
	 * @param id primary key of wanted ExpScript object
	 * @return the wanted ExpScript object
	 */
	@GET
	@Path("/{id}")
	public ExpScript getExpScript(@PathParam("id") String id) throws JAXBException{

		EntityManager em = factory.createEntityManager();
		ExpScript expScript = em.find(ExpScript.class, Integer.parseInt(id));

		return expScript;
	}	
	
	/**
	 * This method changes an ExpScript database object in the attached sql database
	 * @param id primary key of the ExpScript object for localization
	 * @param name changeable attribute, "-1" for no change
	 * @param description changeable attribute, "-1" for no change
	 * @param author changeable attribute, "-1" for no change
	 */
	@PUT
	@Path("/{id}")
	public void changeExpScript(@PathParam("id") String id, @QueryParam("name") String name,@QueryParam("author") String author,
								@QueryParam("description") String description){

		EntityManager em = factory.createEntityManager();
		ExpScript expScriptfinal = null;

		em.getTransaction().begin();
		expScriptfinal = em.find(ExpScript.class, Integer.parseInt(id));
	
		if(!name.equals("-1") && !expScriptfinal.getName().equals(name)) expScriptfinal.setName(name);
		if(!author.equals("-1") && !expScriptfinal.getAuthor().equals(author)) expScriptfinal.setAuthor(author);
		if(!description.equals("-1") && !expScriptfinal.getDescription().equals(description)) expScriptfinal.setDescription(description);
		em.persist(expScriptfinal);
		em.getTransaction().commit();

		em.close();	
	}
	
	/**
	 * This method deletes an ExpScript database object from the attached sql database
	 * @param id the primary key of the ExpScript object to be deleted
	 */
	@DELETE
	@Path("/{id}")
	public void deleteExpScript(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpScript expScript = em.find(ExpScript.class, Integer.parseInt(id));
		Experiment experiment = em.find(Experiment.class,expScript.getExperiment().getId());

		em.remove(expScript);
		em.getTransaction().commit();
		em.refresh(experiment);

		em.close();	
	}

}
