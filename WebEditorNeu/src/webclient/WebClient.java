package webclient;

import javax.ws.rs.core.MultivaluedMap;

import model.db.ExpEditor;
import model.db.ExpItem;
import model.db.ExpScript;
import model.db.ExpScriptSection;
import model.db.Experiment;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The class WebClient implements a Jersey Client to create and send REST-HTTP-Requests 
 * to the REST-Servlet-classes in the rest-package
 * @author Wolfgang Ostermeier
 *
 */
public class WebClient
{ 
	/**
	 * The host address to be added to any request
	 */
	private String host;
	
	/**
	 * The Jersey Client
	 */
	private Client client;
	
	
   /**
    * The constructor initializes a WebClient instance with a host address and a Jersey Client instance
    * @param host String of the host address
    */
   public WebClient(String host){
	   
	   this.host= host;//+"80";// +"80" must be added on some Tomcat Servers, on others it must be removed
	   this.client=Client.create();
   }
   
   /**
    * This method initializes a change operation on an ExpEditor object on an SQL database via REST-request 
    * @param id primary key of the ExpEditor object for localization
    * @param firstname changeable attribute, "-1" for no change
    * @param name changeable attribute, "-1" for no change
    * @param email changeable attribute, "-1" for no change
    * @param password changeable attribute, "-1" for no change
    * @param phone changeable attribute, "-1" for no change
    * @param organisation changeable attribute, "-1" for no change
    * @return the HTTP status code received after sending the REST-request
    */
   public int changeExpEditor(String id, String firstname,String name, String email, String password, String phone, String organisation){
	   
	  String starturl = host+"/WebEditor/rest/ExpEditorToDB/";
      WebResource wrs = client.resource(starturl);
      
      MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
      queryData.add("firstname", firstname);
      queryData.add("name", name);
      queryData.add("email", email);
      queryData.add("password", password);
      queryData.add("phone", phone);
      queryData.add("organisation", organisation);

      ClientResponse response = wrs.path(id).queryParams(queryData).put(ClientResponse.class);
 
      return response.getStatus();


   }
   
   /**
    * This method initializes a create operation of an ExpEditor object on an SQL database via REST-request
    * @param name settable attribute
    * @param firstName settable attribute
    * @param email settable attribute
    * @param password settable attribute
    * @param phone settable attribute
    * @param organisation settable attribute
    * @return the primary key of the new database object
    */
   public String createExpEditor(String name, String firstName, String email, String password, String phone, String organisation){

	   String starturl = host+"/WebEditor/rest/ExpEditorToDB/";
	      try {
			WebResource wrs = client.resource(starturl);
			  
			  MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
			  queryData.add("name", name);
			  queryData.add("firstname", firstName);
			  queryData.add("email", email);
			  queryData.add("password", password);
			  queryData.add("phone", phone);
			  queryData.add("organisation", organisation);
			  
			  String response = wrs.queryParams(queryData).post(String.class);
			  
			  return response;
			  
		} catch (Exception e) {
		
			return null;
		}
	      
	 }
   
   /**
    * This method initializes a retrieve operation of an ExpEditor object via its email and password attribute from an SQL database via REST-request
    * @param email email attribute of wanted ExpEditor object
    * @param password password attribute of wanted ExpEditor object
    * @return the wanted ExpEditor object
    */
   public ExpEditor getExpEditor(String email, String password){
	   		
	   String starturl = host+"/WebEditor/rest/ExpEditorToDB/";
	      try {
			   WebResource wrs = client.resource(starturl);

			   ExpEditor response = wrs.path(email+"/"+password).get(ExpEditor.class);

			  return response;
			  
		} catch (Exception e) {
			return null;
		}


	}
   
   /**
    * This method initializes a delete operation of an ExpEditor object from an SQL database via REST-request
    * @param id the primary key of the ExpEditor object to be deleted
    * @return the HTTP status code received after sending the REST-request
    */
   public int deleteExpEditor(String id){
	   		
	   	  String starturl = host+"/WebEditor/rest/ExpEditorToDB/";
	      WebResource wrs = client.resource(starturl);
	      

	      ClientResponse response = wrs.path(id).delete(ClientResponse.class);

	      return response.getStatus();


	}
   
   /**
    * This method initializes a create operation of an Experiment object on an SQL database via REST-request
    * @param name settable attribute
    * @param description settable attribute
    * @param expeditorid settable attribute
    * @param projectid settable attribute
    * @return the primary key of the new database object
    */
   public String createExperiment(String name, String description, String expeditorid, String projectid){

	      try {
			String starturl = host+"/WebEditor/rest/ExperimentToDB/";
			  WebResource wrs = client.resource(starturl);

			  MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
			  queryData.add("name", name);
			  queryData.add("description", description);
			  queryData.add("expeditorid", expeditorid);
			  queryData.add("projectid", projectid);

			  String response = wrs.queryParams(queryData).post(String.class);
  
			  return response;
		} catch (Exception e) {

			return null;
		}


	     
	 }
   
   /**
    * This method initializes a change operation on an Experiment object on an SQL database via REST-request
    * @param id primary key of the Experiment object for localization
    * @param name changeable attribute, "-1" for no change
    * @param description changeable attribute, "-1" for no change
    * @param status changeable attribute, "-1" for no change
    * @return the HTTP status code received after sending the REST-request
    */
   public int changeExperiment(String id, String name, String description, String status){
	   		
	   	  String starturl = host+"/WebEditor/rest/ExperimentToDB/";
	      WebResource wrs = client.resource(starturl);
	      
	      MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
	      queryData.add("name", name);
	      queryData.add("description", description);
	      queryData.add("status", status);

	      ClientResponse response = wrs.path(id).queryParams(queryData).put(ClientResponse.class);
	      
	      return response.getStatus();


   }
   
   /**
    * This method initializes a retrieve operation of an Experiment from an SQL database via REST-request
    * @param id primary key of wanted Experiment object
    * @return the wanted Experiment object
    */
   public Experiment getExperiment(String id){
	   
	   	  String starturl = host+"/WebEditor/rest/ExperimentToDB/";
	      WebResource wrs = client.resource(starturl); 
	      
	      try {


	    	  Experiment response = wrs.path(id).get(Experiment.class);

				  return response;
				  
			} catch (Exception e) {

				return null;
			}

   }
   
   /**
    * This method initializes a delete operation of an Experiment object from an SQL database via REST-request
    * @param id the primary key of the Experiment object to be deleted
    * @return the HTTP status code received after sending the REST-request
    */
   public int deleteExperiment(String id){
	   		
	   	  String starturl = host+"/WebEditor/rest/ExperimentToDB/";
	      WebResource wrs = client.resource(starturl);

	      ClientResponse response = wrs.path(id).delete(ClientResponse.class);
	      
	      return response.getStatus();


	}
   
   /**
    * This method initializes a create operation of an ExpScript object on an SQL database via REST-request
    * @param name settable attribute
    * @param description settable attribute
    * @param author settable attribute
    * @param id the primary key of the parent database object
    * @return the primary key of the new database object
    */
   public String createExpScript(String name, String description, String author, String id){

	      try {
			String starturl = host+"/WebEditor/rest/ExpScriptToDB/";
			  WebResource wrs = client.resource(starturl);

			  MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
			  queryData.add("name", name);
			  queryData.add("description", description);
			  queryData.add("author", author);
			  queryData.add("id", id);

			  String response = wrs.queryParams(queryData).post(String.class);
 
				  return response;
		} catch (Exception e) {
			
			return null;
		}

   }
   
   /**
    * This method initializes a change operation on an ExpScript object on an SQL database via REST-request
    * @param id primary key of the ExpScript object for localization
    * @param name changeable attribute, "-1" for no change
    * @param description changeable attribute, "-1" for no change
    * @param author changeable attribute, "-1" for no change
    * @return the HTTP status code received after sending the REST-request
    */
   public int changeExpScript( String id, String name, String description, String author){
  		
	   	  String starturl = host+"/WebEditor/rest/ExpScriptToDB/";
	      WebResource wrs = client.resource(starturl);
	      
	      MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
	      queryData.add("name", name);
	      queryData.add("author", author);
	      queryData.add("description", description);
	      

	      ClientResponse response = wrs.path(id).queryParams(queryData).put(ClientResponse.class);

	      return response.getStatus();


   }
   
   /**
    * This method initializes a retrieve operation of an ExpScript object from an SQL database via REST-request
    * @param id primary key of wanted ExpScript object
    * @return the wanted ExpScript object
    */
   public ExpScript getExpScript(String id){
	   
	   	  String starturl = host+"/WebEditor/rest/ExpScriptToDB/";
	      WebResource wrs = client.resource(starturl); 
	      
	      try {


	    	  ExpScript response = wrs.path(id).get(ExpScript.class);

				  return response;
				  
			} catch (Exception e) {

				return null;
			}

   }
   
   /**
    * This method initializes a delete operation of an ExpScript object from an SQL database via REST-request
    * @param id the primary key of the ExpScript object to be deleted
    * @return the HTTP status code received after sending the REST-request
    */
   public int deleteExpScript(String id){
		
	   	  String starturl = host+"/WebEditor/rest/ExpScriptToDB/";
	      WebResource wrs = client.resource(starturl);
	      

	      ClientResponse response = wrs.path(id).delete(ClientResponse.class);
	      
	      return response.getStatus();
   }
   
   /**
    * This method initializes a create operation of an ExpScriptSection object on an SQL database via REST-request
    * @param name settable attribute
    * @param position settable attribute
    * @param ordering settable attribute
    * @param maxreplay settable attribute
    * @param scrid the primary key of the parent database object
    * @return the primary key of the new database object
    */
   public String createExpScriptSection(String name, String position, String ordering, String maxreplay, String scrid){

	      try {
			String starturl = host+"/WebEditor/rest/ExpScriptSectionToDB/";
			  WebResource wrs = client.resource(starturl);

			  MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
			  queryData.add("name", name);
			  queryData.add("position", position);
			  queryData.add("ordering", ordering);
			  queryData.add("maxreplay", maxreplay);
			  queryData.add("scrid", scrid);

			  String response = wrs.queryParams(queryData).post(String.class);

			  return response;
		} catch (Exception e) {
			
			return null;
		}
	}
   
   /**
    * This method initializes a change operation on an ExpScriptSection object on an SQL database via REST-request
    * @param id primary key of the ExpScriptSection object for localization
    * @param position changeable attribute, "-1" for no change
    * @param name changeable attribute, "-1" for no change
    * @param ordering changeable attribute, "-1" for no change
    * @param maxreplay changeable attribute, "-1" for no change
    * @return the HTTP status code received after sending the REST-request
    */
   public int changeExpScriptSection(String id, String position, String name, String ordering, String maxreplay){

	      String starturl = host+"/WebEditor/rest/ExpScriptSectionToDB/";
	      WebResource wrs = client.resource(starturl);

	      MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
	      queryData.add("position", position);
	      queryData.add("name", name);
	      queryData.add("ordering", ordering);
	      queryData.add("maxreplay", maxreplay);

	      ClientResponse response = wrs.path(id).queryParams(queryData).put(ClientResponse.class);

	      return response.getStatus();
	}
   
   /**
    * This method initializes a retrieve operation of an ExpScriptSection object from an SQL database via REST-request
    * @param id primary key of wanted ExpScriptSection object
    * @return the wanted ExpScriptSection object
    */
   public ExpScriptSection getExpScriptSection(String id){
	   
	   	  String starturl = host+"/WebEditor/rest/ExpScriptSectionToDB/";
	      WebResource wrs = client.resource(starturl); 
	      
	      try {


	    	  ExpScriptSection response = wrs.path(id).get(ExpScriptSection.class);

				  return response;
				  
			} catch (Exception e) {

				return null;
			}

   }
   
   /**
    * This method initializes a delete operation of an ExpScriptSection object from an SQL database via REST-request
    * @param id the primary key of the ExpScriptSection object to be deleted
    * @return the HTTP status code received after sending the REST-request
    */
	public int deleteExpScriptSection(String id){
		
	   	  String starturl = host+"/WebEditor/rest/ExpScriptSectionToDB/";
	      WebResource wrs = client.resource(starturl);
	      

	      ClientResponse response = wrs.path(id).delete(ClientResponse.class);

	      return response.getStatus();


	}
	
    /**
     * This method initializes a create operation of an ExpItem object on an SQL database via REST-request
     * @param position settable attribute
     * @param label settable attribute
     * @param options settable attribute
     * @param assessmentoptions settable attribute
     * @param expected settable attribute
     * @param url settable attribute
     * @param scrsid the primary key of the parent database object
     * @return the primary key of the new database object
     */
	public String createExpItem(String position, String label, String options, String assessmentoptions, String expected, String url, String scrsid){

	      try {
			String starturl = host+"/WebEditor/rest/ExpItemToDB/";
			  WebResource wrs = client.resource(starturl);

			  MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
			  queryData.add("position", position);
			  queryData.add("label", label);
			  queryData.add("options", options);
			  queryData.add("assessmentoptions", assessmentoptions);
			  queryData.add("expected", expected);
			  queryData.add("url", url);
			  queryData.add("scrsid", scrsid);

			  String response = wrs.queryParams(queryData).post(String.class);

			  return response;
		} catch (Exception e) {
			
			return null;
		}
	}
	
    /**
     * This method initializes a change operation on an ExpItem object on an SQL database via REST-request
     * @param id primary key of the ExpItem object for localization
     * @param position changeable attribute, "-1" for no change
     * @param label changeable attribute, "-1" for no change
     * @param options changeable attribute, "-1" for no change
     * @param assessmentoptions changeable attribute, "-1" for no change
     * @param expected changeable attribute, "-1" for no change
     * @param url changeable attribute, "-1" for no change
     * @return the HTTP status code received after sending the REST-request
     */
	public int changeExpItem(String id, String position, String label, String options, String assessmentoptions,
			                String expected,  String url){
  		
	   	  String starturl = host+"/WebEditor/rest/ExpItemToDB/";
	      WebResource wrs = client.resource(starturl);
	      
	      MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
	      queryData.add("position", position);
	      queryData.add("label", label);
	      queryData.add("options", options);
	      queryData.add("assessmentoptions", assessmentoptions);
	      queryData.add("expected", expected);
	      queryData.add("url", url);
	      
	      ClientResponse response = wrs.path(id).queryParams(queryData).put(ClientResponse.class);
	      
	      return response.getStatus();


	}
	
    /**
     * This method initializes a retrieve operation of an ExpItem object from an SQL database via REST-request
     * @param id primary key of wanted ExpItem object
     * @return the wanted ExpItem object
     */
	public ExpItem getExpItem(String id){
	   
	   	  String starturl = host+"/WebEditor/rest/ExpItemToDB/";
	      WebResource wrs = client.resource(starturl);
	      
	      try {

	    	  ExpItem response = wrs.path(id).get(ExpItem.class);

				  return response;
				  
			} catch (Exception e) {

				return null;
			}
	}
	
   /**
    * This method initializes a delete operation of an ExpItem object from an SQL database via REST-request
    * @param id the primary key of the ExpItem object to be deleted
    * @return the HTTP status code received after sending the REST-request
    */
   public int deleteExpItem(String id){
 		
	   	  String starturl = host+"/WebEditor/rest/ExpItemToDB/";
	      WebResource wrs = client.resource(starturl);
	      

	      ClientResponse response = wrs.path(id).delete(ClientResponse.class);

	      return response.getStatus();


   }
   
   /**
    * This method initializes a change operation of the position attribute of multiple ExpScriptSection 
    * objects and the adjustment of the position attribute of affected ExpScriptSection objects via REST-request
    * @param selItems primary keys of the ExpScriptSection objects to be changed
    * @param position new value for the position attribute of the first ExpScriptSection object
    * @return the HTTP status code received after sending the REST-request
    */
   public int moveMultipleExpScriptSections(String selItems, String position){
	   
	   String starturl = host+"/WebEditor/rest/ExpScriptSectionToDB/multimove";
	      WebResource wrs = client.resource(starturl);

	      MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
	      queryData.add("selItems", selItems);
	      queryData.add("position", position);
	      
	

	      ClientResponse response = wrs.queryParams(queryData).put(ClientResponse.class);

	      return response.getStatus();
	   
   }
   
   /**
    * This method initializes a change operation of the position attribute of multiple ExpItem 
    * objects and the adjustment of the position attribute of affected ExpItem objects via REST-request
    * @param selItems primary keys of the ExpItem objects to be changed
    * @param position new value for the position attribute of the first ExpItem object
    * @return the HTTP status code received after sending the REST-request
    */
   public int moveMultipleExpItems(String selItems, String position){
	   
	   String starturl = host+"/WebEditor/rest/ExpItemToDB/multimove";
	      WebResource wrs = client.resource(starturl);

	      MultivaluedMap<String, String> queryData = new MultivaluedMapImpl();
	      queryData.add("selItems", selItems);
	      queryData.add("position", position);
	      
	

	      ClientResponse response = wrs.queryParams(queryData).put(ClientResponse.class);

	      return response.getStatus();
	   
   }
   
   /**
    * This method creates a copy of an Experiment object and inserts it into a target parent object
    * @param pasteID the primary key of the Experiment object to be copied 
    * @param targetID the primary key of the target parent object
    * @param expeditorid the primary key of the acting ExpEditor
    * @return the primary key of the new Experiment object
    */
   public String pasteExperiment(String pasteID, String targetID, String expeditorid){
	   
	   	  Experiment experiment  = this.getExperiment(pasteID);
	   	  
	   	  String newId =this.createExperiment(experiment.getName(), experiment.getDescription(), expeditorid, targetID);
	   	  
	   	  for(ExpScript expscr :experiment.getExpScripts()){
	   		  
	   		String scriptID =this.createExpScript(expscr.getName(), expscr.getDescription(), expscr.getAuthor(), newId);
	   	    for(ExpScriptSection expscrs :expscr.getExpScriptSections()){
	   	    	String sectionID = this.createExpScriptSection(expscrs.getName(),String.valueOf(expscrs.getPosition()),expscrs.getOrdering(), String.valueOf(expscrs.getMaxreplay()), scriptID);
	   	    	for(ExpItem expi :expscrs.getExpItems()){
	   	    		this.createExpItem(String.valueOf(expi.getPosition()), expi.getLabel(), expi.getOptions(), expi.getAssessmentoptions(), expi.getExpected(), expi.getUrl(), sectionID);
	   	    	}
	   	    }
	   	  }
	   	  
	   	return newId;
   }
   
   /**
    * This method creates a copy of an ExpScript object and inserts it into a target parent object
    * @param pasteID the primary key of the ExpScript object to be copied 
    * @param targetID the primary key of the target parent object
    * @return the primary key of the new ExpScript object
    */
   public String pasteExpScript(String pasteID, String targetID){
	   		
	   	  ExpScript expScript  = this.getExpScript(pasteID);
	   	  
	   	  String newId =this.createExpScript(expScript.getName(), expScript.getDescription(), expScript.getAuthor(), targetID);

	   	    for(ExpScriptSection expscrs :expScript.getExpScriptSections()){
	   	    	String sectionID = this.createExpScriptSection(expscrs.getName(),String.valueOf(expscrs.getPosition()),expscrs.getOrdering(), String.valueOf(expscrs.getMaxreplay()), newId);
	   	    	for(ExpItem expi :expscrs.getExpItems()){
	   	    		this.createExpItem(String.valueOf(expi.getPosition()), expi.getLabel(), expi.getOptions(), expi.getAssessmentoptions(), expi.getExpected(), expi.getUrl(), sectionID);
	   	    	}
	   	    }

	   	return newId;

   }
   
   /**
    * This method creates a copy of an ExpScriptSection object and inserts it into a target parent object
    * @param pasteID the primary key of the ExpScriptSection object to be copied 
    * @param targetID the primary key of the target parent object
    * @return the primary key of the new ExpScriptSection object
    */
   public String pasteExpScriptSection(String pasteID, String targetID){
	   	
	   synchronized(this){
	   	  ExpScriptSection expScriptSection  = this.getExpScriptSection(pasteID);
	   	  
	   	  String newId =this.createExpScriptSection(expScriptSection.getName(),"",expScriptSection.getOrdering(), String.valueOf(expScriptSection.getMaxreplay()), targetID);


	   	    	for(ExpItem expi :expScriptSection.getExpItems()){
	   	    		this.createExpItem(String.valueOf(expi.getPosition()), expi.getLabel(), expi.getOptions(), expi.getAssessmentoptions(), expi.getExpected(), expi.getUrl(), newId);
	   	    	}

	   	return newId;
	   }

   }
   
   /**
    * This method creates a copy of an ExpItem object and inserts it into a target parent object
    * @param pasteID the primary key of the ExpItem object to be copied 
    * @param targetID the primary key of the target parent object
    * @return the primary key of the new ExpItem object
    */
   public String pasteExpItem(String pasteID, String targetID){
	   
	   synchronized(this){
	   	  ExpItem expItem  = this.getExpItem(pasteID);
	   	  
	   	  String newId =this.createExpItem("-1", expItem.getLabel(), expItem.getOptions(), expItem.getAssessmentoptions(), expItem.getExpected(), expItem.getUrl(), targetID);

	   	return newId;
	   }

   }

}
