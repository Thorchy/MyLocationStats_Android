package mobile.mylocationstats.domain;

public class Location {
	
	private String name;
	private double x;
	private double y;
	private int totalVisited;
	
	public Location(double x, double  y) {
		this.x = x;
		this.y = y;
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

}
