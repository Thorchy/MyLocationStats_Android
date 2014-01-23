package mobile.mylocationstats.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import mobile.mylocationstats.domain.Location;
import mobile.mylocationstats.domain.Target;
import mobile.mylocationstats.domain.Visit;

public class AndroidDatabase implements Database {

	private static Database androidDatabase;
	private DatabaseHandler dbHandler;

	private AndroidDatabase(Context context) {
		dbHandler = new DatabaseHandler(context);
	}

	public static Database getInstance(Context context) {
		if (androidDatabase == null) {
			androidDatabase = new AndroidDatabase(context);
		}

		return androidDatabase;
	}

	@Override
	public Location getLocation(long id) {
		Location location = null;
		SQLiteDatabase db = dbHandler.getReadableDatabase();
		Cursor cursor = db.query("locations", new String[] { "id", "name", "latitude", "longitude" }, "id=?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();

			location = new Location(cursor.getDouble(2), cursor.getDouble(3));
			location.setId(cursor.getInt(0));
			location.setName(cursor.getString(1));
			location.setLastVisited(getLastVisited(location.getId()));
			location.setTotalVisited(getAmountVisited(location.getId()));
		}
		
		db.close();

		return location;
	}

	@Override
	public void editLocation(Location location) {
		SQLiteDatabase db = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("name", location.getName());
		values.put("latitude", location.getLatitude());
		values.put("longitude", location.getLongitude());

		db.update("locations", values, "id=?", new String[] { String.valueOf(location.getId()) });
		db.close();
	}

	@Override
	public void addLocation(Location location) {
		SQLiteDatabase db = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("name", location.getName());
		values.put("latitude", location.getLatitude());
		values.put("longitude", location.getLongitude());

		location.setId(db.insert("locations", null, values));
		db.close();
	}

	@Override
	public void removeLocation(long id) {
		SQLiteDatabase db = dbHandler.getWritableDatabase();

		db.delete("locations", "id=?", new String[] { String.valueOf(id) });
		db.close();
	}

	@Override
	public Target getTarget(long id) {
		Target target = null;

		SQLiteDatabase db = dbHandler.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT t.id, t.due_date, l.id, l.name, l.latitude, l.longitude FROM targets t INNER JOIN locations l ON t.location_id = l.id WHERE t.id = ?", new String[] { id + "" });

		if (cursor != null) {
			cursor.moveToFirst();

			Location location = new Location(cursor.getDouble(4), cursor.getDouble(5));
			location.setId(cursor.getInt(2));
			location.setName(cursor.getString(3));
			location.setLastVisited(getLastVisited(location.getId()));
			location.setTotalVisited(getAmountVisited(location.getId()));

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(cursor.getLong(1));

			target = new Target(location, cal);
			target.setId(cursor.getInt(0));
		}
		
		db.close();

		return target;
	}

	@Override
	public void addTarget(Target target) {

		if (target.getLocation().getId() == 0) {
			addLocation(target.getLocation());
		}

		SQLiteDatabase db = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("location_id", target.getLocation().getId());
		values.put("due_date", target.getDueDate().getTimeInMillis());

		target.setId(db.insert("targets", null, values));
		db.close();
	}

	@Override
	public void removeTarget(long id) {
		SQLiteDatabase db = dbHandler.getWritableDatabase();

		db.delete("targets", "id=?", new String[] { String.valueOf(id) });
		db.close();
	}

	@Override
	public Visit getVisit(long id) {
		return null;
	}

	@Override
	public void addVisit(Visit visit) {
		if (visit.getLocation().getId() == 0) {
			addLocation(visit.getLocation());
		}

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("location_id", visit.getLocation().getId());
		values.put("visited_on", visit.getVisitedOn().getTimeInMillis());

		visit.setId(db.insert("location_visited", null, values));
		db.close();
	}

	@Override
	public int getAmountVisited(long id) {
		int amount = 0;
		SQLiteDatabase db = dbHandler.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT count(*) AS amount_visited FROM location_visited WHERE location_id = ?", new String[] { String.valueOf(id) });

		if (cursor != null) {
			cursor.moveToFirst();
			amount = cursor.getInt(0);
		}
		
		db.close();

		return amount;
	}

	@Override
	public Calendar getLastVisited(long id) {
		Calendar lastVisited = null;
		SQLiteDatabase db = dbHandler.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT max(visited_on) AS last_visited FROM location_visited WHERE location_id = ?", new String[] { String.valueOf(id) });

		if (cursor != null) {
			cursor.moveToFirst();
			lastVisited = Calendar.getInstance();
			lastVisited.setTimeInMillis(cursor.getLong(0));
		}
		
		db.close();

		return lastVisited;
	}

	@Override
	public List<Location> getAllLocations() {
		List<Location> locations = new ArrayList<Location>();

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT locations.id, name, latitude, longitude FROM locations INNER JOIN location_visited lv ON locations.id = lv.location_id", null);

		if (cursor.moveToFirst()) {
			do {
				Location location = new Location(cursor.getDouble(2), cursor.getDouble(3));
				location.setId(cursor.getInt(0));
				location.setName(cursor.getString(1));
				location.setLastVisited(getLastVisited(location.getId()));
				location.setTotalVisited(getAmountVisited(location.getId()));

				locations.add(location);
			} while (cursor.moveToNext());
		}
		
		db.close();

		return locations;
	}

	@Override
	public List<Target> getAllTargets() {
		List<Target> targets = new ArrayList<Target>();

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT t.id, t.due_date, l.id, l.name, l.latitude, l.longitude FROM targets t INNER JOIN locations l ON t.location_id = l.id", null);

		if (cursor.moveToFirst()) {
			do {
				Location location = new Location(cursor.getDouble(4), cursor.getDouble(5));
				location.setId(cursor.getInt(2));
				location.setName(cursor.getString(3));
				location.setLastVisited(getLastVisited(location.getId()));
				location.setTotalVisited(getAmountVisited(location.getId()));

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(cursor.getLong(1));

				Target target = new Target(location, cal);
				target.setId(cursor.getInt(0));

				targets.add(target);
			} while (cursor.moveToNext());
		}
		
		db.close();

		return targets;
	}

	@Override
	public Location getMostVisited() {
		Location location = null;
		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT l.id, name, latitude, longitude, count(*) AS amount_visited FROM locations l INNER JOIN location_visited lv ON l.id = lv.location_id GROUP BY l.id, name, latitude, longitude ORDER BY amount_visited desc LIMIT 1", null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			location = new Location(cursor.getDouble(2), cursor.getDouble(3));
			location.setId(cursor.getInt(0));
			location.setName(cursor.getString(1));
			location.setLastVisited(getLastVisited(location.getId()));
			location.setTotalVisited(getAmountVisited(location.getId()));
		}
		
		db.close();

		return location;
	}

	@Override
	public Location getLastVisited() {
		Location location = null;
		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT l.id, name, latitude, longitude, MAX(visited_on) AS last_visited FROM locations l INNER JOIN location_visited lv ON l.id = lv.location_id GROUP BY l.id, name, latitude, longitude ORDER BY last_visited desc LIMIT 1", null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			location = new Location(cursor.getDouble(2), cursor.getDouble(3));
			location.setId(cursor.getInt(0));
			location.setName(cursor.getString(1));
			location.setLastVisited(getLastVisited(location.getId()));
			location.setTotalVisited(getAmountVisited(location.getId()));
		}
		
		db.close();

		return location;
	}

	@Override
	public Target getNearestTarget(Location location) {
		float meters = Float.MAX_VALUE;
		float distance;
		Target nearest = null;

		for (Target target : getAllTargets()) {
			distance = target.getLocation().distanceFrom(location);
			if (distance < meters) {
				nearest = target;
				meters = distance;
			}
		}

		if (meters < 100) {
			addVisit(new Visit(nearest.getLocation(), Calendar.getInstance()));
			removeTarget(nearest.getId());
		}

		return nearest;
	}

	@Override
	public void checkVisited(Location location, long visited) {
		float meters = Float.MAX_VALUE;
		float distance;
		Location nearest = null;

		for (Location l : getAllLocations()) {
			distance = l.distanceFrom(location);
			if (distance < meters) {
				nearest = l;
				meters = distance;
			}
		}

		if (nearest != null) {
			Log.d("MYLOCATION", meters + " | " + visited + " | " + (getLastVisited(nearest.getId()).getTimeInMillis() + 1800000));
			if (meters < 100 && visited > (getLastVisited(nearest.getId()).getTimeInMillis() + 1800000)) {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(visited);
				addVisit(new Visit(nearest, cal));
			}
		}
	}

}
