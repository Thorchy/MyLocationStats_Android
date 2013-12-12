package mobile.mylocationstats.domain;

import java.util.List;

import mobile.mylocationstats.db.Database;
import mobile.mylocationstats.db.DatabaseFactory;

public class Facade {
	
	private Database database;
	
	public Facade() {
		database = DatabaseFactory.getDatabase("MEMORY");
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public String getMostVisited() {
		Location locMostVisited = database.getMostVisited();
		String mv = "Nothing visited yet.";
		
		if(locMostVisited != null) {
			if(locMostVisited.getName() != null) {
				mv = locMostVisited.getName();
			} else {
				mv = locMostVisited.getLatitude() + "; " + locMostVisited.getLongitude();
			}
		}
		
		return mv;
	}
	
	public String getLastVisited() {
		Location locLastVisited = database.getLastVisited();
		String lv = "Nothing visited yet.";
		
		if(locLastVisited != null) {
			if(locLastVisited.getName() != null) {
				lv = locLastVisited.getName();
			} else {
				lv = locLastVisited.getLatitude() + "; " + locLastVisited.getLongitude();
			}
		}
		
		return lv;
	}
	
	public String getNearestTarget(Location location) {
		Target nearestTarget = database.getNearestTarget(location);
		String nt = "No targets found.";
		
		if(nearestTarget != null) {
			if(nearestTarget.getLocation().getName() != null) {
				nt = nearestTarget.getLocation().getName();
			} else {
				nt = nearestTarget.getLocation().getLatitude() + "; " + nearestTarget.getLocation().getLongitude();
			}
		}
		
		return nt;
	}
	
	public List<Location> getAllVisitedLocations() {
		return database.getAllLocations();
	}

	public List<Target> getAllTargets() {
		return database.getAllTargets();
	}
	
	public void addTarget(Target target) {
		database.addTarget(target);
	}
}
