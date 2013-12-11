package mobile.mylocationstats.domain;

import java.util.Calendar;

public class Target {
	
	private Location location;
	private Calendar dueDate;
	
	public Target(Location location, Calendar dueDate) {
		this.location = location;
		this.dueDate = dueDate;
	}
	
	public double getX() {
		return location.getX();
	}
	
	public double getY() {
		return location.getY();
	}
	
	public Calendar getDueDate() {
		return dueDate;
	}
	
	public Location getLocation() {
		return location;
	}

}
