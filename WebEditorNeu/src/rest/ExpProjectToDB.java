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

import model.db.ExpProject;

/**
 * The class ExpProjectToDB receives REST-Requests and executes operations on ExpProject database objects 
 * in an attached sql database
 * @author Wolfgang Ostermeier
 *
 */
@Path("/ExpProjectToDB")
public class ExpProjectToDB {
	
	/**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit webExperiment
	 */
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
	
    /**
     * This method creates an ExpProject database object in the attached sql database
     * @param name settable attribute
     * @param description settable attribute
     * @param institution settable attribute
     * @param language settable attribute
     * @param country settable attribute
     * @param logopath settable attribute
     * @return the primary key of the new database object
     */
	@POST
	public String createExpProject(@QueryParam("name") String name, @QueryParam("description") String description, @QueryParam("institution") String institution,
			@QueryParam("language") String language, @QueryParam("country") String country, @QueryParam("logopath") String logopath){
		
    	
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpProject expProject = new ExpProject();
		expProject.setName(name);
		expProject.setDescription(description);
		expProject.setInstitution(institution);
		expProject.setLanguage(language);
		expProject.setCountry(country);
		expProject.setLogopath(logopath);
		em.persist(expProject);
		em.getTransaction().commit();
		em.refresh(expProject);
		em.close();	
		
		return String.valueOf(expProject.getId());

	}
	
	@GET
	@Path("/{id}")
	public ExpProject getExpProject(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		ExpProject expProject = em.find(ExpProject.class, Integer.parseInt(id));
		
		return expProject;
	}	
	
    /**
     * This method changes an ExpProject database object in the attached sql database
     * @param id id primary key of the ExpProject object for localization
     * @param name changeable attribute, "-1" for no change
     * @param description changeable attribute, "-1" for no change
     * @param status changeable attribute, "-1" for no change
     * @param institution changeable attribute, "-1" for no change
     * @param language changeable attribute, "-1" for no change
     * @param country changeable attribute, "-1" for no change
     * @param logopath changeable attribute, "-1" for no change
     */
	@PUT
	@Path("/{id}")
	public void changeExpProject(@PathParam("id") String id, @QueryParam("name") String name,@QueryParam("description") String description, @QueryParam("status") String status, 
								 @QueryParam("institution") String institution, @QueryParam("language") String language, @QueryParam("country") String country, 
								 @QueryParam("logopath") String logopath){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpProject expProject = em.find(ExpProject.class, Integer.parseInt(id));
		
		if(!name.equals("-1") && !expProject.getName().equals(name)) expProject.setName(name);
		if(!description.equals("-1") && !expProject.getDescription().equals(description)) expProject.setDescription(description);
		if(status != null) expProject.setStatus(status);
		if(!institution.equals("-1") && !expProject.getInstitution().equals(institution)) expProject.setInstitution(institution);
		if(!language.equals("-1") && !expProject.getLanguage().equals(language)) expProject.setLanguage(language);
		if(!country.equals("-1") && !expProject.getCountry().equals(country)) expProject.setCountry(country);
		if(!logopath.equals("-1") && !expProject.getLogopath().equals(logopath)) expProject.setLogopath(logopath);
		em.persist(expProject);
		em.getTransaction().commit();
		em.close();	
	}
	
	/**
	 * This method deletes an ExpProject database object from the attached sql database
	 * @param id the primary key of the ExpProject object to be deleted
	 */
	@DELETE
	@Path("/{id}")
	public void deleteExpProject(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		ExpProject expProject = em.find(ExpProject.class, Integer.parseInt(id));
		em.remove(expProject);
		em.getTransaction().commit();
		em.close();	
	}

}
