package mobile.mylocationstats.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mobile.mylocationstats.domain.Location;
import mobile.mylocationstats.domain.Target;

public class MemoryDatabase implements Database {

	private static Database memoryDatabse;
	
	private List<Location> locations;
	private List<Target> targets;

	private MemoryDatabase() {
		locations = new ArrayList<Location>();
		targets = new ArrayList<Target>();
		
		Location l1 = new Location(1,1);
		l1.setName("Loc 1");
		
		Location l2 = new Location(47,6);
		l2.setName("Loc 2");
		
		locations.add(l1);
		locations.add(l2);
		
		Target t1 = new Target(l1, Calendar.getInstance());
		Target t2 = new Target(l2, Calendar.getInstance());
		targets.add(t1);
		targets.add(t2);
		
	}

	public static Database getInstance() {
		if (memoryDatabse == null) {
			memoryDatabse = new MemoryDatabase();
		}

		return memoryDatabse;
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
		locations.add(location);
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
		targets.add(target);
	}

	@Override
	public void removeTarget(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Location> getAllLocations() {
		return locations;
	}

	@Override
	public List<Target> getAllTargets() {
		return targets;
	}

	@Override
	public Location getMostVisited() {
		Location loc = new Location(51,7);
		loc.setName("Test MV");
		return loc;
	}

	@Override
	public Location getLastVisited() {
		Location loc = new Location(50,6);
		loc.setName("Test LV");
		return loc;
	}

	@Override
	public Target getNearestTarget(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

}
