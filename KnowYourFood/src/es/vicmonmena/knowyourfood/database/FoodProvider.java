package es.vicmonmena.knowyourfood.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * 
 * @author vicmonmena
 *
 */
public class FoodProvider extends ContentProvider {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "FoodProvider";
	
	private static final String AUTHORITY = "es.vicmonmena.knowyourfood.food";
	
	public static final Uri CONTENT_URI = 
			Uri.parse("content://" + AUTHORITY + "/food");

	private static final int URI_FOOD = 1;
	private static final int URI_FOOD_ITEM = 2;
	
	private static final UriMatcher mUriMatcher;
	
	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(AUTHORITY, "food", URI_FOOD);
		mUriMatcher.addURI(AUTHORITY, "food/#", URI_FOOD_ITEM);
	}
	
	/**
	 * Objeto para trabajar contra la BBDD.
	 */
	private KYFDatabaseHelper dbHelper;
	

	@Override
	public boolean onCreate() {
		dbHelper = new KYFDatabaseHelper(getContext());
		return true;
	}
	
	@Override
	public String getType(Uri uri) {
		int match = mUriMatcher.match(uri);
		
		switch (match){
			case URI_FOOD:
				return "vnd.android.cursor.dir/vnd.es.vicmonmena.knowyourfood.jobs";
			case URI_FOOD_ITEM:
				return "vnd.android.cursor.item/vnd.es.vicmonmena.knowyourfood.jobs";
			default:
				Log.w(TAG, "Uri didn't match: " + uri);
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		int match = mUriMatcher.match(uri);

		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
		qBuilder.setTables(KYFDatabase.FOOD_TABLE_NAME);
		
		switch (match){
			case URI_FOOD:
				//nada
				break;
			case URI_FOOD_ITEM:
				String id = uri.getPathSegments().get(1);
				qBuilder.appendWhere(KYFDatabase._ID + "=" + id);
				break;
			default:
				Log.w(TAG, "Uri didn't match: " + uri);
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		Cursor c = qBuilder.query(
			db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		
		return c;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		long id = db.insert(KYFDatabase.FOOD_TABLE_NAME, null, values);
		
		Uri result = null;
		
		if (id >= 0){
			result = ContentUris.withAppendedId(CONTENT_URI, id);
			notifyChange(result);
		}
		
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		int numAffectedRows = db.update(KYFDatabase.FOOD_TABLE_NAME, values, 
			selection, selectionArgs);
		
		if (numAffectedRows > 0){
			notifyChange(uri);
		}
		return numAffectedRows;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int numAffectedRows = db.delete(KYFDatabase.FOOD_TABLE_NAME, selection, selectionArgs);
		if (numAffectedRows > 0){
			notifyChange(uri);
		}
		return numAffectedRows;
	}
	
	protected void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }
}
