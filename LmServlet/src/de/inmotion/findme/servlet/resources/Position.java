package de.inmotion.findme.servlet.resources;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;
import org.eclipse.persistence.config.CacheType;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Kapselt die verschiedenen Eigenschaften einer Position.
 * Wird unter anderm von Jackson zur automatischen JSON-Konvertierung genutzt.
 * @author Tobias Wochinger
 *
 */

@Entity
@Cache(isolation=CacheIsolationType.ISOLATED)
@JsonIgnoreProperties(value = { "user" })
public class Position {
	
	@Id
	@GeneratedValue
	private long id;
	
	private double latitude;
	private double longitude;
	private long timestamp;
	
	
	@Transient
	private long userid;
	
	@Transient
	public long getUserid() {
		return userid;
	}
	@Transient
	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	
	@OneToOne
	private User user;
	
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}
	
	public static double calcDistanceInKm(double lat1, double lng1, double lat2, double lng2) {
		
	    int r = 6371; // average radius of the earth in km
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLon = Math.toRadians(lng2 - lng1);
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	       Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) 
	      * Math.sin(dLon / 2) * Math.sin(dLon / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double d = r * c;
	    return d;
	}

}
