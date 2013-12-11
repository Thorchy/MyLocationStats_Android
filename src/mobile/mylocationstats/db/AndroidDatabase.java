package mobile.mylocationstats.db;

import java.util.List;

import mobile.mylocationstats.domain.Location;
import mobile.mylocationstats.domain.Target;

public class AndroidDatabase implements Database {
	
	private static Database androidDatabase;
	
	private AndroidDatabase() {
	}
	
	public static Database getInstance() {
		if(androidDatabase == null) {
			androidDatabase = new AndroidDatabase();
		}
		
		return androidDatabase;
	}

	@Override
	public Location getLocation(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editLocation(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLocation(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLocation(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Target getTarget(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTarget(Target target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTarget(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Location> getAllLocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Target> getAllTargets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getMostVisited() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLastVisited() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Target getNearestTarget(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

}
