package mobile.mylocationstats.domain;

import java.util.Calendar;

public class Location {
	
	private String name;
	private double x;
	private double y;
	private int totalVisited;
	private Calendar lastVisited;
	
	public Location(double x, double  y) {
		this.x = x;
		this.y = y;
		lastVisited = Calendar.getInstance();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
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
		return getX() + ";" + getY();
	}
	
	@Override
	public String toString() {
		return name + " (" + totalVisited + "): " + getX() + "; " + getY();
	}

}
