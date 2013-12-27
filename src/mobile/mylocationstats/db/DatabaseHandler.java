package mobile.mylocationstats.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 4;
	private static final String DATABASE_NAME = "MyLocationStats";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOCATIONS = "CREATE TABLE locations ("
		+	"	id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+	"	name VARCHAR(100) NOT NULL,"
		+	"	latitude DOUBLE NOT NULL,"
		+	"	longitude DOUBLE NOT NULL"
		+	");";
				
		String CREATE_LOCATION_VISITED = "CREATE TABLE location_visited ("
		+	"	id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+	"	location_id INTEGER REFERENCES locations(id),"
		+	"	visited_on LONG NULL"
		+	");";

		String CREATE_TARGETS =	"CREATE TABLE targets ("
		+	"	id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+	"	location_id INTEGER REFERENCES locations(id),"
		+	"	due_date LONG NULL"
		+	");";
		
		db.execSQL(CREATE_LOCATIONS);
		db.execSQL(CREATE_LOCATION_VISITED);
		db.execSQL(CREATE_TARGETS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS location_visited");
		db.execSQL("DROP TABLE IF EXISTS targets");
		db.execSQL("DROP TABLE IF EXISTS locations");
		onCreate(db);
	}

}
