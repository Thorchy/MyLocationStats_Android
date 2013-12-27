package mobile.mylocationstats.domain;

import java.util.Calendar;

public class Location {
	
	private long id;
	private String name;
	private double latitude;
	private double longitude;
	private int totalVisited;
	private Calendar lastVisited;
	
	public Location(double latitude, double  longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		lastVisited = Calendar.getInstance();
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	
	public void setTotalVisited(int totalVisited) {
		this.totalVisited = totalVisited;
	}
	
	public int getTotalVisited() {
		return totalVisited;
	}
	
	public void setLastVisited(Calendar lastVisited) {
		this.lastVisited = lastVisited;
	}
	
	public Calendar getLastVisited() {
		return lastVisited;
	}
	
	public String getLastVisitedFormatted() {
		return lastVisited.get(Calendar.YEAR) + "-" + (lastVisited.get(Calendar.MONTH) + 1) + "-" + lastVisited.get(Calendar.DAY_OF_MONTH); 
	}
	
	public String getCoordinates() {
		return getLatitude() + ";" + getLongitude();
	}
	
	@Override
	public String toString() {
		return name + " (" + totalVisited + "): " + getLatitude() + "; " + getLongitude();
	}
	
	public float distanceFrom(Location location) {
		float[] distance = new float[3];
		android.location.Location.distanceBetween(getLatitude(), getLongitude(), location.getLatitude(), location.getLongitude(), distance);
	
		return distance[0];
	}

}
