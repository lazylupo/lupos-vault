package de.inmotion.findme.servlet.resources;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Matcher {
	
	@Id
	@GeneratedValue
	private long id;
	
	private long user1_id;
	private long user2_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUser1_id() {
		return user1_id;
	}
	public void setUser1_id(long user1_id) {
		this.user1_id = user1_id;
	}
	public long getUser2_id() {
		return user2_id;
	}
	public void setUser2_id(long user2_id) {
		this.user2_id = user2_id;
	}
	
	

}
