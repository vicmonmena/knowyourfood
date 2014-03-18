package es.vicmonmena.knowyourfoodwriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import es.vicmonmena.knowyourfoodwriter.domain.Food;

/**
 * 
 * @author vicmonmena
 *
 */
public class MainActivity extends Activity {

	/**
	 * Etiqueta para localización de LOGS correspondientes a esta clase.
	 */
	private static final String TAG = "WriteTagActivity";
	
	/**
	 * 
	 */
	private NfcAdapter nfcAdapter;
	
	/**
	 *  Contendrá la información extraida de la etiqueta NFC
	 */
    private PendingIntent pendingIntent;
    
    /**
     * Etiqueta NFC
     */
    private Tag myTag;
    
    // Campos del layout
    private EditText foodNameEditTxt;
    private EditText foodBrandEditTxt;
    private EditText foodIngredientsEditTxt;
    private CheckBox lactosaCheckBox;
    private CheckBox glutenCheckBox;
    private CheckBox faceCheckBox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Comprobar que el NFCAdapter está disponible
        nfcAdapter = NfcAdapter.getDefaultAdapter(MainActivity.this);
        
        if (nfcAdapter == null) {
        	Toast.makeText(MainActivity.this, 
        		"NFC not enable", 
        		Toast.LENGTH_SHORT).show();
        	finish();
        	return;
        }
        
        // Rellenamos con los datos de la etiqueta leída
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
        	this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        
        foodNameEditTxt = (EditText) findViewById(R.id.foodEdit);
        foodBrandEditTxt = (EditText) findViewById(R.id.brandEdit);	
        foodIngredientsEditTxt = (EditText) findViewById(R.id.ingredientsEdit);
        lactosaCheckBox = (CheckBox) findViewById(R.id.lactosaCheckBox);
        glutenCheckBox = (CheckBox) findViewById(R.id.glutenCheckBox);
        faceCheckBox = (CheckBox) findViewById(R.id.faceCheckBox);
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	Log.d(TAG, "onPause");
    	if (nfcAdapter != null) {
    		nfcAdapter.disableForegroundDispatch(this);
    	}
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	Log.d(TAG, "onResume");
    	if (nfcAdapter != null) {
    		nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	
    	Log.d(TAG, "onNewIntent...");
    	// Obtenemos la tecnología de la etiqueta BFC que hemos leído
    	myTag=intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    }
    
    /**
     * Captura el evento click de los botones que tengan definido este método.
     * @param view
     */
    public void onClickButton(View view) {

    	switch (view.getId()) {
			case R.id.writeBtn:
		    	if (foodValidation()) {
		    		try {
		    			Food food = new Food(
		    				foodNameEditTxt.getText().toString(),
	    		    		foodBrandEditTxt.getText().toString(),
	    		    		foodIngredientsEditTxt.getText().toString(),
	    		    		lactosaCheckBox.isChecked(), 
	    		    		glutenCheckBox.isChecked(),
	    		    		faceCheckBox.isChecked());
		    			
						if (writeFood(food)) {
							Toast.makeText(MainActivity.this, 
								getString(R.string.msg_tag_written), 
								Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(MainActivity.this, "Error writting", Toast.LENGTH_SHORT).show();
						}
					} catch (IOException e) {
						Toast.makeText(MainActivity.this, "Error writting (I/O Exception)", Toast.LENGTH_SHORT).show();
					} catch (FormatException e) {
						Toast.makeText(MainActivity.this, "Error writting (FormatException)", Toast.LENGTH_SHORT).show();
					}
		    	} else {
		    		Toast.makeText(MainActivity.this, getString(R.string.msg_empty_fields), Toast.LENGTH_SHORT).show();
		    	}
		    	break;
			default:
				break;
		}
    }
    
    /**
     * Escribir en una etiqueta NFC un alimento (Food)
     * @param text
     * @throws IOException
     * @throws FormatException
     */
    private boolean writeFood(Food food) throws IOException, FormatException {
	
    	boolean result = false;
		Log.d(TAG, "Writing NFC tag ...");
		
		if (myTag != null) {
			byte[] checks = {
				(byte) (food.isLactosa()?1:0), 
				(byte) (food.isGluten()?1:0),
				(byte) (food.isFace()?1:0)
			};
			NdefRecord[] records = new NdefRecord[]{
				createTextRecord(food.getName()),
				createTextRecord(food.getBrand()),
				createTextRecord(food.getIngredients()),
				createTextRecordFromBytes(checks)};
			
			NdefMessage message = new NdefMessage(records);
			
			// Obtener una instancia de Ndef para la etiqueta
			Ndef ndef = Ndef.get(myTag);
			
			// Activar I/O
			ndef.connect();
			
			// Escribir el mensaje
			ndef.writeNdefMessage(message);
			
			// Cerrar la conexión
			ndef.close();
			
			result = true;
		}
		return result;
	}
    
    /**
     * Crea un NDEF TEXT Record para escribir en la etiqueta
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    private NdefRecord createTextRecord(String text) 
    	throws UnsupportedEncodingException {
    	
    	String lang = "es";
    	
    	byte[] textBytes = text.getBytes();
    	byte[] langBytes = lang.getBytes();
    	
    	int textLength = textBytes.length;
    	int langLength = langBytes.length;
    	
    	byte[] payload = new byte[1 + langLength + textLength];
    	
    	//Byte de estado (Ver especificaciones NDEF)
    	payload[0] = (byte) langLength;
    	
    	//Copiar langbytes y textbytes en el payload
    	System.arraycopy(langBytes, 0, payload, 1, langLength);
    	System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);
    	
    	NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, 
    		NdefRecord.RTD_TEXT, new byte[0], payload);
    	
    	return record;
    }
    
    /**
     * Crea un NDEF TEXT Record para escribir en la etiqueta
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    private NdefRecord createTextRecordFromBytes(byte[] payload) {
    	
    	NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, 
        		NdefRecord.RTD_TEXT, new byte[0], payload);
    	
    	return record;
	}
    
    /**
     * Validación de los campos del formulario.
     * @return True si todos los campos están rellenos, false en caso contrario.
     */
    private boolean foodValidation() {
    	
    	boolean result = true;
    	
    	if (TextUtils.isEmpty(foodNameEditTxt.getText())
			|| TextUtils.isEmpty(foodBrandEditTxt.getText())
			|| TextUtils.isEmpty(foodIngredientsEditTxt.getText())) {
    		
    		result = false;
    	}
    	return result;
    }
}
