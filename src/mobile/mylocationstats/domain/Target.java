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
	
	@Override
	public String toString() {
		return location.getName() + " (" + dueDate.get(Calendar.YEAR) + "-" + dueDate.get(Calendar.MONTH) + "-"  + dueDate.get(Calendar.DAY_OF_MONTH)  + "): " + getX() + "; " + getY();
	}

}
