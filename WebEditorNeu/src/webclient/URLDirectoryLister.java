package webclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * The class URLDirectoryLister creates a list of the names of the files that are contained in an 
 * Apache Server url directory
 * @author Wolfgang Ostermeier
 *
 */
public class URLDirectoryLister {
	/**
	 * the url of the Apache Server url directory
	 */
	private URL url;
	
	/**
	 * The constructor initializes an instance of the class with the url of the 
	 * Apache Server url directory for the file name listing
	 * @param url the url of the Apache Server url directory
	 * @throws IOException
	 */
	public URLDirectoryLister(String url) throws IOException {

			this.url = new URL(url);
	}
	
	/**
	 * This method creates the file name listing of the Apache Server url directory located 
	 * in the private field url
	 * @return the file name list
	 */
	public List<String> getListing(){
		
			try {
				
				List<String> listing = new ArrayList<String>();
				
				String myString = IOUtils.toString(this.url, "UTF-8");
				String[] trs = myString.split("<tr>");
				    for(int i=4;i<trs.length-1;i++){
				    	
				    	String[] hrefs = trs[i].split("href=\"");
				    	String[] titles = hrefs[1].split("\"");
				    	listing.add(titles[0]);
				    }

						return listing;
				} catch (Exception e) {
	
					return null;
				}
    }

}
