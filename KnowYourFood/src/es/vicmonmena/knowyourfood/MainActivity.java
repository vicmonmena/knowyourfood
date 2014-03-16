package es.vicmonmena.knowyourfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author vicmonmena
 *
 */
public class MainActivity extends Activity {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "MainActivity";
	
	private TextView foodName;
	private TextView foodBrand;
	private TextView foodIngredients;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        foodName = (TextView) findViewById(R.id.foodNameTxtView);
        foodBrand = (TextView) findViewById(R.id.foodBrandTxtView);
        foodIngredients = (TextView) findViewById(R.id.foodIngredientsTxtView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        	case R.id.action_list:
        		Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
        		startActivity(intent);
        		return true;
        	case R.id.action_info:
        		AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.action_info);
				builder.setMessage(getString(R.string.dialog_info));
				builder.setNeutralButton(R.string.dialog_done, 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
				builder.create().show();
        		return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Captura el evento onclick del botón y añade un alimento a la lista
     * @param view
     */
    public void addFood(View view) {
    	if (view.getId() == R.id.addFoodBtn) {
    		
    		//if ()
    		//Controller.getInstance().addFood(this, foodName, foodBrand, foodIngredients, hadLactosa, hadGluten, isFACE);
    	}
	}
}