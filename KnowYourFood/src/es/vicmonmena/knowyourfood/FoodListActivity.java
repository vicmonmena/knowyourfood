package es.vicmonmena.knowyourfood;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import es.vicmonmena.knowyourfood.database.KYFDatabase;

/**
 * 
 * @author vicmonmena
 *
 */
public class FoodListActivity extends ListActivity {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "FoodListActivity";
	
	/**
	 * Manejador del listado.
	 */
	private FoodCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Cursor c = Controller.getInstance().getAllFood(this);
		adapter = new FoodCursorAdapter(this, c);
        setListAdapter(adapter);
        
        // registramos el listview para el menú contextual
        registerForContextMenu(getListView());
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        	case R.id.action_export:
        		Toast.makeText(this, "En construcción", Toast.LENGTH_LONG).show();
        		return true;
        	case R.id.action_info:
        		AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.action_info);
				builder.setMessage(getString(R.string.dialog_export_info));
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
	 * Clase interna para el manejo de los elementos de la lista.
	 */
	final class FoodCursorAdapter extends ResourceCursorAdapter {

		/**
		 * Layout de cada elemento del listado.
		 */
		private LayoutInflater mInflater;
		
		public FoodCursorAdapter(Context context, Cursor cursor) {
			super(context, R.layout.food_row_item, cursor, true);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			
			View view = mInflater.inflate(android.R.layout.simple_list_item_2, parent, false);

			ViewHolder holder = new ViewHolder();
			holder.foodNameTxt = (TextView) view.findViewById(R.id.foodNameRowItem);
			holder.foodBrandTxt = (TextView) view.findViewById(R.id.foodBrandRowItem);
			view.setTag(holder);
			return view;
		}
		
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ViewHolder holder = (ViewHolder) view.getTag();
			holder.foodNameTxt.setText(
				cursor.getString(cursor.getColumnIndex(KYFDatabase.NAME)));
			holder.foodBrandTxt.setText(
				cursor.getString(cursor.getColumnIndex(KYFDatabase.BRAND)));
		}
	}
	/**
	 * Patrón Holder: Clase que contiene los campos de un registro de la lista.
	 */
	static class ViewHolder {
		TextView foodNameTxt;
		TextView foodBrandTxt;
	}
}
