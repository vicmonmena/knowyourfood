package es.vicmonmena.knowyourfood;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import es.vicmonmena.knowyourfood.database.FoodProvider;
import es.vicmonmena.knowyourfood.database.KYFDatabase;

public class Controller {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "Controller";
	
	/**
	 * Controller class instace. Singleton pattern.
	 */
	private static Controller controller;
	
	/**
	 * Controller class constructor. Singleton patter.
	 */
	private Controller() {}
	
	/**
	 * Get the Controller class instance. Singleton pattern.
	 * @return
	 */
	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		
		return controller;
	}
	
	/**
	 * Obtien el listado completo de alimentos almacenados en BBDD.
	 * @param context
	 * @return
	 */
	public Cursor getAllFood(Context context) {
		ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(FoodProvider.CONTENT_URI, 
        	KYFDatabase.PROJECTION, null, null, KYFDatabase.NAME);
        return cursor;
	}
	
	/**
	 * Registra un alimento en la BBDD
	 * @param context
	 * @param foodName
	 * @param foodBrand
	 * @param foodIngredients
	 * @param hadLactosa
	 * @param hadGluten
	 * @param isFACE
	 */
	public void addFood(Context context, String foodName, 
		String foodBrand, String foodIngredients, boolean hadLactosa, 
		boolean hadGluten, boolean isFACE) {
		Log.d(TAG, "Adding food to your list...");
		ContentValues values = new ContentValues(1);
		
		values.put(KYFDatabase.NAME, foodName);
		values.put(KYFDatabase.BRAND, foodBrand);
		values.put(KYFDatabase.INGREDIENTS, foodIngredients);
		values.put(KYFDatabase.LACTOSA, hadLactosa);
		values.put(KYFDatabase.GLUTEN, hadGluten);
		values.put(KYFDatabase.FACE, isFACE);
		
		ContentResolver cr = context.getContentResolver();
		cr.insert(FoodProvider.CONTENT_URI, values);
	}
}
