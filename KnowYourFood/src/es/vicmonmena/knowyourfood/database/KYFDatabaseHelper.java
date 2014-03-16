package es.vicmonmena.knowyourfood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author vicmonmena
 *
 */
public class KYFDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "KYFDatabaseHelper";
	
	public KYFDatabaseHelper(Context context){
		super(context, KYFDatabase.DATABASE_NAME, null, KYFDatabase.DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		db.execSQL("CREATE TABLE " + 
			KYFDatabase.FOOD_TABLE_NAME + " (" +
			KYFDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
			KYFDatabase.NAME + " TEXT NOT NULL, " +
			KYFDatabase.BRAND + " TEXT NOT NULL, " +
			KYFDatabase.INGREDIENTS + " TEXT NOT NULL" + ");"
		);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade");
		if (oldVersion < newVersion){
			db.execSQL("DROP TABLE IF EXISTS " + KYFDatabase.FOOD_TABLE_NAME + ";");
			onCreate(db);
		}
	}
}
