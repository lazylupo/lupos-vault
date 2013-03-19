/*
 * Created on 24.04.2010
 *
 * Project: WebExperiment
 * Original author: draxler
 * 
 * edited by: Wolfgang Ostermeier  in 2012/13
 */
package model.db;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;
import javax.persistence.Transient;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.sun.jersey.api.view.ImplicitProduces;

/**
 * This class ExpItem belongs to a Java Persistence Unit. 
 * Instances of this class are used to create or change a corresponding database object.
 * ExpItem database objects can also be retrieved as instances of this class.
 */
@Entity
@XmlRootElement
@XmlType(name = "expitem", propOrder = {
		"id",
        "position",
        "label",
        "options",
        "assessmentoptions",
        "expected",
        "url"})
@ImplicitProduces("text/html")
@Path("view/ExpItem")
public class ExpItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="keys")
    private int id;
    private String url;
    private String label;
    private String options;
    private String assessmentoptions;
    private int position;
    private String expected;

	@Transient
    private List<String> urlsSep = new ArrayList<String>();
	@Transient
    private List<String> optionsSep = new ArrayList<String>();
    @Transient
    private List<String> assessmentOptionsSep = new ArrayList<String>();

	// --- linking fields ----------
    @ManyToOne
    private ExpScriptSection expScriptSection;

    
    
    public ExpItem() {}
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name="url", required=true)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlElement(required=true)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAssessmentoptions() {
        return assessmentoptions;
    }

    public void setAssessmentoptions(String assessmentoptions) {
        this.assessmentoptions = assessmentoptions;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public void setExpScriptSection(ExpScriptSection section) {
        this.expScriptSection = section;
    }
    
    @XmlTransient
    public ExpScriptSection getExpScriptSection() {
        return expScriptSection;
    }
    
	/**
	 * This method creates a list of the items contained in the url String(separated with a |)
	 * and returns it
	 * @return the created list
	 */
    @XmlTransient
    public List<String> getUrlsSep() {
    	
    	this.urlsSep.clear();
    	String[] result = this.url.split("\\|");
    	for(String url : result){
    		if(!url.equals("")){
    		this.urlsSep.add(url);
    		}
    	}
		return urlsSep;
	};

	public void setUrlsSep(List<String> urlsSep) {
		this.urlsSep = urlsSep;
	};
	
	/**
	 * This method creates a list of the items contained in the options String(separated with a |)
	 * and returns it
	 * @return the created list
	 */
	@XmlTransient
	public List<String> getOptionsSep() {
		
		this.optionsSep.clear();
    	String[] result = this.options.split("\\|");
    	for(String option : result){
    		if(!option.equals("")){
    		this.optionsSep.add(option);
    		}
    	}
    	
		return optionsSep;
	};

	public void setOptionsSep(List<String> optionsSep) {
		this.optionsSep = optionsSep;
	};

	/**
	 * This method creates a list of the items contained in the assessmentoptions String(separated with a |)
	 * and returns it
	 * @return the created list
	 */
	@XmlTransient
	public List<String> getAssessmentOptionsSep() {
		
		this.assessmentOptionsSep.clear();
    	String[] result = this.assessmentoptions.split("\\|");
    	for(String assessmentoption : result){
    		if(!assessmentoption.equals("")){
    		this.assessmentOptionsSep.add(assessmentoption);
    		}
    	}
    	
		return assessmentOptionsSep;
	};

	public void setAssessmentOptionsSep(List<String> assessmentOptionsSep) {
		this.assessmentOptionsSep = assessmentOptionsSep;
	};

	/**
     * This method acts on HTTP-GET-request,retrieves an ExpItem database object 
     * from the attached sql database and returns it in a Jersey implicit view
   	 * @param id the primary key of wanted ExpItem object
	 * @return the wanted ExpItem object
     */
    @Path("/{id}")
    public ExpItem getExpItem(@PathParam("id") String id){
    	
    	
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("webExperiment");
		EntityManager em = factory.createEntityManager();
		ExpItem expItem = em.find(ExpItem.class, Integer.parseInt(id));

		return expItem;
    	
    }
    /**
     * This methods checks whether the file associated with a given url is a wav-file or not 
     * @param urlName the url of the file to check
     * @return boolean
     */
    public static boolean isValid(String urlName){
        try {
          HttpURLConnection.setFollowRedirects(false);
          
          urlName = urlName.replace(" ", "%20");
          HttpURLConnection con =
             (HttpURLConnection) new URL(urlName).openConnection();
          con.setRequestMethod("HEAD");
          con.setConnectTimeout(100);
          return (con.getContentType().equals("audio/x-wav"));
        }
        catch (Exception e) {

           return false;
        }
    }
}
