package mobile.mylocationstats.domain;

import java.util.Calendar;

public class Visit {
	
	private long id;
	private Location location;
	private Calendar visitedOn;
	
	public Visit(Location location, Calendar visited_on) {
		this.location = location;
		this.visitedOn = visited_on;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setVisitedOn(Calendar visitedOn) {
		this.visitedOn = visitedOn;
	}
	
	public Calendar getVisitedOn() {
		return visitedOn;
	}

}
