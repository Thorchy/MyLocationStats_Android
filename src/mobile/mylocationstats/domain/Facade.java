package mobile.mylocationstats.domain;

import java.util.List;

import mobile.mylocationstats.db.AndroidDatabase;
import mobile.mylocationstats.db.Database;

public class Facade {
	
	private Database localDatabase;
	
	public Facade() {
		localDatabase = AndroidDatabase.getInstance();
	}
	
	public String getMostVisited() {
		Location locMostVisited = localDatabase.getMostVisited();
		String mv = "Nothing visited yet.";
		
		if(locMostVisited != null) {
			if(locMostVisited.getName() != null) {
				mv = localDatabase.getMostVisited().getName();
			} else {
				mv = locMostVisited.getX() + "; " + locMostVisited.getY();
			}
		}
		
		return mv;
	}
	
	public String getLastVisited() {
		Location locLastVisited = localDatabase.getLastVisited();
		String lv = "Nothing visited yet.";
		
		if(locLastVisited != null) {
			if(locLastVisited.getName() != null) {
				lv = localDatabase.getMostVisited().getName();
			} else {
				lv = locLastVisited.getX() + "; " + locLastVisited.getY();
			}
		}
		
		return lv;
	}
	
	public String getNearestTarget(Location location) {
		Target nearestTarget = localDatabase.getNearestTarget(location);
		String nt = "No targets found.";
		
		if(nearestTarget != null) {
			if(nearestTarget.getLocation().getName() != null) {
				nt = nearestTarget.getLocation().getName();
			} else {
				nt = nearestTarget.getLocation().getX() + "; " + nearestTarget.getLocation().getY();
			}
		}
		
		return nt;
	}
	
	public List<Location> getAllVisitedLocations() {
		return localDatabase.getAllLocations();
	}

	public List<Target> getAllTargets() {
		return localDatabase.getAllTargets();
	}
	
	public void addTarget(Target target) {
		localDatabase.addTarget(target);
	}
}