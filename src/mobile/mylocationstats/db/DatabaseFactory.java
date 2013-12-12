package mobile.mylocationstats.db;

public class DatabaseFactory {
	
	public static Database getDatabase(String db) {
		Database database = MemoryDatabase.getInstance();
		if(db.equals("ANDROID")) {
			database = AndroidDatabase.getInstance();
		} else if (db.equals("MEMORY")) {
			database = MemoryDatabase.getInstance();
		}
		
		return database;
	}

}
