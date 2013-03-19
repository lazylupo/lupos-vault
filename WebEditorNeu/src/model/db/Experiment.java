/*
 * Created on 03.05.2010
 *
 * Project: WebExperiment
 * Original author: draxler
 * 
 * edited by: Wolfgang Ostermeier  in 2012/13
 */
package model.db;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Transient;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.sun.jersey.api.view.ImplicitProduces;

/**
 * This class Experiment belongs to a Java Persistence Unit. 
 * Instances of this class are used to create or change a corresponding database object.
 * Experiment database objects can also be retrieved as instances of this class.
 */
@XmlRootElement(name="experiment")
@XmlType(name = "experiment", propOrder = {
        "id",
		"name",
        "description",
        "infopage",
        "status",
        "startdate",
        "enddate",
        "expScripts"})
@Entity
@ImplicitProduces("text/html")
@Path("view/Experiment/")
public class Experiment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="keys")
    private int id;
    private String name;
    private String description; 
    @Transient
    private URL infopage;
    @Transient
    private Date startdate;
    @Transient
    private Date enddate;
    private String status;
    
    @ManyToOne
    private ExpAdmin expAdmin;
    @ManyToOne
    private ExpEditor expEditor;
	@ManyToOne
    private ExpProject expProject;
    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="experiment")
    private List<ExpScript> expScripts;
	
    

    public Experiment() {}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(required=true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getInfopage() {
        return infopage;
    }

    public void setInfopage(URL infopage) {
        this.infopage = infopage;
    }

    @XmlElement(required=true)
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @XmlElement(required=true)
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public ExpProject getExpProject() {
        return expProject;
    }

    public void setExpProject(ExpProject expProject) {
        this.expProject = expProject;
    }

    @XmlTransient 
    public ExpAdmin getExpAdmin() {
        return expAdmin;
    }

    public void setExpAdmin(ExpAdmin admin) {
        this.expAdmin = admin;
    }

    public void setExpScripts(List<ExpScript> scripts) {
        expScripts = scripts;
    }

    @XmlElementWrapper(name="expscripts", required=true)
    @XmlElements(@XmlElement(name="expscript"))
    public List<ExpScript> getExpScripts() {
    	
    	try {
			Collections.sort(expScripts, new ExpScriptComparator());
		} catch (Exception e) {

		}
        return expScripts;
    }
    @XmlTransient 
    public ExpEditor getExpEditor() {
  		return expEditor;
  	}

  	public void setExpEditor(ExpEditor expEditor) {
  		this.expEditor = expEditor;
  	}
  	
    /**
     * This method acts on HTTP-GET-request,retrieves an Experiment database object 
     * from the attached sql database and returns it in a Jersey implicit view
   	 * @param id the primary key of wanted Experiment object
	 * @return the wanted Experiment object
     */
    @Path("{id}")
    public Experiment getExperiment(@PathParam("id") String id){
    	
    	
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
		EntityManager em = factory.createEntityManager();
		Experiment experiment = em.find(Experiment.class, Integer.parseInt(id));
		
		return experiment;
    	
    }
    
    /**
	 * This private class compares the names of ExpScripts lexicographically
	 * @author Wolfgang Ostermeier
	 *
	 */
	private class ExpScriptComparator implements Comparator<ExpScript> {
	   	 
	  	  public int compare(ExpScript e1, ExpScript e2) {
	  	 
	  	    return e1.getName().compareTo(e2.getName());
	  	  }
	  }
}
