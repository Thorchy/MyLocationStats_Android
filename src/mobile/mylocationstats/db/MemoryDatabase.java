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

		Location l1 = new Location(50.87369541032191, 4.736698307096958);
		l1.setName("Trolieberg");

		Location l2 = new Location(50.9, 4.6);
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
		Location location = null;

		for (Location tempLocation : locations) {
			if (tempLocation.getId() == id) {
				location = tempLocation;
			}
		}

		return location;
	}

	@Override
	public void editLocation(Location location) {
		locations.set(locations.indexOf(location), location);
	}

	@Override
	public void addLocation(Location location) {
		locations.add(location);
	}

	@Override
	public void removeLocation(int id) {
		locations.remove(getLocation(id));
	}

	@Override
	public Target getTarget(int id) {
		Target target = null;

		for (Target tempTarget : targets) {
			if (tempTarget.getId() == id) {
				target = tempTarget;
			}
		}

		return target;
	}

	@Override
	public void addTarget(Target target) {
		targets.add(target);
	}

	@Override
	public void removeTarget(int id) {
		targets.remove(getTarget(id));
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
		Location loc = new Location(51, 7);
		loc.setName("Test MV");
		return loc;
	}

	@Override
	public Location getLastVisited() {
		Location loc = new Location(50, 6);
		loc.setName("Test LV");
		return loc;
	}

	@Override
	public Target getNearestTarget(Location location) {
		float meters = Float.MAX_VALUE;
		float distance;
		Target nearest = null;

		for (Target target : targets) {

			distance = target.getLocation().distanceFrom(location);
			if (distance < meters) {
				nearest = target;
				meters = distance;
			}
		}

		return nearest;
	}

}
