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
import javax.persistence.ManyToMany;
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
 * This class ExpProject belongs to a Java Persistence Unit. 
 * Instances of this class are used to create or change a corresponding database object.
 * ExpProject database objects can also be retrieved as instances of this class.
 */
@XmlRootElement(name="expproject")
@XmlType(name = "expproject", propOrder = {
		"id",
        "name",
        "description",
        "infopage",
        "status",
        "institution",
        "language",
        "country",
        "logopath",
        "startdate",
        "enddate",
        "expAdmin",
        "experiments"})
@Entity
@ImplicitProduces("text/html")
@Path("view/ExpProject/")
public class ExpProject {

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
    private String institution;
    private String language;
    private String country;
    private String logopath;
    
    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="expProject")
    private List<Experiment> experiments;
    @ManyToOne
    private ExpAdmin expAdmin;
    @ManyToMany
    private List<ExpEditor> expEditor;

	public ExpProject () {}
    
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public URL getInfopage() {
        return infopage;
    }

    public void setInfopage(URL infoPage) {
        this.infopage = infoPage;
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

    @XmlElement(required=true)
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @XmlElement(required=true)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @XmlElement(required=true)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @XmlElement(required=true)
    public String getLogopath() {
        return logopath;
    }

    public void setLogopath(String logopath) {
        this.logopath = logopath;
    }

    @XmlElementWrapper(name="experiments")
    @XmlElements(@XmlElement(name="experiment"))
    public List<Experiment> getExperiments() {
    	
    	try {
			Collections.sort(experiments, new ExperimentComparator());
		} catch (Exception e) {

		}
        return experiments;
    }

    public void setExperiments(List<Experiment> experiments) {
        this.experiments = experiments;
    }

    public ExpAdmin getExpAdmin() {
        return expAdmin;
    }

    public void setExpAdmin(ExpAdmin admin) {
        this.expAdmin = admin;
    }
    
    @XmlTransient 
    public List<ExpEditor> getExpEditor() {
		return expEditor;
	}

	public void setExpEditor(List<ExpEditor> expEditor) {
		this.expEditor = expEditor;
	}
    
	/**
     * This method acts on HTTP-GET-request,retrieves an ExpProject database object 
     * from the attached sql database and returns it in a Jersey implicit view
   	 * @param id the primary key of wanted ExpProject object
	 * @return the wanted ExpProject object
     */
    @Path("{id}")
    public ExpProject getExpProject(@PathParam("id") String id){
    	
    	
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
		EntityManager em = factory.createEntityManager();
		ExpProject expProject = em.find(ExpProject.class, Integer.parseInt(id));

		return expProject;
    	
    }
    
    /**
	 * This private class compares the names of Experiments lexicographically
	 * @author Wolfgang Ostermeier
	 *
	 */
	private class ExperimentComparator implements Comparator<Experiment> {
	   	 
	  	  public int compare(Experiment e1, Experiment e2) {
	  	 
	  	    return e1.getName().compareTo(e2.getName());
	  	  }
	  }
}
