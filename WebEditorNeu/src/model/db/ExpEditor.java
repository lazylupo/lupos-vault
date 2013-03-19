/*
 * Created on 02.05.2010
 *
 * Project: WebExperiment
 * Original author: draxler
 * 
 * edited by: Wolfgang Ostermeier  in 2012/13
 */
package model.db;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sun.jersey.api.view.ImplicitProduces;

/**
 * This class ExpEditor belongs to a Java Persistence Unit. 
 * Instances of this class are used to create or change a corresponding database object.
 * ExpEditor database objects can also be retrieved as instances of this class.
 * @author Wolfgang Ostermeier
 */
@Entity
@XmlRootElement
@XmlType(name = "expeditor", propOrder = {
        "id",
		"name",
        "firstName",
        "email",
        "password",
        "phone",
        "organisation",
        "expProjects",
        "experiments"})
@ImplicitProduces("text/html")
@Path("view/ExpEditor/")
public class ExpEditor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="keys")
    private int id;
    private String name = "";
    private String firstName = "";
    private String email = "";
    private String password = ""; 
    private String phone = "";
    private String organisation = "";

    
    @ManyToMany(mappedBy="expEditor")
    private List<ExpProject> expProjects;
    @OneToMany(mappedBy="expEditor")
    private List<Experiment> experiments;
    
    public ExpEditor() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }


    public List<ExpProject> getExpProjects() {
    	
    	try {
			Collections.sort(expProjects, new ExpProjectComparator());
		} catch (Exception e) {

		}
        return expProjects;
    }

    public void setExpProjects(List<ExpProject> projects) {
        this.expProjects = projects;
    }

    @XmlElementWrapper(name="experiments")
    @XmlElements(@XmlElement(name="experiment"))
    public List<Experiment> getExperiments() {
    	
        return experiments;
    }

    public void setExperiments(List<Experiment> experiments) {
        this.experiments = experiments;
    }
    
    /**
     * This method acts on HTTP-GET-request,retrieves an ExpEditor database object 
     * from the attached sql database and returns it in a Jersey implicit view
   	 * @param id the primary key of wanted ExpEditor object
	 * @return the wanted ExpEditor object
     */
	@Path("{id}")
	public ExpEditor getExpEditor(@PathParam("id") String id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
		EntityManager em = factory.createEntityManager();
		ExpEditor expEditor = em.find(ExpEditor.class, Integer.parseInt(id));
	
		return expEditor;
		
	}
	
	/**
	 * This static method retrieves an ExpEditor database object from the attached sql database
	 * @param id the primary key of wanted ExpEditor object
	 * @return the wanted ExpEditor object
	 */
	public static ExpEditor getExpEditorStatic(String id){
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
		EntityManager em = factory.createEntityManager();
		ExpEditor expEditor = em.find(ExpEditor.class, Integer.parseInt(id));
		
		return expEditor;
	}
	
	/**
	 * This private class compares the names of ExpProjects lexicographically
	 * @author Wolfgang Ostermeier
	 *
	 */
	private class ExpProjectComparator implements Comparator<ExpProject> {
   	 
  	  public int compare(ExpProject e1, ExpProject e2) {
  	 
  	    return e1.getName().compareTo(e2.getName());
  	  }
  }

}
