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

import model.db.ExpEditor;
import model.db.ExpProject;
import model.db.Experiment;

/**
 * The class ExperimentToDB receives REST-Requests and executes operations on Experiment database objects 
 * in an attached sql database
 * @author Wolfgang Ostermeier
 *
 */
@Path("/ExperimentToDB")
public class ExperimentToDB {
	
	/**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit webExperiment
	 */
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
	
	/**
	 * This method creates an Experiment database object in the attached sql database
     * @param name settable attribute
     * @param description settable attribute
     * @param expEditorId settable attribute
     * @param projectId settable attribute
     * @return the primary key of the new database object
     */
	@POST
	public String createExperiment(@QueryParam("name") String name, @QueryParam("description") String description, @QueryParam("expeditorid") String expEditorId, @QueryParam("projectid") String projectId){
		
    	
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Experiment expfinal = new Experiment();
		expfinal.setName(name);
		expfinal.setDescription(description);
		ExpEditor expEditor = em.find(ExpEditor.class,Integer.parseInt(expEditorId));
		ExpProject expProject = em.find(ExpProject.class,Integer.parseInt(projectId));
		expfinal.setExpProject(expProject);
		expfinal.setExpEditor(expEditor);
		expEditor.getExperiments().add(expfinal);
		em.persist(expfinal);
		em.getTransaction().commit();
		em.refresh(expfinal);
		em.refresh(expProject);
		em.refresh(expEditor);
		em.close();
		return String.valueOf(expfinal.getId());
	}
	
	/**
     * This method retrieves an Experiment database object from the attached sql database
     * @param id primary key of wanted Experiment object
     * @return the wanted Experiment object
     */
	@GET
	@Path("/{id}")
	public Experiment getExperiment(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		Experiment experiment = em.find(Experiment.class, Integer.parseInt(id));

		return experiment;
	}	
	
	/**
	 * This method changes an ExpEditor database object in the attached sql database
	 * @param id primary key of the Experiment object for localization
	 * @param name changeable attribute, "-1" for no change
	 * @param description changeable attribute, "-1" for no change
	 * @param status changeable attribute, "-1" for no change
	 */
	@PUT
	@Path("/{id}")
	public void changeExperiment(@PathParam("id") String id, @QueryParam("name") String name,
								@QueryParam("description") String description, @QueryParam("status") String status){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Experiment experiment = em.find(Experiment.class, Integer.parseInt(id));
	
		if(!name.equals("-1") && !experiment.getName().equals(name)) experiment.setName(name);
		if(!description.equals("-1") && !experiment.getDescription().equals(description)) experiment.setDescription(description);
		if(!status.equals("-1")) experiment.setStatus(status);
		em.persist(experiment);
		em.getTransaction().commit();
		em.close();	
	}
	
	/**
	 * This method deletes an Experiment database object from the attached sql database
	 * @param id the primary key of the Experiment object to be deleted
	 */
	@DELETE
	@Path("/{id}")
	public void deleteExperiment(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Experiment experiment = em.find(Experiment.class, Integer.parseInt(id));
		ExpProject expProject = em.find(ExpProject.class,experiment.getExpProject().getId());

		em.remove(experiment);
		em.getTransaction().commit();
		em.refresh(expProject);

		em.close();	
	}

}
