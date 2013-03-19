/*
 * Created on 26.08.2010
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
 * This class ExpScript belongs to a Java Persistence Unit. 
 * Instances of this class are used to create or change a corresponding database object.
 * ExpScript database objects can also be retrieved as instances of this class.
 */
@Entity
@XmlRootElement(name="expscript")
@XmlType(name = "expscript", propOrder = {
		"id",
		"name",
        "author",
        "description",
        "expScriptSections"})
@ImplicitProduces("text/html")
@Path("view/ExpScript/")
public class ExpScript {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="keys")
    private int id;
    private String name;
    private String author;
    private String description;
    
    @ManyToOne
    private Experiment experiment;
    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="expScript")
    private List<ExpScriptSection> expScriptSections;
    
    public ExpScript() {}
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @XmlElement(required=true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(required=true)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    @XmlElementWrapper(name="expscriptsections", required=true)
    @XmlElements(@XmlElement(name="expscriptsection"))
    public List<ExpScriptSection> getExpScriptSections() {
    	
    	try {
			Collections.sort(expScriptSections, new ExpScriptSectionComparator());
		} catch (Exception e) {

		}
        return expScriptSections;
    }

    public void setExpScriptSections(List<ExpScriptSection> expScriptSections) {
        this.expScriptSections = expScriptSections;
    }
    
    /**
     * This method acts on HTTP-GET-request,retrieves an ExpScript database object 
     * from the attached sql database and returns it in a Jersey implicit view
   	 * @param id the primary key of wanted ExpScript object
	 * @return the wanted ExpScript object
     */
    @Path("{id}")
    public ExpScript getExpScript(@PathParam("id") String id){
    	
    	
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
		EntityManager em = factory.createEntityManager();
		ExpScript expScript = em.find(ExpScript.class, Integer.parseInt(id));
		
		Collections.sort(expScript.getExpScriptSections(), new ExpScriptSectionComparator());

		return expScript;
    	
    }
    
    /**
	 * This private class compares the position attributes of ExpScriptSections
	 * @author Wolfgang Ostermeier
	 *
	 */
    private class ExpScriptSectionComparator implements Comparator<ExpScriptSection> {
    	 
    	  public int compare(ExpScriptSection e1, ExpScriptSection e2) {
    	 
    	    if (e1.getPosition() == e2.getPosition()) {
    	    	
    	      return 0;
    	    }
    	    else if (e1.getPosition() > e2.getPosition()) {
    	      return 1;
    	    }
    	    else {
    	      return -1;
    	    }
    	  }
    }
}
