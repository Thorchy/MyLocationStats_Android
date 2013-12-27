package mobile.mylocationstats.db;

import android.content.Context;

public class DatabaseFactory {
	
	public static Database getDatabase(String db, Context context) {
		Database database = MemoryDatabase.getInstance();
		if(db.equals("ANDROID")) {
			database = AndroidDatabase.getInstance(context);
		} else if (db.equals("MEMORY")) {
			database = MemoryDatabase.getInstance();
		}
		
		return database;
	}

}
