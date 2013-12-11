package mobile.mylocationstats.db;

import java.util.List;

import mobile.mylocationstats.domain.Location;
import mobile.mylocationstats.domain.Target;

public interface Database {
	
	public Location getLocation(int id);
	public void editLocation(Location location);
	public void addLocation(Location location);
	public void removeLocation(int id);
	
	public Target getTarget(int id);
	public void addTarget(Target target);
	public void removeTarget(int id);
	
	public List<Location> getAllLocations();
	public List<Target> getAllTargets();
	
	public Location getMostVisited();
	public Location getLastVisited();
	public Target getNearestTarget(Location location);

}
