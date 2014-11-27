package de.inmotion.findme.servlet.resources;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@JsonIgnoreProperties(value = { "user" })
public class Adventure {
	
	@Id
	@GeneratedValue
	private long id;
	private boolean active;
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	private boolean meAlone;
	private boolean dateAlone;
	private int perimeter;
	private String whereabout;
	private String distFeature;
	private boolean oppositeFemale;
	private boolean oppositeMale;
	
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
	
	@Transient
	private Calendar matchExpireTime;
	
	@Transient
	public Calendar getMatchExpireTime() {
		return matchExpireTime;
	}
	
	@Transient
	public void setMatchExpireTime(Calendar matchExpireTime) {
		this.matchExpireTime = matchExpireTime;
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
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public boolean isMeAlone() {
		return meAlone;
	}
	public void setMeAlone(boolean meAlone) {
		this.meAlone = meAlone;
	}
	public boolean isDateAlone() {
		return dateAlone;
	}
	public void setDateAlone(boolean dateAlone) {
		this.dateAlone = dateAlone;
	}
	public int getPerimeter() {
		return perimeter;
	}
	public void setPerimeter(int perimeter) {
		this.perimeter = perimeter;
	}
	public String getWhereabout() {
		return whereabout;
	}
	public void setWhereabout(String whereabout) {
		this.whereabout = whereabout;
	}
	public String getDistFeature() {
		return distFeature;
	}
	public void setDistFeature(String distFeature) {
		this.distFeature = distFeature;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isOppositeFemale() {
		return oppositeFemale;
	}
	public void setOppositeFemale(boolean oppositeFemale) {
		this.oppositeFemale = oppositeFemale;
	}
	public boolean isOppositeMale() {
		return oppositeMale;
	}
	public void setOppositeMale(boolean oppositeMale) {
		this.oppositeMale = oppositeMale;
	}
	

}
