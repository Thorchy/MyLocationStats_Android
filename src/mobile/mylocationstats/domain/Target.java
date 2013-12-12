package mobile.mylocationstats.domain;

import java.util.Calendar;

public class Target {
	
	private long id;
	private Location location;
	private Calendar dueDate;
	
	public Target(Location location, Calendar dueDate) {
		this.location = location;
		this.dueDate = dueDate;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public double getLatitude() {
		return location.getLatitude();
	}
	
	public double getLongitude() {
		return location.getLongitude();
	}
	
	public Calendar getDueDate() {
		return dueDate;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public String getDueDateFormatted() {
		return dueDate.get(Calendar.YEAR) + "-" + (dueDate.get(Calendar.MONTH) + 1) + "-" + dueDate.get(Calendar.DAY_OF_MONTH); 
	}
	
	@Override
	public String toString() {
		return location.getName() + " (" + dueDate.get(Calendar.YEAR) + "-" + (dueDate.get(Calendar.MONTH) + 1) + "-"  + dueDate.get(Calendar.DAY_OF_MONTH)  + "): " + getLatitude() + "; " + getLongitude();
	}

}
