package rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import model.db.ExpEditor;

/**
 * The class ExpEditorToDB receives REST-Requests and executes operations on ExpEditor database objects 
 * in the attached sql database
 * @author Wolfgang Ostermeier
 *
 */
@Path("/ExpEditorToDB")
public class ExpEditorToDB {
	
	/**
	 * EntityManagerFactory that creates EntityManager instances for the persistence unit webExperiment
	 */
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
	
	/**
	 * This method creates an ExpEditor database object in the attached sql database
	 * @param name settable attribute
	 * @param firstname settable attribute
	 * @param email settable attribute
	 * @param password settable attribute
	 * @param phone settable attribute
	 * @param organisation settable attribute
	 * @return the primary key of the new database object
	 */
	@POST
	public String createExpEditor(@QueryParam("name") String name, @QueryParam("firstname") String firstname, @QueryParam("email") String email,
								@QueryParam("password") String password,@QueryParam("phone") String phone,@QueryParam("organisation") String organisation){
		
    	if(this.getExpEditor(email, password)==null){
			try {
				EntityManager em = factory.createEntityManager();
				em.getTransaction().begin();
				ExpEditor expEditor = new ExpEditor();
				expEditor.setName(name);
				expEditor.setFirstName(firstname);
				expEditor.setEmail(email);
				expEditor.setPassword(password);
				expEditor.setPhone(phone);
				expEditor.setOrganisation(organisation);
				em.persist(expEditor);
				em.getTransaction().commit();
				em.refresh(expEditor);
				em.close();	
				
				return String.valueOf(expEditor.getId());
				
			} catch (Exception e) {
				return null;
			}
    	}else return null;
	}
	
	/**
	* This method retrieves an ExpEditor database object from the attached sql database 
	* via its email and password attribute
	* @param email email attribute of wanted ExpEditor object
	* @param password password attribute of wanted ExpEditor object
	* @return the wanted ExpEditor object
	*/	
	@GET
	@Path("/{email}/{password}")
	public ExpEditor getExpEditor(@PathParam("email") String email, @PathParam("password") String password){

		try {
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT expe from ExpEditor expe WHERE expe.email LIKE :email AND expe.password LIKE :password")
					  .setParameter("email", email)
					  .setParameter("password", password);
			ExpEditor expEditor  = (ExpEditor) q.getSingleResult();
			
			return expEditor;
		} catch (Exception e) {
			
			return null;
		}
	}
	
	/**
	 * This method changes an ExpEditor database object in the attached sql database
	 * @param id primary key of the ExpEditor object for localization
	 * @param firstname changeable attribute, "-1" for no change
	 * @param name changeable attribute, "-1" for no change
	 * @param email changeable attribute, "-1" for no change
	 * @param password changeable attribute, "-1" for no change
	 * @param phone changeable attribute, "-1" for no change
	 * @param organisation changeable attribute, "-1" for no change
	 */
	@PUT
	@Path("/{id}")
	public void changeExpEditor(@PathParam("id") String id,  @QueryParam("firstname") String firstname, @QueryParam("name") String name, @QueryParam("email") String email,
								@QueryParam("password") String password,@QueryParam("phone") String phone,@QueryParam("organisation") String organisation){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpEditor expEditor = em.find(ExpEditor.class, Integer.parseInt(id));

		if(!expEditor.getFirstName().equals(firstname)) expEditor.setFirstName(firstname);
		if(!expEditor.getName().equals(name)) expEditor.setName(name);
		if(!expEditor.getEmail().equals(email)) expEditor.setEmail(email);
		if(!expEditor.getPassword().equals(password)) expEditor.setPassword(password);
		if(!expEditor.getPhone().equals(phone)) expEditor.setPhone(phone);
		if(!expEditor.getOrganisation().equals(organisation)) expEditor.setOrganisation(organisation);
		em.persist(expEditor);
		em.getTransaction().commit();
		em.close();	
	}
	
	/**
	* This method deletes an ExpEditor database object from the attached sql database
	* @param id the primary key of the ExpEditor object to be deleted
	*/
	@DELETE
	@Path("/{id}")
	public void deleteExpEditor(@PathParam("id") String id){

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		ExpEditor expEditor = em.find(ExpEditor.class, Integer.parseInt(id));
		em.remove(expEditor);
		em.getTransaction().commit();
		em.close();	
	}

}
