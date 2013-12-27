package mobile.mylocationstats.db;

import java.util.Calendar;
import java.util.List;

import mobile.mylocationstats.domain.Location;
import mobile.mylocationstats.domain.Target;
import mobile.mylocationstats.domain.Visit;

public interface Database {
	
	public Location getLocation(long id);
	public void editLocation(Location location);
	public void addLocation(Location location);
	public void removeLocation(long id);
	
	public Target getTarget(long id);
	public void addTarget(Target target);
	public void removeTarget(long id);
	
	public Visit getVisit(long id);
	public void addVisit(Visit visit);
	public int getAmountVisited(long id);
	public Calendar getLastVisited(long id);
	
	public List<Location> getAllLocations();
	public List<Target> getAllTargets();
	
	public Location getMostVisited();
	public Location getLastVisited();
	public Target getNearestTarget(Location location);
	public void checkVisited(Location location, long visited);

}
