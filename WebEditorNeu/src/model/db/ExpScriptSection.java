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
 * This class ExpScriptSection belongs to a Java Persistence Unit. 
 * Instances of this class are used to create or change a corresponding database object.
 * ExpScriptSection database objects can also be retrieved as instances of this class.
 */
@XmlRootElement
@XmlType(name = "expscriptsection", propOrder = {
        "id",
		"position",
        "name",
        "ordering",
        "active",
        "maxreplay",
        "expItems"})
@Entity
@ImplicitProduces("text/html")
@Path("view/ExpScriptSection/")
public class ExpScriptSection {

    public static final String SEQUENTIAL = "sequential";
    public static final String RANDOM = "random";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="keys")
    private int id;
    private String name;
    private int position;
    private String ordering = SEQUENTIAL;
    private boolean active;
    private int maxreplay;
    
    @ManyToOne
    private ExpScript expScript;
    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="expScriptSection")
    private List<ExpItem> expItems;
    
    public ExpScriptSection() {
    }
    
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


    @XmlElement(required=true)
    public int getPosition() {
        return position;
    }


    public void setPosition(int position) {
        this.position = position;
    }


    @XmlElement(required=true)
    public String getOrdering() {
        return ordering;
    }


    public void setOrdering(String ordering) {
        this.ordering = ordering;
    }


    @XmlElement(required=true)
    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    @XmlElement(required=true)
    public int getMaxreplay() {
        return maxreplay;
    }


    public void setMaxreplay(int maxreplay) {
        this.maxreplay = maxreplay;
    }


    @XmlTransient
    public ExpScript getExpScript() {
        return expScript;
    }


    public void setExpScript(ExpScript expScript) {
        this.expScript = expScript;
    }

    @XmlElementWrapper(name="expitems")
    @XmlElements(@XmlElement(name="expitem"))
    public List<ExpItem> getExpItems() {
    	
    	try {
			Collections.sort(expItems, new ExpItemComparator());
		} catch (Exception e) {

		}
        return expItems;
    }


    public void setExpItems(List<ExpItem> expItems) {
        this.expItems = expItems;
    }
    
    /**
     * This method sorts a list of ExpItems by their position
     */
    public void sortExpItems(){
    	
    	Collections.sort(this.getExpItems(), new ExpItemComparator());
    	
    }
    
    /**
     * This method acts on HTTP-GET-request,retrieves an ExpScriptSection database object 
     * from the attached sql database and returns it in a Jersey implicit view
   	 * @param id the primary key of wanted ExpScriptSection object
	 * @return the wanted ExpScriptSection object
     */
    @Path("{id}")
    public ExpScriptSection getExpScriptSection(@PathParam("id") String id){
    	
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
		EntityManager em = factory.createEntityManager();
		ExpScriptSection expScriptSection = em.find(ExpScriptSection.class, Integer.parseInt(id));
		
		expScriptSection.sortExpItems();

		return expScriptSection;	
    }
    
    /**
	 * This private class compares the position attributes of ExpItems
	 * @author Wolfgang Ostermeier
	 *
	 */
    private class ExpItemComparator implements Comparator<ExpItem> {
   	 
  	  public int compare(ExpItem e1, ExpItem e2) {
  	 
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
    
    /**
	 * This static method retrieves an ExpScriptSection database object from the attached sql database
	 * @param id the primary key of wanted ExpScriptSection object
	 * @return the wanted ExpScriptSection object
	 */
	public static ExpScriptSection getExpScriptSectionStatic(int id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
		EntityManager em = factory.createEntityManager();
		ExpScriptSection expScriptSection = em.find(ExpScriptSection.class, id);
		
		expScriptSection.sortExpItems();

		return expScriptSection;
	}

}
